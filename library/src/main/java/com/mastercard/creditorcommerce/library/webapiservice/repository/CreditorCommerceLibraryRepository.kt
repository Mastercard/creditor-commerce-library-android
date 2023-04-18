package com.mastercard.creditorcommerce.library.webapiservice.repository

import android.content.Context
import com.mastercard.creditorcommerce.library.LibraryCallbackListener
import com.mastercard.creditorcommerce.library.models.DspManifestDataResponseModel

internal interface CreditorCommerceLibraryRepository {
    /**
     * getManifestRAWData() return List of Dsp details to merchant form Url provided in parameter
     * @param dspManifestUrl: It is Manifest file URL which contain list of available DSP's
     * @param context : Android context required to make API call
     * @param callbackListener Library callback return Success & failure response after validating data
     * @param isVerificationRequired: Boolean = true this is optional parameter for skip signing verification while fetching DSP details
     */
    fun getManifestRAWData(dspManifestUrl: String, context: Context, callbackListener: LibraryCallbackListener<DspManifestDataResponseModel>, isVerificationRequired: Boolean)

}