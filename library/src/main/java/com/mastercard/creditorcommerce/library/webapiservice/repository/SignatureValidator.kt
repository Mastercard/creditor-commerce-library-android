package com.mastercard.creditorcommerce.library.webapiservice.repository

import android.content.Context
import android.webkit.URLUtil
import com.mastercard.creditorcommerce.library.LibraryCallbackListener
import com.mastercard.creditorcommerce.library.exception.LibraryError
import com.mastercard.creditorcommerce.library.exception.LibraryErrorType
import com.mastercard.creditorcommerce.library.models.DspManifestDataResponseModel
import com.mastercard.creditorcommerce.library.utils.LibraryConstants
import com.mastercard.creditorcommerce.library.utils.LibraryUtils
import org.jose4j.jwa.AlgorithmConstraints
import org.jose4j.jws.JsonWebSignature
import org.jose4j.lang.JoseException
import org.json.JSONObject
import java.io.ByteArrayInputStream
import java.io.IOException
import java.net.URL
import java.security.PublicKey
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate

internal class SignatureValidator {

    companion object {
        private val TAG = SignatureValidator::class.java.simpleName
    }

    fun verifySignatureAndParseData(
        context: Context,
        dspManifestUrl: String,
        manifestSignedData: String,
        callbackListener: LibraryCallbackListener<DspManifestDataResponseModel>
    ) {
        try {
            val signedDataArray = manifestSignedData.split(LibraryConstants.MANIFEST_DATA_SEPARATOR)
            val encodedHeader: String = signedDataArray[LibraryConstants.HEADER_INDEX_IN_MANIFEST_DATA]
            val encodedPayload: String = signedDataArray[LibraryConstants.PAYLOAD_INDEX_IN_MANIFEST_DATA]
            val encodedSignature: String = signedDataArray[LibraryConstants.SIGNATURE_INDEX_IN_MANIFEST_DATA]

            if (validateHeaderAndSignature(dspManifestUrl, encodedHeader, encodedSignature)) {
                val decodedHeaderJson = JSONObject(LibraryUtils.decodeBase64ToString(encodedHeader))
                val signedCertificateURL = getSignedCertificateUrlFromHeaderJson(decodedHeaderJson)!!
                val algorithm = getAlgorithmFromHeaderJson(decodedHeaderJson)!!

                val creditorCommerceLibraryRepository: CreditorCommerceLibraryRepository =
                    CreditorCommerceLibraryRepositoryImpl()
                creditorCommerceLibraryRepository.getSignedCertificateFromURL(signedCertificateURL,
                    context,
                    object : LibraryCallbackListener<String> {
                        override fun success(response: String) {
                            //Validate Signature hear
                            val publicKey = getPublicKey(response)
                            if (publicKey != null) {
                                if (isSignatureVerified(algorithm, manifestSignedData, publicKey)) {
                                    LibraryUtils.parseSignedManifestPayloadData(encodedPayload, callbackListener)
                                } else {
                                    callbackListener.failure(LibraryErrorType.SignatureVerificationFailed.libraryError)
                                }

                            } else {
                                callbackListener.failure(LibraryErrorType.InvalidPublicKey.libraryError)
                            }
                        }
                        override fun failure(error: LibraryError) {
                            callbackListener.failure(error)
                        }
                    }
                )
            }

        } catch (ex: CertificateException) {
            callbackListener.failure(LibraryErrorType.InvalidSignedCertificate.libraryError)
        } catch (ex: JoseException) {
            callbackListener.failure(LibraryErrorType.SignatureVerificationFailed.libraryError)
        } catch (ex: LibraryError) {
            callbackListener.failure(ex)
        } catch (ex: Exception) {
            callbackListener.failure(LibraryErrorType.InvalidManifestFileError.libraryError)
        }
    }

    @Throws(
        CertificateException::class,
        IOException::class,
        JoseException::class
    )
    private fun getPublicKey(signedCertificateResponse: String): PublicKey? {
        val f = CertificateFactory.getInstance("X.509")
        val certificate = f.generateCertificate(ByteArrayInputStream(signedCertificateResponse.toByteArray())) as X509Certificate
        val pk = certificate.publicKey
        return pk
    }

    @Throws(JoseException::class)
    private fun isSignatureVerified(
        algorithm: String,
        signatureWithPayload: String,
        publicKey: PublicKey
    ): Boolean {
        val jws = JsonWebSignature()
        jws.setAlgorithmConstraints(
            AlgorithmConstraints(
                AlgorithmConstraints.ConstraintType.PERMIT,
                algorithm
            )
        )
        jws.compactSerialization = signatureWithPayload
        jws.key = publicKey
        return jws.verifySignature()
    }

    /**
     * Validate Signed manifest Data and payload data details received in Manifest API Response
     * @param encodedHeader: encoded header data
     * @param encodedSignature: encoded signature data
     * @return Any : error or true or false
     */
    @Throws(
        LibraryError::class,
        Exception::class,
    )
    private fun validateHeaderAndSignature( dspManifestUrl: String, encodedHeader: String, encodedSignature: String): Boolean {
        if (encodedHeader.isEmpty()) {
            throw LibraryErrorType.EmptyHeaderReceived.libraryError
        }

        if (encodedSignature.isEmpty()) {
            throw LibraryErrorType.EmptySignatureReceived.libraryError

        }
        val decodedHeaderJson = JSONObject(LibraryUtils.decodeBase64ToString(encodedHeader))
        val signedCertificateURL = getSignedCertificateUrlFromHeaderJson(decodedHeaderJson)
        val algorithm = getAlgorithmFromHeaderJson(decodedHeaderJson)

        if (signedCertificateURL.isNullOrEmpty() || !URLUtil.isValidUrl(signedCertificateURL)) {
            throw LibraryErrorType.InvalidSignatureCertificateUrl.libraryError
        }

        if (dspManifestUrl.isNotEmpty() && URLUtil.isValidUrl(dspManifestUrl)) {
            val dspManifestUrlHost = URL(dspManifestUrl).host
            val signedCertificateURLHost = URL(signedCertificateURL).host
            if (!(dspManifestUrlHost.equals(signedCertificateURLHost, false))) {
                throw LibraryErrorType.InvalidSignatureCertificateUrl.libraryError
            }
        }

        if (algorithm.isNullOrEmpty()) {
            throw LibraryErrorType.InvalidSignatureAlgorithm.libraryError
        }
        return true
    }

    private fun getSignedCertificateUrlFromHeaderJson(headerJson: JSONObject): String? {
        return headerJson.getString(LibraryConstants.CERTIFICATE_URL_KEY)
    }

    private fun getAlgorithmFromHeaderJson(headerJson: JSONObject): String? {
        return headerJson.getString(LibraryConstants.ALGORITHM_KEY)
    }
}