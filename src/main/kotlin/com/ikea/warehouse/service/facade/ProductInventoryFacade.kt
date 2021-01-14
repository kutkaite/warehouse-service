package com.ikea.warehouse.service.facade

import com.ikea.warehouse.service.entity.ProductInventory
import com.ikea.warehouse.service.repository.ProductInventoryRepository
import org.springframework.stereotype.Component

@Component
class ProductInventoryFacade(
        private val productInventoryRepository: ProductInventoryRepository
) {

    fun findAll() = productInventoryRepository.findAll().toList()

    fun findAvailableQuantity(productName: String) = productInventoryRepository.findAvailableQuantity(productName)

    fun save(productInventory: ProductInventory) =  productInventoryRepository.save(productInventory)
}
