package com.mastercard.creditorcommerce.library

import com.mastercard.creditorcommerce.library.exception.LibraryError
import com.mastercard.creditorcommerce.library.utils.JourneyType
import com.mastercard.creditorcommerce.library.utils.ValidationUtils
import org.junit.Assert
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class LibraryApiTest {

    companion object {
        private val TAG: String = LibraryApiTest::class.simpleName.toString()
    }

    @Test
    fun basicValidationTest() {
        //Empty URL
        ValidationUtils.validateManifestURL(TestData.INVALID_MANIFEST_URL).let {
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
        ValidationUtils.validateManifestURL(TestData.URL_WITHOUT_SECURE_PROTOCOL).let {
            when (it) {
                is LibraryError -> {
                    Assert.assertTrue(true)
                }
                else -> {
                    Assert.assertTrue(false)
                }
            }
        }

        //Valid URL Test
        ValidationUtils.validateManifestURL(TestData.VALID_MANIFEST_URL)
                .let {
                    when (it) {
                        is LibraryError -> {
                            Assert.assertTrue(false)
                        }
                        else -> {
                            Assert.assertTrue(true)
                        }
                    }
                }

        //Empty DSP ID
        ValidationUtils.validateUniversalLinkData(TestData.INVALID_DSP_ID, "", JourneyType.RequestToPay, 3).let {
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
                TestData.VALID_DSP_ID,
                TestData.INVALID_LIFECYCLE_ID,
                JourneyType.RequestToPay,
                3
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
    fun manifestDataValidationTest() {
        //Invalid Manifest Format
        ValidationUtils.validateSignedManifestAndPayloadData(TestData.INVALID_MANIFEST_FORMAT).let {
            when (it) {
                is LibraryError -> {
                    Assert.assertTrue(true)
                }
                else -> {
                    Assert.assertTrue(false)
                }
            }
        }

        //Invalid Manifest Format Payload
        ValidationUtils.validateSignedManifestAndPayloadData(TestData.INVALID_MANIFEST_FORMAT_PAYLOAD).let {
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

/*
* Hardcoded data which is used in Test cases.
*/
object TestData {
    const val INVALID_MANIFEST_URL = ""
    const val URL_WITHOUT_SECURE_PROTOCOL = "http://zts4test.co.uk"
    const val VALID_MANIFEST_URL = "https://zts4test.co.uk/zapp-creditor-commerce/cdn-files/dsplist-signed"

    const val INVALID_DSP_ID = ""
    const val WRONG_DSP_ID = "xyx"
    const val VALID_DSP_ID = "partnerBankV5001"

    const val INVALID_LIFECYCLE_ID = ""

    const val INVALID_MANIFEST_FORMAT = "asd.fgh"
    const val INVALID_MANIFEST_FORMAT_PAYLOAD = "qwe..asd"
}
