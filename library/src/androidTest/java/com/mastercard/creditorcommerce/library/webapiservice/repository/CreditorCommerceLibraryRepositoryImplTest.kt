package com.mastercard.creditorcommerce.library.webapiservice.repository

import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.mastercard.creditorcommerce.library.LibraryCallbackListener
import com.mastercard.creditorcommerce.library.TestConstants
import com.mastercard.creditorcommerce.library.exception.LibraryError
import com.mastercard.creditorcommerce.library.models.DspManifestDataResponseModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test


class CreditorCommerceLibraryRepositoryImplTest {

    @Test
    fun getManifestRAWData() {
        val service = mockk<CreditorCommerceLibraryRepositoryImpl>()
        val apiCallbackListener = object : LibraryCallbackListener<String> {
            override fun success(response: String) {
                assertNotNull(response)
            }

            override fun failure(error: LibraryError) {
                assertTrue(false)
            }
        }

        every {
            service.callAPI(
                TestConstants.VALID_MANIFEST_URL,
                getApplicationContext(), apiCallbackListener
            )
        } returns apiCallbackListener.success(
            TestConstants.VALID_MANIFEST_RESPONSE
        )

        val creditorCommerceLibraryRepository = CreditorCommerceLibraryRepositoryImpl()
        creditorCommerceLibraryRepository.getManifestRAWData(
            TestConstants.VALID_MANIFEST_URL,
            getApplicationContext(),
            object : LibraryCallbackListener<DspManifestDataResponseModel> {
                override fun success(response: DspManifestDataResponseModel) {
                    assertNotNull(response)
                }

                override fun failure(error: LibraryError) {
                    assertTrue(false)
                }
            },
            true
        )
    }

    @Test
    fun getSignedCertificateFromURL() {
        val service = mockk<CreditorCommerceLibraryRepositoryImpl>()
        val apiCallbackListener = object : LibraryCallbackListener<String> {
            override fun success(response: String) {
                assertNotNull(response)
            }

            override fun failure(error: LibraryError) {
                assertTrue(false)
            }
        }

        every {
            service.callAPI(
                TestConstants.VALID_CERT_URL,
                getApplicationContext(), apiCallbackListener
            )
        } returns apiCallbackListener.success(

            "-----BEGIN CERTIFICATE-----\n" +
                    "MIIDbDCCAlQCCQCnaPh0b1/P4TANBgkqhkiG9w0BAQsFADB4MQswCQYDVQQGEwJH\n" +
                    "QjEPMA0GA1UECAwGTG9uZG9uMQ8wDQYDVQQHDAZMb25kb24xETAPBgNVBAoMCFZv\n" +
                    "Y2FsaW5rMQ0wCwYDVQQLDARaYXBwMSUwIwYDVQQDDBxqd3MtdmVyaWZpY2F0aW9u\n" +
                    "LWNlcnRpZmljYXRlMB4XDTE5MTExNDEzMzkxOVoXDTI5MTExMTEzMzkxOVoweDEL\n" +
                    "MAkGA1UEBhMCR0IxDzANBgNVBAgMBkxvbmRvbjEPMA0GA1UEBwwGTG9uZG9uMREw\n" +
                    "DwYDVQQKDAhWb2NhbGluazENMAsGA1UECwwEWmFwcDElMCMGA1UEAwwcandzLXZl\n" +
                    "cmlmaWNhdGlvbi1jZXJ0aWZpY2F0ZTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCC\n" +
                    "AQoCggEBAMRImj5ZO97X4hJisY+3dt0Ru86XYnPaAiXh7ceKYRcpCV0S3lhXdWDG\n" +
                    "ijbk8qrApk7b0wXpkHLTzTI1e+CdkwYClhW9Gn1pZT30sNMM+PYcKVkF6oRQKugi\n" +
                    "q3LbTCQl2mq0SkaK6tdD3uP4v0kWN3y8OYGywE3/8TyZRbBXoCNZKreyA/YqdflH\n" +
                    "uXXqUN1yq89I3EcLU1+6UscbrQzkoMBSaJq32uVeh1ScpYzQ8raOPcJyCd2lUfOg\n" +
                    "paMoWj5G/EdHJPUz/+6/IRZdZHR0mbzgbin1K1f4NG4m2PQoXjodHg/R1zZvuYyF\n" +
                    "mGiRJQNu7jo/yLH/7f6TQ1xxyk1jFQ8CAwEAATANBgkqhkiG9w0BAQsFAAOCAQEA\n" +
                    "ZJmAz11xd3nIWh4onxwlesjn3a6W1BFt4KZ2GI1uqeRSoIseFlEN2kIt2RsLNQW/\n" +
                    "BKivpZCh7bANPfweE615C4rCFWvvx/7DX92cbWeXXuyt7cf2A3QKB1U8qle00hit\n" +
                    "4+0vMH3m94lruEBfv+nCyuGn/WsuSb/4G/WGm+uF6cJxXqh8HhvhJBUcI5PFjQzl\n" +
                    "1kEPH3YBuGLopqjcqtTZS37i+R4yq8es0tPYREvYBplyT/W2M1wgxP/aLzIesWzQ\n" +
                    "pFhg+3CrgsiImm4HNoAYOEGWI6i1SdWC9RTc3KheeA8MQRL9P3joZYS1vHVTEc7I\n" +
                    "qqn5nyas2pwj6jfts6LnlQ==\n" +
                    "-----END CERTIFICATE-----"
        )
    }
}

