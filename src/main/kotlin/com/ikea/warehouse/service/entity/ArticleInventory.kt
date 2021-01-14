package com.ikea.warehouse.service.entity

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@Entity
@Table(name = "article_inventory")
class ArticleInventory(
        @Id
        @JsonProperty("art_id")
        val articleId: Int,
        @JsonProperty("name")
        val name: String,
        @JsonProperty("stock")
        val stock: Int
) : InventoryEntity {
        constructor():this(-1, "NOT_SET", -1)
}
