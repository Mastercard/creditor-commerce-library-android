package com.mastercard.creditorcommerce.library.utils

import android.webkit.URLUtil
import com.mastercard.creditorcommerce.library.exception.LibraryErrorType
import com.mastercard.creditorcommerce.library.models.DspDataResponseModel

internal object ValidationUtils {

    /**
     * Validate Manifest URL
     * @param manifestURL: String dsp manifest URL which contain signed DSP details.
     * @return Any : Return true if URL is correct if false the return LibraryError.
     */
    internal fun validateManifestURL(manifestURL: String): Any {
        if (manifestURL.isEmpty() || !URLUtil.isValidUrl(manifestURL)) {
            return LibraryErrorType.InvalidDspManifestUrl.libraryError
        }
        if (!URLUtil.isHttpsUrl(manifestURL)) {
            return LibraryErrorType.InvalidSecureProtocol.libraryError
        }
        return true
    }

    /**
     * TValidate data that required to generate universal link
     * @param dspId: Id of selected DSP which universal link needs to be generate
     * @param lifeCycleId: Payment lifecycle selected payment
     * @param journeyType: payment journey type
     * @param businessType: payment business type
     * @return Return true if data is correct if false the return LibraryError.
     */
    internal fun validateUniversalLinkData(
        dspId: String,
        lifeCycleId: String,
        journeyType: JourneyType,
        businessType: Int
    ): Any {
        if (dspId.isEmpty()) {
            return LibraryErrorType.InvalidDspID.libraryError
        }

        if (lifeCycleId.isEmpty()) {
            return LibraryErrorType.InvalidLifecycleId.libraryError
        }

        if (journeyType != JourneyType.RequestToLink && journeyType != JourneyType.RequestToPay) {
            return LibraryErrorType.InvalidJourneyType.libraryError
        }

        if (businessType == 0) {
            return LibraryErrorType.InvalidBusinessType.libraryError
        }
        return true
    }

    /**
     * Validate DSP details received in Manifest API Response
     * @param response: List of DSP received in dsp manifest data.
     * @return Any : Return true if received dsp data is correct if false the return LibraryError.
     */
    internal fun validateDspAPIResponse(response: List<DspDataResponseModel>): Any {
        if (response.isEmpty()) {
            return LibraryErrorType.DSPListFileIsEmpty.libraryError
        }

        //validate data for all dsp's
        for (dsp in response) {
            if (dsp.dspName.isEmpty()) {
                return LibraryErrorType.InvalidDSPName.libraryError
            }

            if (dsp.dspLogo.isEmpty()) {
                return LibraryErrorType.InvalidDSPLogo.libraryError
            }
            if (dsp.dspUniversalLink.isEmpty()) {
                return LibraryErrorType.InvalidDSPUniversalLink.libraryError
            }

            if (dsp.dspUniqueId.isEmpty()) {
                return LibraryErrorType.InvalidDSPUniqueId.libraryError
            }

            if (dsp.dspLogoHash.isEmpty()) {
                return LibraryErrorType.InvalidDspLogoHash.libraryError
            }
        }
        return true
    }

    /**
     * Validate Signed manifest Data and payload data details received in Manifest API Response
     * @param manifestSignedData: manifest signed data
     * @return Any : Return List of bytes if data is valid else return LibraryError.
     */
    internal fun validateSignedManifestAndPayloadData(manifestSignedData: String): Any {
        if (manifestSignedData.isEmpty()) {
            return LibraryErrorType.EmptyStringReceived.libraryError
        }
        val signedDataList = manifestSignedData.split(LibraryConstants.MANIFEST_DATA_SEPARATOR)
        //Validate Signed Manifest Data
        if (signedDataList.isEmpty() || signedDataList.size != LibraryConstants.MANIFEST_DATA_ARRAY_SIZE) {
            return LibraryErrorType.InvalidManifestDataReceived.libraryError
        }

        //Extract Payload from signed manifest content it on second position in signed content
        val encodedPayloadData = signedDataList[LibraryConstants.PAYLOAD_INDEX_IN_MANIFEST_DATA]
        if (encodedPayloadData.isEmpty()) {
            return LibraryErrorType.EmptyPayloadReceived.libraryError
        }
        return true
    }

}