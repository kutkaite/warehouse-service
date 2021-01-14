package com.ikea.warehouse.service.repository

import com.ikea.warehouse.service.entity.ProductInventory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest


@ExtendWith
@DataJpaTest
class ProductInventoryRepositoryTest {

    @Autowired
    private lateinit var productInventoryRepository: ProductInventoryRepository

    private val productInventory = ProductInventory(
            name = "Desk",
            maxQuantityAvailable = 5,
            price = 20.5
    )

    @Test
    fun `findAvailableQuantity() should return available quantity for a given product`() {
        productInventoryRepository.save(productInventory)
        val actual = productInventoryRepository.findAvailableQuantity(productInventory.name)

        assertEquals(productInventory.maxQuantityAvailable, actual)
    }
}
