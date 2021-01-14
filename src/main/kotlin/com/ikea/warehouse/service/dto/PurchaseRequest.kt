package com.ikea.warehouse.service.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class PurchaseRequest(
        @JsonProperty("productName")
        val productName: String,
        @JsonProperty("quantity")
        val quantity: Int
)
