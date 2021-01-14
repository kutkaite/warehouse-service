package com.ikea.warehouse.service.repository

import com.ikea.warehouse.service.entity.ProductInventory
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface ProductInventoryRepository : CrudRepository<ProductInventory, Long?> {
    @Query("SELECT max_quantity_available FROM product_inventory WHERE name = :productName", nativeQuery = true)
    fun findAvailableQuantity(productName: String?): Int?
}
