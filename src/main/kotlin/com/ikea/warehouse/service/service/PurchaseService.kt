package com.ikea.warehouse.service.service

import com.ikea.warehouse.service.dto.PurchaseResponseDto
import com.ikea.warehouse.service.entity.ArticleInventory
import com.ikea.warehouse.service.entity.ProductArticleDetail
import com.ikea.warehouse.service.facade.ArticleInventoryFacade
import com.ikea.warehouse.service.facade.ProductArticleDetailFacade
import com.ikea.warehouse.service.facade.ProductInventoryFacade
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.Exception

@Service
class PurchaseService(
        val articleInventoryFacade: ArticleInventoryFacade,
        val productInventoryFacade: ProductInventoryFacade,
        val productArticleDetailFacade: ProductArticleDetailFacade,
        val inventoryService: InventoryService
) {

    @Transactional
    fun purchaseProduct(productName: String, productQuantity: Int): PurchaseResponseDto {
        val productArticleDetails = productArticleDetailFacade.getProductByName(productName)

        if (productArticleDetails.isNullOrEmpty()) {
            return PurchaseResponseDto(
                    productName,
                    productQuantity,
                    "There was a problem with your purchase! There is no such product."
            )
        }

        val maxAvailableQuantity = productInventoryFacade.findAvailableQuantity(productName)!!
        if (maxAvailableQuantity < productQuantity) {
            return PurchaseResponseDto(
                    productName,
                    productQuantity,
                    "There was a problem with your purchase! Not enough stock for this product."
            )
        }

        updateArticleInventory(productArticleDetails, productQuantity)

        inventoryService.updateProductInventory()

        val totalOrderAmount = getTotalOrderAmount(productArticleDetails, productQuantity)
        return PurchaseResponseDto(productName, productQuantity, "Thanks for purchasing!", totalOrderAmount)

    }

    fun updateArticleInventory(productArticleDetails: List<ProductArticleDetail>, productQuantity: Int) {
        productArticleDetails.forEach {
            try {
                val articleInventory = articleInventoryFacade.findByArticleId(it.articleId)
                val currentStock = articleInventory.stock

                val newStockAmount = currentStock - (productQuantity * it.quantityRequired)

                articleInventoryFacade.save(
                        ArticleInventory(
                                articleId = articleInventory.articleId,
                                name = articleInventory.name,
                                stock = newStockAmount
                        )
                )
            } catch (e: Exception) {
                LOG.error("Failed to update Article inventory", e.message)
                return
            }
        }
        LOG.info("Article inventory was updated with the new stock amount")
    }

    fun getTotalOrderAmount(productArticleDetails: List<ProductArticleDetail>, productQuantity: Int): Double {
        val productPrice = productArticleDetails[0].productPrice
        return productPrice * productQuantity
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(PurchaseService::class.java)
    }
}
