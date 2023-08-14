package com.mastercard.creditorcommerce.library.utils

import com.mastercard.creditorcommerce.library.TestConstants
import com.mastercard.creditorcommerce.library.exception.LibraryError
import com.mastercard.creditorcommerce.library.models.DspDataResponseModel
import org.junit.Assert
import org.junit.Test

class ValidationUtilsTest {

    @Test
    fun validateManifestURL() {
        //Empty URL
        ValidationUtils.validateManifestURL(TestConstants.EMPTY_VALUE).let {
            when (it) {
                is LibraryError -> {
                    Assert.assertTrue(true)
                }
                else -> {
                    Assert.assertTrue(false)
                }
            }
        }

        //Invalid Protocol
        ValidationUtils.validateManifestURL(TestConstants.URL_WITHOUT_SECURE_PROTOCOL).let {
            when (it) {
                is LibraryError -> {
                    Assert.assertTrue(true)
                }
                else -> {
                    Assert.assertTrue(false)
                }
            }
        }

    }

    @Test
    fun validateUniversalLinkData() {
        //Empty DSP ID
        ValidationUtils.validateUniversalLinkData(
            TestConstants.EMPTY_VALUE,
            TestConstants.VALID_LIFECYCLE_ID,
            JourneyType.RequestToPay,
            TestConstants.VALID_BUSINESS_TYPE
        ).let {
            when (it) {
                is LibraryError -> {
                    Assert.assertTrue(true)
                }
                else -> {
                    Assert.assertTrue(false)
                }
            }
        }

        //Empty lifecycle ID
        ValidationUtils.validateUniversalLinkData(
            TestConstants.VALID_DSP_UNIQUE_ID,
            TestConstants.EMPTY_VALUE,
            JourneyType.RequestToPay,
            TestConstants.VALID_BUSINESS_TYPE
        ).let {
            when (it) {
                is LibraryError -> {
                    Assert.assertTrue(true)
                }
                else -> {
                    Assert.assertTrue(false)
                }
            }
        }

        //Invalid  Business Type
        ValidationUtils.validateUniversalLinkData(
            TestConstants.VALID_DSP_UNIQUE_ID,
            TestConstants.VALID_LIFECYCLE_ID,
            JourneyType.RequestToPay,
            TestConstants.INVALID_BUSINESS_TYPE
        ).let {
            when (it) {
                is LibraryError -> {
                    Assert.assertTrue(true)
                }
                else -> {
                    Assert.assertTrue(false)
                }
            }
        }
    }

    @Test
    fun validateDspAPIResponse() {

        ValidationUtils.validateDspAPIResponse(
            listOf(
                DspDataResponseModel(
                    TestConstants.EMPTY_VALUE,
                    TestConstants.VALID_USE_CASE_TYPE,
                    TestConstants.VALID_DSP_LOGO_URL,
                    TestConstants.VALID_DSP_UNIVERSAL_LINK,
                    TestConstants.VALID_DSP_UNIQUE_ID,
                    TestConstants.VALID_DSP_LOGO_HASH
                )
            )
        ).let {
            when (it) {
                is LibraryError -> {
                    Assert.assertTrue(true)
                }
                else -> {
                    Assert.assertTrue(false)
                }
            }
        }

        ValidationUtils.validateDspAPIResponse(
            listOf(
                DspDataResponseModel(
                    TestConstants.VALID_DSP_NAME,
                    TestConstants.VALID_USE_CASE_TYPE,
                    TestConstants.EMPTY_VALUE,
                    TestConstants.VALID_DSP_UNIVERSAL_LINK,
                    TestConstants.VALID_DSP_UNIQUE_ID,
                    TestConstants.VALID_DSP_LOGO_HASH
                )
            )
        ).let {
            when (it) {
                is LibraryError -> {
                    Assert.assertTrue(true)
                }
                else -> {
                    Assert.assertTrue(false)
                }
            }
        }

        ValidationUtils.validateDspAPIResponse(
            listOf(
                DspDataResponseModel(
                    TestConstants.VALID_DSP_NAME,
                    TestConstants.VALID_USE_CASE_TYPE,
                    TestConstants.VALID_DSP_LOGO_URL,
                    TestConstants.EMPTY_VALUE,
                    TestConstants.VALID_DSP_UNIQUE_ID,
                    TestConstants.VALID_DSP_LOGO_HASH
                )
            )
        ).let {
            when (it) {
                is LibraryError -> {
                    Assert.assertTrue(true)
                }
                else -> {
                    Assert.assertTrue(false)
                }
            }
        }

        ValidationUtils.validateDspAPIResponse(
            listOf(
                DspDataResponseModel(
                    TestConstants.VALID_DSP_NAME,
                    TestConstants.VALID_USE_CASE_TYPE,
                    TestConstants.VALID_DSP_LOGO_URL,
                    TestConstants.VALID_DSP_UNIVERSAL_LINK,
                    TestConstants.EMPTY_VALUE,
                    TestConstants.VALID_DSP_LOGO_HASH
                )
            )
        ).let {
            when (it) {
                is LibraryError -> {
                    Assert.assertTrue(true)
                }
                else -> {
                    Assert.assertTrue(false)
                }
            }
        }

        ValidationUtils.validateDspAPIResponse(
            listOf(
                DspDataResponseModel(
                    TestConstants.VALID_DSP_NAME,
                    TestConstants.VALID_USE_CASE_TYPE,
                    TestConstants.VALID_DSP_LOGO_URL,
                    TestConstants.VALID_DSP_UNIVERSAL_LINK,
                    TestConstants.VALID_DSP_UNIQUE_ID,
                    TestConstants.EMPTY_VALUE
                )
            )
        ).let {
            when (it) {
                is LibraryError -> {
                    Assert.assertTrue(true)
                }
                else -> {
                    Assert.assertTrue(false)
                }
            }
        }

    }

    @Test
    fun validateSignedManifestAndPayloadData() {
        ValidationUtils.validateSignedManifestAndPayloadData(
            TestConstants.EMPTY_VALUE
        ).let {
            when (it) {
                is LibraryError -> {
                    Assert.assertTrue(true)
                }
                else -> {
                    Assert.assertTrue(false)
                }
            }
        }

        ValidationUtils.validateSignedManifestAndPayloadData(
            TestConstants.INVALID_MANIFEST_FORMAT
        ).let {
            when (it) {
                is LibraryError -> {
                    Assert.assertTrue(true)
                }
                else -> {
                    Assert.assertTrue(false)
                }
            }
        }

        ValidationUtils.validateSignedManifestAndPayloadData(
            TestConstants.INVALID_MANIFEST_FORMAT_PAYLOAD
        ).let {
            when (it) {
                is LibraryError -> {
                    Assert.assertTrue(true)
                }
                else -> {
                    Assert.assertTrue(false)
                }
            }
        }

    }
}