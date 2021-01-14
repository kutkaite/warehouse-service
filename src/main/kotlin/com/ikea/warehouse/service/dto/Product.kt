package com.ikea.warehouse.service.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class Product (
        @JsonProperty("name")
        val name: String,
        @JsonProperty("price")
        val price: Double,
        @JsonProperty("contain_articles")
        val articleDetails: List<ArticleDetail>
) {
    data class ArticleDetail(
            @JsonProperty("art_id")
            val articleId: Int,
            @JsonProperty("amount_of")
            val quantity: Int
    )
}
