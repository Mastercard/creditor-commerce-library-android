package com.mastercard.creditorcommerce.library.utils

import com.mastercard.creditorcommerce.library.LibraryCallbackListener
import com.mastercard.creditorcommerce.library.TestConstants
import com.mastercard.creditorcommerce.library.exception.LibraryError
import com.mastercard.creditorcommerce.library.models.DspManifestDataResponseModel
import org.junit.Assert.*
import org.junit.Test

class LibraryUtilsTest {

    @Test
    fun encodeStringToBase64() {
        val stringToEncode = LibraryUtils.encodeStringToBase64("testinputstring")
        val expectedResult = "dGVzdGlucHV0c3RyaW5n"
        assertEquals(expectedResult, stringToEncode)
    }

    @Test
    fun decodeBase64ToString() {
        val stringToEncode = LibraryUtils.decodeBase64ToString("dGVzdGlucHV0c3RyaW5n")
        val expectedResult = "testinputstring"
        assertEquals(expectedResult, stringToEncode)
    }

    @Test
    fun parseSignedManifestPayloadData() {

        val callbackListener = object : LibraryCallbackListener<DspManifestDataResponseModel> {
            override fun success(response: DspManifestDataResponseModel) {
                assertNotNull(response)
            }

            override fun failure(error: LibraryError) {
                assertTrue(false)
            }
        }

        LibraryUtils.parseSignedManifestPayloadData(
            TestConstants.VALID_MANIFEST_PAYLOAD,
            callbackListener
        )

    }
}