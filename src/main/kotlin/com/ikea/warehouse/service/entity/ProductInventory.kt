package com.ikea.warehouse.service.entity

import javax.persistence.*

@Entity
@Table(name = "product_inventory")
data class ProductInventory(
        @Id
        val name: String,
        val maxQuantityAvailable: Int,
        val price: Double
) : InventoryEntity {
        constructor():this("NOT_SET", -1, 00.0)
}
