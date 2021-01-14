package com.ikea.warehouse.service.entity

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@Entity
@Table(name = "product_article_detail")
data class ProductArticleDetail(
        @JsonProperty("art_id")
        val articleId: Int,
        @JsonProperty("amount_of")
        val quantityRequired: Int,
        val requiredByProduct: String,
        val productPrice: Double
) : InventoryEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int = 0

        constructor():this(-1, -1, "NOT_SET", 00.0)
}
