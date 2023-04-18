package com.mastercard.creditorcommerce.library.exception

/*
* LibraryError ho handle error in creditor commerce library.
*/
class LibraryError(val errorCode: Int?, val errorMessage: String?) : Exception()