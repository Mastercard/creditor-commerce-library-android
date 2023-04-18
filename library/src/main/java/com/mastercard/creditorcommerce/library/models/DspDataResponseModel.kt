package com.mastercard.creditorcommerce.library.models

 /*
 * DSP details that received in Manifest data response
 */
 internal data class DspDataResponseModel(
    var dspName: String,
    var dspApiVersion: Int,
    var dspLogo: String,
    var dspUniversalLink: String,
    var dspUniqueId: String,
    var appIconHash: String
)
