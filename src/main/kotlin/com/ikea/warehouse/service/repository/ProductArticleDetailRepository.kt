package com.ikea.warehouse.service.repository

import com.ikea.warehouse.service.entity.ProductArticleDetail
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface ProductArticleDetailRepository : CrudRepository<ProductArticleDetail, Long?> {
    @Query("SELECT * FROM product_article_detail WHERE required_by_product = :productName", nativeQuery = true)
    fun findProductByName(productName: String?): Iterable<ProductArticleDetail>
}
