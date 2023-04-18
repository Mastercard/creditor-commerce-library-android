package com.mastercard.creditorcommerce.library

import android.content.Context
import com.mastercard.creditorcommerce.library.exception.LibraryError
import com.mastercard.creditorcommerce.library.models.DspDataResponseModel
import com.mastercard.creditorcommerce.library.models.DspDetails
import com.mastercard.creditorcommerce.library.utils.JourneyType

object CreditorCommerceLibraryWrapper {

    /**
     * getDspDetails() return List of Dsp details to merchant form Url provided in parameter
     * @param context : Android context required to make API call
     * @param dspManifestUrl: It is Manifest file URL which contain list of available DSP's
     * @param callbackListener Library callback return Success & failure response to Merchant
     * @param isVerificationRequired: Boolean = true this is optional parameter for skip signing verification while fetching DSP details
     */
    fun getDspDetails(context: Context, dspManifestUrl: String, callbackListener: LibraryCallbackListener<List<DspDetails>>, isVerificationRequired: Boolean = false) {
        CreditorCommerceLibraryAPI.getDspDetails(context, dspManifestUrl, object : LibraryCallbackListener<List<DspDataResponseModel>> {
            override fun success(response: List<DspDataResponseModel>) {
                val dspList = mutableListOf<DspDetails>()
                for (dsp in response) {
                    dspList.add(DspDetails(dsp.dspName,
                            dsp.dspApiVersion,
                            dsp.dspLogo,
                            dsp.dspUniqueId,
                            dsp.appIconHash))
                }
                callbackListener.success(dspList)
            }

            override fun failure(error: LibraryError) {
                callbackListener.failure(error)
            }

        }, isVerificationRequired)
    }

    /**
     * generateUniversalLink() Generate parametrised universal link for selected DSP
     * @param dspId: Id of selected DSP which universal link needs to be generate
     * @param lifeCycleId: Payment lifecycle selected payment
     * @param journeyType: payment journey type
     * @param callbackListener Library callback return Success & failure response to Merchant
     */
    fun generateUniversalLink(dspId: String,
                              lifeCycleId: String,
                              businessType: Int,
                              journeyType: JourneyType,
                              callbackListener: LibraryCallbackListener<String>) {
        CreditorCommerceLibraryAPI.generateUniversalLink(dspId, lifeCycleId, businessType, journeyType,
                callbackListener)
    }

    /**
     * invokeApp() Invoke dsp app by generating universal link
     * @param context : Android context required to make API call
     * @param dspId: Id of selected DSP which universal link needs to be generate
     * @param lifeCycleId: Payment lifecycle selected payment
     * @param journeyType: payment journey type
     * @param callbackListener Library callback return Success & failure response to Merchant
     */
    fun invokeApp(context: Context, dspId: String, lifeCycleId: String, businessType: Int, journeyType: JourneyType,
                  callbackListener: LibraryCallbackListener<Boolean>) {
        CreditorCommerceLibraryAPI.invokeApp(context, dspId, lifeCycleId, businessType, journeyType, callbackListener)
    }
}