package com.ikea.warehouse.service.dto

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class ProductTest {
    private val objectMapper = ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)

    @Test
    fun `Should de-serialize Product`() {
        val json =
                """{
                    "name": "Dining Chair",
                    "price": 30.0,
                    "contain_articles": [
                    {
                        "art_id": "1",
                        "amount_of": "4"
                    },
                    {
                        "art_id": "2",
                        "amount_of": "8"
                    },
                    {
                        "art_id": "3",
                        "amount_of": "1"
                    }]
                }
                """.trimIndent()

        val product = objectMapper.readValue(json, Product::class.java)
        Assertions.assertEquals("Dining Chair", product.name)
        Assertions.assertEquals(30.0, product.price)
    }
}
