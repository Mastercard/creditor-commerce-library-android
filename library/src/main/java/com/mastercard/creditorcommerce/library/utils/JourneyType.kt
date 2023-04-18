package com.mastercard.creditorcommerce.library.utils

enum class JourneyType(val journeyType: String) {
    /*
    * Journey type RequestToPay
    */
    RequestToPay("rx"),

    /*
    * Journey type RequestToLink
    */
    RequestToLink("ry"),
}