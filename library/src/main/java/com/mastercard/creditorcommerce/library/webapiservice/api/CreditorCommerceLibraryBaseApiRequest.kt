package com.mastercard.creditorcommerce.library.webapiservice.api

import android.content.Context
import com.android.volley.ClientError
import com.android.volley.NoConnectionError
import com.android.volley.Request
import com.android.volley.TimeoutError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.mastercard.creditorcommerce.library.LibraryCallbackListener
import com.mastercard.creditorcommerce.library.exception.LibraryErrorType


internal abstract class CreditorCommerceLibraryBaseApiRequest {

    companion object {
        private val TAG = CreditorCommerceLibraryBaseApiRequest::class.simpleName.toString()
    }

    /**
     * make API call oin given url and return result as a string in callback
     * @param url: String API url
     * @param context: Android context required to make API call
     * @return callbackListener Library callback return Success & failure response
     */
    fun callAPI(
            url: String,
            context: Context,
            callbackListener: LibraryCallbackListener<String>
    ) {
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    callbackListener.success(response)
                },
                { error ->
                    if (error is NoConnectionError) {
                        callbackListener.failure(LibraryErrorType.NoConnectionError.libraryError)
                    } else if (error is TimeoutError) {
                        callbackListener.failure(LibraryErrorType.TimeoutError.libraryError)
                    } else if (error is ClientError) {
                        callbackListener.failure(LibraryErrorType.ClientError.libraryError)
                    } else {
                        callbackListener.failure(LibraryErrorType.UnknownTechnicalError.libraryError)
                    }
                })
        queue.add(stringRequest)
    }
}