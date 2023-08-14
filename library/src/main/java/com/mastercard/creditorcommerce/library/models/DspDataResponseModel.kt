package com.mastercard.creditorcommerce.library.models

 /*
 * DSP details that received in Manifest data response
 */
 internal data class DspDataResponseModel(
    var dspName: String,
    var dspUseCaseType: Int,
    var dspLogo: String,
    var dspUniversalLink: String,
    var dspUniqueId: String,
    var dspLogoHash: String
)
