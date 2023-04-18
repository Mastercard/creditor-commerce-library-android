package com.mastercard.creditorcommerce.library

import com.mastercard.creditorcommerce.library.exception.LibraryError

/*
* Creditor Commerce Library callback listener
*/
interface LibraryCallbackListener<T : Any> {
    fun success(response: T)
    fun failure (error: LibraryError)
}