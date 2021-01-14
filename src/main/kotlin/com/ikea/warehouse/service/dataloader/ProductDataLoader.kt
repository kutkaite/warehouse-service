package com.ikea.warehouse.service.dataloader

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.ikea.warehouse.service.dto.Product
import com.ikea.warehouse.service.entity.ProductArticleDetail
import com.ikea.warehouse.service.entity.InventoryEntity
import com.ikea.warehouse.service.facade.ProductArticleDetailFacade

class ProductDataLoader(private val productArticleDetailFacade: ProductArticleDetailFacade) : DataLoader() {

    override fun mapJsonToEntity(jsonNode: JsonNode, objectMapper: ObjectMapper) {
        val product = objectMapper.readValue(jsonNode.toString(), Product::class.java)
        mapToProductArticleDetail(product)
    }

    private fun mapToProductArticleDetail(product: Product) {
        product.articleDetails.forEach {
            val articleDetail = ProductArticleDetail(
                    articleId = it.articleId,
                    quantityRequired = it.quantity,
                    requiredByProduct = product.name,
                    productPrice = product.price)
            save(articleDetail)
        }
    }

    override fun save(inventoryEntity: InventoryEntity): InventoryEntity {
        val articleDetail = inventoryEntity as ProductArticleDetail
        return productArticleDetailFacade.save(articleDetail)
    }
}
