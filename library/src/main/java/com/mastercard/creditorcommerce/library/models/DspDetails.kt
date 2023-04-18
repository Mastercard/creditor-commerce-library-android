package com.mastercard.creditorcommerce.library.models

/*
* DSP details that will shared merchants.
*/
data class DspDetails (
    var dspName: String,
    var dspApiVersion: Int,
    var dspLogo: String,
    var dspUniqueId: String,
    var appIconHash: String
)