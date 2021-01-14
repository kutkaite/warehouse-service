package com.ikea.warehouse.service.dto

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class PurchaseRequestTest {
    private val objectMapper = ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)

    @Test
    fun `Should de-serialize Purchase Request`() {
        val json =
                """{
                    "productName": "Dining Chair",
                    "quantity": 1
                }
                """.trimIndent()

        val purchaseRequest = objectMapper.readValue(json, PurchaseRequest::class.java)
        Assertions.assertEquals("Dining Chair", purchaseRequest.productName)
        Assertions.assertEquals(1, purchaseRequest.quantity)
    }
}
