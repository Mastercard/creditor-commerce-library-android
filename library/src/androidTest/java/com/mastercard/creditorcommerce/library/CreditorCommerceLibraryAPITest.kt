package com.mastercard.creditorcommerce.library
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.mastercard.creditorcommerce.library.exception.LibraryError
import com.mastercard.creditorcommerce.library.models.DspDataResponseModel
import com.mastercard.creditorcommerce.library.models.DspManifestDataResponseModel
import com.mastercard.creditorcommerce.library.utils.JourneyType
import io.mockk.every
import io.mockk.mockkObject
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.lang.reflect.Field

class CreditorCommerceLibraryAPITest {
    private val dspDataResponse = DspDataResponseModel(
        TestConstants.VALID_DSP_NAME,
        TestConstants.VALID_USE_CASE_TYPE,
        TestConstants.VALID_DSP_LOGO_URL,
        TestConstants.VALID_DSP_UNIVERSAL_LINK,
        TestConstants.VALID_DSP_UNIQUE_ID,
        TestConstants.VALID_DSP_LOGO_HASH
    )

    @Before
    fun beforeTest() {
        mockkObject(CreditorCommerceLibraryAPI.CreditorCommerceLibraryRepository)
    }

    @Test
    fun getDspDetails() {

        val getDspDetailsCallbackListener =
            object : LibraryCallbackListener<DspManifestDataResponseModel> {
                override fun success(response: DspManifestDataResponseModel) {
                    assertNotNull(response)
                }

                override fun failure(error: LibraryError) {
                    assertTrue(false)
                }
            }

        every {
            CreditorCommerceLibraryAPI.CreditorCommerceLibraryRepository.getManifestRAWData(
                TestConstants.VALID_MANIFEST_URL,
                getApplicationContext(),
                getDspDetailsCallbackListener, true
            )
        } returns getDspDetailsCallbackListener.success(
            DspManifestDataResponseModel(1,  listOf(dspDataResponse))
        )

        CreditorCommerceLibraryAPI.getDspDetails(
            getApplicationContext(),
            TestConstants.VALID_MANIFEST_URL,
            object : LibraryCallbackListener<List<DspDataResponseModel>> {
                override fun success(response: List<DspDataResponseModel>) {
                    assertTrue(response.isNotEmpty())
                }

                override fun failure(error: LibraryError) {
                    assertTrue(false)
                }
            },
            true
        )
    }

    @Test
    fun generateUniversalLink() {
        val dspdataResponseList = listOf(dspDataResponse)

        val dspList: Field = CreditorCommerceLibraryAPI::class.java.getDeclaredField("dspList")
        dspList.isAccessible = true
        dspList.set(String, dspdataResponseList)

        val generateUniversalLinkcallbackListener = object : LibraryCallbackListener<String> {
            override fun success(response: String) {
                assertNotNull(response)
            }

            override fun failure(error: LibraryError) {
                assertTrue(false)
            }
        }

        CreditorCommerceLibraryAPI.generateUniversalLink(
            TestConstants.VALID_DSP_UNIQUE_ID,
            TestConstants.VALID_LIFECYCLE_ID,
            TestConstants.VALID_BUSINESS_TYPE,
            JourneyType.RequestToPay,
            generateUniversalLinkcallbackListener
        )
    }

    @Test
    fun invokeApp() {

        val dspdataResponseList = listOf(dspDataResponse)

        val dspList: Field = CreditorCommerceLibraryAPI::class.java.getDeclaredField("dspList")
        dspList.isAccessible = true
        dspList.set(String, dspdataResponseList)

        val invokeAppCallbackListener = object : LibraryCallbackListener<Boolean> {
            override fun success(response: Boolean) {
                assertNotNull(response)
            }

            override fun failure(error: LibraryError) {
                assertTrue(false)
            }
        }

        CreditorCommerceLibraryAPI.invokeApp(
            getApplicationContext(),
            TestConstants.VALID_DSP_UNIQUE_ID,
            TestConstants.VALID_LIFECYCLE_ID,
            TestConstants.VALID_BUSINESS_TYPE,
            JourneyType.RequestToPay,
            invokeAppCallbackListener
        )
    }
}
