package com.ikea.warehouse.service.repository

import com.ikea.warehouse.service.entity.ArticleInventory
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository


interface ArticleInventoryRepository : CrudRepository<ArticleInventory, Long?> {
    @Query("SELECT * FROM article_inventory where article_id = :articleId", nativeQuery = true)
    fun findByArticleId(articleId: Int): ArticleInventory

    @Query("SELECT stock FROM article_inventory where article_id = :articleId", nativeQuery = true)
    fun findStockByArticleId(articleId: Int): Int

    @Query("SELECT MAX(stock) FROM article_inventory WHERE article_id in :articleIds", nativeQuery = true)
    fun getMostStockRequired(articleIds: MutableList<Int>): Int
}
