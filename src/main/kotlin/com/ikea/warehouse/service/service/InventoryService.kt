package com.ikea.warehouse.service.service

import com.ikea.warehouse.service.dto.ProductInventoryResponseDto
import com.ikea.warehouse.service.entity.ProductArticleDetail
import com.ikea.warehouse.service.entity.ProductInventory
import com.ikea.warehouse.service.facade.ArticleInventoryFacade
import com.ikea.warehouse.service.facade.ProductArticleDetailFacade
import com.ikea.warehouse.service.facade.ProductInventoryFacade
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class InventoryService(
        val articleInventoryFacade: ArticleInventoryFacade,
        val productInventoryFacade: ProductInventoryFacade,
        val productArticleDetailFacade: ProductArticleDetailFacade
) {

    fun updateProductInventory() {
        val productsGrouped = productArticleDetailFacade.findAll().groupBy({ it.requiredByProduct }, { it })

        if (productsGrouped.isNullOrEmpty()) {
            LOG.info("No products are available")
            return
        }

        productsGrouped.forEach { product ->
            try {
                val productAvailability = maxPerPurchase(product.key, product.value)
                val productInventory = ProductInventory(
                        name = product.key,
                        maxQuantityAvailable = productAvailability,
                        price = product.value[0].productPrice)
                productInventoryFacade.save(productInventory)
            } catch (e: Exception) {
                LOG.error("Failed to add {} to Product inventory", product.key, e.message)
                return
            }
        }
        LOG.info("Product inventory was updated with the latest quantity amounts")
    }

    fun maxPerPurchase(productName: String, productArticleDetail: List<ProductArticleDetail>): Int {
        val articleIds: MutableList<Int> = mutableListOf()
        productArticleDetail.forEach { articleIds.add(it.articleId) }

        // get an article with most stock as a starting point for later calculations
        var maxProductAvailability = articleInventoryFacade.getArticleWithMostStock(articleIds = articleIds)

        productArticleDetail.forEach { articleDetails ->
            val articleStock = articleInventoryFacade.findStockByArticleId(articleDetails.articleId)
            val maxCurrentArticle = articleStock.div(articleDetails.quantityRequired)
            if (maxProductAvailability > maxCurrentArticle) {
                maxProductAvailability = maxCurrentArticle
            }
        }
        return maxProductAvailability
    }

    fun getAllAvailableProducts(): ProductInventoryResponseDto {
        val productInventory = productInventoryFacade.findAll().filter { it.maxQuantityAvailable > 0 }
        if (productInventory.isNullOrEmpty()) {
            return ProductInventoryResponseDto(emptyList(), "No products are available")
        }
        return ProductInventoryResponseDto(productInventory, "Shop responsibly! 😇 🌍")
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(InventoryService::class.java)
    }
}
