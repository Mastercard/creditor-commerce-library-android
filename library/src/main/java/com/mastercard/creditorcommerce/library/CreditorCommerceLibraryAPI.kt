package com.mastercard.creditorcommerce.library

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.mastercard.creditorcommerce.library.exception.LibraryError
import com.mastercard.creditorcommerce.library.exception.LibraryErrorType
import com.mastercard.creditorcommerce.library.models.DspDataResponseModel
import com.mastercard.creditorcommerce.library.models.DspManifestDataResponseModel
import com.mastercard.creditorcommerce.library.utils.JourneyType
import com.mastercard.creditorcommerce.library.utils.LibraryConstants
import com.mastercard.creditorcommerce.library.utils.LibraryUtils
import com.mastercard.creditorcommerce.library.utils.ValidationUtils
import com.mastercard.creditorcommerce.library.webapiservice.repository.CreditorCommerceLibraryRepository
import com.mastercard.creditorcommerce.library.webapiservice.repository.CreditorCommerceLibraryRepositoryImpl
import java.net.URLDecoder

internal object CreditorCommerceLibraryAPI {
    private val TAG = CreditorCommerceLibraryAPI::class.java.simpleName
    private var CreditorCommerceLibraryRepository: CreditorCommerceLibraryRepository = CreditorCommerceLibraryRepositoryImpl()

    /*
    * Temporary storing data list of DSP which is goin to be used in GenerateLink & Invoke app functionality
    */
    private var dspList: List<DspDataResponseModel>? = null

    /**
     * getDspDetails() return List of Dsp details to merchant form Url provided in parameter
     * @param context : Android context required to make API call
     * @param dspManifestUrl: It is Manifest file URL which contain list of available DSP's
     * @param callbackListener Library callback return Success & failure response to Merchant
     * @param isVerificationRequired: Boolean = true this is optional parameter for skip signing verification while fetching DSP details
     */
    fun getDspDetails(context: Context, dspManifestUrl: String, callbackListener: LibraryCallbackListener<List<DspDataResponseModel>>, isVerificationRequired: Boolean) {

        ValidationUtils.validateManifestURL(dspManifestUrl).let { it ->
            when (it) {
                is LibraryError -> {
                    callbackListener.failure(it)
                }
                else -> {
                    CreditorCommerceLibraryRepository.getManifestRAWData(
                            dspManifestUrl,
                            context,
                            object : LibraryCallbackListener<DspManifestDataResponseModel> {
                                override fun success(response: DspManifestDataResponseModel) {

                                    ValidationUtils.validateDspAPIResponse(response.apps).let {
                                        when (it) {
                                            is LibraryError -> {
                                                callbackListener.failure(it)
                                            }
                                            else -> {
                                                dspList = response.apps
                                                callbackListener.success(response.apps)
                                            }
                                        }
                                    }
                                }

                                override fun failure(error: LibraryError) {
                                    callbackListener.failure(error)
                                }
                            },
                            isVerificationRequired)
                }
            }
        }
    }

    /**
     * generateUniversalLink() Generate parametrised universal link for selected DSP
     * @param dspId: Id of selected DSP which universal link needs to be generate
     * @param lifeCycleId: Payment lifecycle selected payment
     * @param journeyType: payment journey type
     * @param callbackListener Library callback return Success & failure response to Merchant
     */
    fun generateUniversalLink(dspId: String, lifeCycleId: String, businessType: Int, journeyType: JourneyType,
                              callbackListener: LibraryCallbackListener<String>) {

        ValidationUtils.validateUniversalLinkData(dspId, lifeCycleId, journeyType, businessType).let {
            when (it) {
                is LibraryError -> {
                    callbackListener.failure(it)
                }
                else -> {

                    val dspUniversalLink = dspList?.find { it.dspUniqueId == dspId }?.dspUniversalLink
                    if (!dspUniversalLink.isNullOrEmpty()) {
                        callbackListener.success(buildParametrisedUniversalLink(dspUniversalLink, lifeCycleId, businessType, journeyType))
                    } else {
                        callbackListener.failure(LibraryErrorType.NoDataPresentForDspID.libraryError)
                    }

                }
            }
        }
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

        ValidationUtils.validateUniversalLinkData(dspId, lifeCycleId, journeyType, businessType).let {
            when (it) {
                is LibraryError -> {
                    callbackListener.failure(it)
                }
                else -> {
                    val dspUniversalLink = dspList?.find { it.dspUniqueId == dspId }?.dspUniversalLink
                    if (!dspUniversalLink.isNullOrEmpty()) {
                        val confirmIntent = Intent(Intent.ACTION_VIEW, Uri.parse(buildParametrisedUniversalLink(dspUniversalLink, lifeCycleId, businessType, journeyType)))
                        confirmIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        try {
                            context.startActivity(confirmIntent)
                            callbackListener.success(true)
                        } catch (ex: Exception) {
                            callbackListener.failure(LibraryErrorType.UnableToInvokeDSP.libraryError)
                        }
                    } else {
                        callbackListener.failure(LibraryErrorType.NoDataPresentForDspID.libraryError)
                    }
                }
            }
        }
    }

    /**
     * This function build a universal link from given validated data and return the generated Universal link
     * In order to make universal link unreadable replace key by another key and encode value to Base64
     * @param universalLink : universal link for selected DSP's
     * @param dspId: Id of selected DSP which universal link needs to be generate
     * @param lifeCycleId: Payment lifecycle selected payment
     * @param journeyType: payment journey type
     * @return Generated universal link in as String
     */
    private fun buildParametrisedUniversalLink(universalLink: String, lifeCycleId: String, businessType: Int, journeyType: JourneyType): String {
        val url = Uri.parse(universalLink).buildUpon()
                .appendQueryParameter(LibraryConstants.LIFECYCLE_ID_KEY, LibraryUtils.encodeStringToBase64(lifeCycleId))
                .appendQueryParameter(LibraryConstants.BUSINESS_TYPE_KEY, LibraryUtils.encodeStringToBase64(businessType.toString()))
                .appendQueryParameter(LibraryConstants.JOURNEY_TYPE_KEY, LibraryUtils.encodeStringToBase64(journeyType.journeyType))
                .build()
        return URLDecoder.decode(url.toString(), "UTF-8")
    }
}

















