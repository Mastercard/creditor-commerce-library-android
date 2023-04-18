package com.mastercard.creditorcommerce.library.models

/*
* Manifest details that received in Manifest data response
*/
 internal data class DspManifestDataResponseModel constructor(
    var dspListVersion: Int,
    var cacheLifetime: Int,
    var apps: List<DspDataResponseModel>
)
