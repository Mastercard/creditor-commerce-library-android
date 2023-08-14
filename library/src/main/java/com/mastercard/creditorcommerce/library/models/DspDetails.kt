package com.mastercard.creditorcommerce.library.models

/*
* DSP details that will shared merchants.
*/
data class DspDetails (
    var dspName: String,
    var dspUseCaseType: Int,
    var dspLogo: String,
    var dspUniqueId: String,
    var dspLogoHash: String
)