package com.mastercard.creditorcommerce.library.utils

import android.os.Build
import android.util.Log
import com.google.gson.Gson
import com.mastercard.creditorcommerce.library.LibraryCallbackListener
import com.mastercard.creditorcommerce.library.exception.LibraryErrorType
import com.mastercard.creditorcommerce.library.models.DspManifestDataResponseModel
import java.util.*

internal object LibraryUtils {

    private val TAG = LibraryUtils::class.java.simpleName

    /**
     * Encode String to Base64
     * @param dataString: String the need to encode to base64
     * @return String : Base64 encoded string
     */
    fun encodeStringToBase64(dataString: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getEncoder().encodeToString(dataString.toByteArray())
        } else {
            return android.util.Base64.encodeToString(
                dataString.trim().toByteArray(),
                android.util.Base64.DEFAULT
            ).trimEnd()
        }
    }

    /**
     * Decode Base64 encoded string to String
     * @param encodedString: String: String that need to decode to string
     * @return String : Base64 decoded string
     */
    fun decodeBase64ToString(encodedString: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getDecoder().decode(encodedString).decodeToString()
        } else {
            android.util.Base64.decode(encodedString, android.util.Base64.DEFAULT)
                .decodeToString() // Unresolved reference: decode
        }
    }

    /**
     * This method parse the signed decoded Manifest data and convert it into DspListManifestDataResponseModel after data & signature validation
     * @param encodedPayloadData: String encoded payload data for dsp details
     * @param callbackListener: LibraryCallbackListener return decoded payload data ir LibraryError
     */
    fun parseSignedManifestPayloadData(
        encodedPayloadData: String,
        callbackListener: LibraryCallbackListener<DspManifestDataResponseModel>
    ) {
        //Extract Payload data from Manifest & Parse data
        try {
            val jsonManifestData = decodeBase64ToString(encodedPayloadData)
            Log.d(TAG, "Decoded Payload Data: ${jsonManifestData}")
            callbackListener.success(
                Gson().fromJson(
                    jsonManifestData,
                    DspManifestDataResponseModel::class.java
                )
            )
        } catch (ex: Exception) {
            //JSONSyntaxException
            callbackListener.failure(LibraryErrorType.InvalidEncoding.libraryError)
        }
    }
}