package com.mastercard.creditorcommerce.library

import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.mastercard.creditorcommerce.library.exception.LibraryError
import com.mastercard.creditorcommerce.library.models.DspDetails
import com.mastercard.creditorcommerce.library.utils.JourneyType
import io.mockk.every
import io.mockk.mockkObject
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CreditorCommerceLibraryWrapperTest {

    @Before
    fun beforeTest() {
        mockkObject(CreditorCommerceLibraryAPI)
    }

    @Test
    fun getDspDetails() {
        val callbackListener = object : LibraryCallbackListener<List<DspDetails>> {
            override fun success(response: List<DspDetails>) {
                assertNotNull(response)
            }

            override fun failure(error: LibraryError) {
                assertTrue(false)
            }
        }
        CreditorCommerceLibraryWrapper.getDspDetails(
            getApplicationContext(),
            TestConstants.VALID_MANIFEST_URL,
            callbackListener
        )
    }


    @Test
    fun generateUniversalLink() {

        val generateUniversalLinkcallbackListener = object : LibraryCallbackListener<String> {

            override fun success(response: String) {
                assertNotNull(response)
            }

            override fun failure(error: LibraryError) {
                assertTrue(false)
            }
        }
        every {
            CreditorCommerceLibraryAPI.generateUniversalLink(
                TestConstants.VALID_DSP_UNIQUE_ID,
                TestConstants.VALID_LIFECYCLE_ID,
                TestConstants.VALID_BUSINESS_TYPE,
                JourneyType.RequestToPay,
                generateUniversalLinkcallbackListener
            )
        } returns generateUniversalLinkcallbackListener.success("responsedata")

        CreditorCommerceLibraryWrapper.generateUniversalLink(
            TestConstants.VALID_DSP_UNIQUE_ID,
            TestConstants.VALID_LIFECYCLE_ID,
            TestConstants.VALID_BUSINESS_TYPE,
            JourneyType.RequestToPay,
            generateUniversalLinkcallbackListener
        )
    }


    @Test
    fun invokeApp() {

        val invokeAppCallbackListener = object : LibraryCallbackListener<Boolean> {
            override fun success(response: Boolean) {
                assertNotNull(response)
            }

            override fun failure(error: LibraryError) {
                assertTrue(false)
            }
        }

        every {
            CreditorCommerceLibraryAPI.invokeApp(
                getApplicationContext(),
                TestConstants.VALID_DSP_UNIQUE_ID,
                TestConstants.VALID_LIFECYCLE_ID,
                TestConstants.VALID_BUSINESS_TYPE,
                JourneyType.RequestToPay,
                invokeAppCallbackListener
            )
        } returns invokeAppCallbackListener.success(true)

        CreditorCommerceLibraryWrapper.invokeApp(
            getApplicationContext(),
            TestConstants.VALID_DSP_UNIQUE_ID,
            TestConstants.VALID_LIFECYCLE_ID,
            TestConstants.VALID_BUSINESS_TYPE,
            JourneyType.RequestToPay,
            invokeAppCallbackListener
        )
    }
}