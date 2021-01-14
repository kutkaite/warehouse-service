package com.ikea.warehouse.service.repository

import com.ikea.warehouse.service.entity.ArticleInventory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest


@ExtendWith
@DataJpaTest
class ArticleInventoryRepositoryTest {

    @Autowired
    private lateinit var articleInventoryRepository: ArticleInventoryRepository

    private val articleInventory = ArticleInventory(
            articleId = 1,
            name = "leg",
            stock = 5
    )

    private val anotherArticleInventory = ArticleInventory(
            articleId = 2,
            name = "table top",
            stock = 20
    )

    @BeforeEach
    fun setUp() {
        articleInventoryRepository.save(articleInventory)
        articleInventoryRepository.save(anotherArticleInventory)
    }

    @Test
    fun `findByArticleId() should return correct article`() {
        val expected = articleInventoryRepository.save(articleInventory)
        val actual = articleInventoryRepository.findByArticleId(articleInventory.articleId)

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `findStockByArticleId() should return correct stock amount for article id`() {
        val actual = articleInventoryRepository.findStockByArticleId(articleInventory.articleId)

        Assertions.assertEquals(articleInventory.stock, actual)
    }

    @Test
    fun `getMostStockRequired() should return the article with most stock`() {
        val actual = articleInventoryRepository.getMostStockRequired(
                mutableListOf(articleInventory.articleId, anotherArticleInventory.articleId))

        Assertions.assertEquals(anotherArticleInventory.stock, actual)
    }
}
