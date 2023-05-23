package com.mastercard.creditorcommerce.library.webapiservice.repository

import android.content.Context
import com.mastercard.creditorcommerce.library.LibraryCallbackListener
import com.mastercard.creditorcommerce.library.exception.LibraryError
import com.mastercard.creditorcommerce.library.exception.LibraryErrorType
import com.mastercard.creditorcommerce.library.models.DspManifestDataResponseModel
import com.mastercard.creditorcommerce.library.utils.LibraryConstants
import com.mastercard.creditorcommerce.library.utils.LibraryUtils
import com.mastercard.creditorcommerce.library.utils.ValidationUtils
import com.mastercard.creditorcommerce.library.webapiservice.api.CreditorCommerceLibraryBaseApiRequest

internal class CreditorCommerceLibraryRepositoryImpl : CreditorCommerceLibraryBaseApiRequest(),
    CreditorCommerceLibraryRepository {

    companion object {
        private val TAG = CreditorCommerceLibraryRepositoryImpl::class.java.simpleName
    }

    /**
     * getManifestRAWData() return List of Dsp details to merchant form Url provided in parameter
     * @param dspManifestUrl: It is Manifest file URL which contain list of available DSP's
     * @param context : Android context required to make API call
     * @param callbackListener Library callback return Success & failure response after validating data
     * @param isVerificationRequired: Boolean = true this is optional parameter for skip signing verification while fetching DSP details
     */
    override fun getManifestRAWData(
        dspManifestUrl: String,
        context: Context,
        callbackListener: LibraryCallbackListener<DspManifestDataResponseModel>,
        isVerificationRequired: Boolean
    ) {
        callAPI(dspManifestUrl, context, object : LibraryCallbackListener<String> {
            override fun success(response: String) {

                ValidationUtils.validateSignedManifestAndPayloadData(response).let {
                    when (it) {
                        is LibraryError -> {
                            callbackListener.failure(it)
                        }
                        else -> {
                            if (isVerificationRequired) {
                                SignatureValidator().verifySignatureAndParseData(context, response, callbackListener)
                                return
                            }
                            LibraryUtils.parseSignedManifestPayloadData(
                                response.split(
                                    LibraryConstants.MANIFEST_DATA_SEPARATOR
                                )[LibraryConstants.PAYLOAD_INDEX_IN_MANIFEST_DATA], callbackListener
                            )
                        }
                    }
                }
            }

            override fun failure(error: LibraryError) {
                callbackListener.failure(error)
            }
        })
    }


    /**
     * getSignedCertificateFromURL() return signing certificate from URL
     * @param signingCertificateURL: It is signing certificate URL
     * @param context : Android context required to make API call
     * @param callbackListener Library callback return Success & failure response after validating data
     **/
    override fun getSignedCertificateFromURL(signingCertificateURL: String, context: Context, callbackListener: LibraryCallbackListener<String>) {
        callAPI(signingCertificateURL, context, object : LibraryCallbackListener<String> {
            override fun success(response: String) {
                if (response.isEmpty()) {
                    callbackListener.failure(LibraryErrorType.EmptySigningCertificateReceived.libraryError)
                    return
                }
                callbackListener.success(response)
            }
            override fun failure(error: LibraryError) {
                callbackListener.failure(error)
            }
        })
    }
}