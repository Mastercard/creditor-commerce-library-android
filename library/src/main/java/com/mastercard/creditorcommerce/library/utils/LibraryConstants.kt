package com.mastercard.creditorcommerce.library.utils

internal object LibraryConstants {

    /*
    * lifeCycleId is key used in universal link generation to append transaction lifecycle id.
    * value of lifeCycleId in universal link is lc
    */
    internal const val LIFECYCLE_ID_KEY: String = "lc"

    /*
    * businessType is key used in universal link generation to append business type for transaction.
    * value of businessType in universal link is tb
    */
    internal const val BUSINESS_TYPE_KEY: String = "tb"

    /*
    * journeyType is key used in universal link generation to append journey type for transaction.
    * value of journeyType in universal link is uc
    */
    internal const val JOURNEY_TYPE_KEY: String = "uc"

    /*
    * Manifest signed data separator.
    */
    internal const val MANIFEST_DATA_SEPARATOR: String = "."

    /*
    * Signed Manifest data array length.
    */
    internal const val MANIFEST_DATA_ARRAY_SIZE: Int = 3

    /*
    * Signed Header Index in Manifest data ie. 0.
    */
    internal const val HEADER_INDEX_IN_MANIFEST_DATA: Int = 0

    /*
    *  Signed Payload Index in Manifest data ie. 1.
    */
    internal const val PAYLOAD_INDEX_IN_MANIFEST_DATA: Int = 1

    /*
    *  Signed Signature Index in Manifest data ie. 2.
    */
    internal const val SIGNATURE_INDEX_IN_MANIFEST_DATA: Int = 2


    /*
    *  Header certificate URL key.
    */
    internal const val CERTIFICATE_URL_KEY: String = "x5u"

    /*
    *  signed algorithm key.
    */
    internal const val ALGORITHM_KEY: String = "alg"
}