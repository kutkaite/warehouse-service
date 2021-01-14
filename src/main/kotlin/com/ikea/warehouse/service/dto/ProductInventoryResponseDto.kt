package com.ikea.warehouse.service.dto

import com.ikea.warehouse.service.entity.ProductInventory

data class ProductInventoryResponseDto(
        val productInventory: List<ProductInventory>,
        val message: String
)
