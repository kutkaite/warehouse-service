package com.ikea.warehouse.service.dataloader

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.ikea.warehouse.service.entity.ArticleInventory
import com.ikea.warehouse.service.entity.InventoryEntity
import com.ikea.warehouse.service.facade.ArticleInventoryFacade

class ArticleDataLoader(
        private val articleInventoryFacade: ArticleInventoryFacade
) : DataLoader() {

    override fun mapJsonToEntity(jsonNode: JsonNode, objectMapper: ObjectMapper) {
        val article = objectMapper.readValue(jsonNode.toString(), ArticleInventory::class.java)
        save(article)
    }

    override fun save(inventoryEntity: InventoryEntity): InventoryEntity {
        val article = inventoryEntity as ArticleInventory
        return articleInventoryFacade.save(article)
    }
}
