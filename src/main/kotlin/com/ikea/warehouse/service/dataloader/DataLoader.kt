package com.ikea.warehouse.service.dataloader

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.ikea.warehouse.service.entity.InventoryEntity
import org.springframework.core.io.ResourceLoader

abstract class DataLoader {
    fun load(dataType: String, resourceLoader: ResourceLoader, objectMapper: ObjectMapper) {
        val resource = resourceLoader.getResource("classpath:static/$dataType.json")
        val jsonNode = objectMapper.readValue(resource.inputStream, JsonNode::class.java)

        if (jsonNode != null && jsonNode.has(dataType)) {
            val articlesJson = jsonNode.get(dataType)
            if (articlesJson.isArray) {
                articlesJson.forEach { mapJsonToEntity(it, objectMapper) }
            }
        }
    }

    abstract fun mapJsonToEntity(jsonNode: JsonNode, objectMapper: ObjectMapper)

    abstract fun save(inventoryEntity: InventoryEntity): InventoryEntity
}
