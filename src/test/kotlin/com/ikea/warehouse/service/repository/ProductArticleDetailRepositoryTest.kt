package com.ikea.warehouse.service.repository

import com.ikea.warehouse.service.entity.ProductArticleDetail
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest


@ExtendWith
@DataJpaTest
class ProductArticleDetailRepositoryTest {

    @Autowired
    private lateinit var productArticleDetailRepository: ProductArticleDetailRepository

    private val productArticleDetail = ProductArticleDetail(
            articleId = 1,
            quantityRequired = 2,
            requiredByProduct = "Desk",
            productPrice = 20.5
    )

    private val anotherProductArticleDetail = ProductArticleDetail(
            articleId = 2,
            quantityRequired = 5,
            requiredByProduct = "Desk",
            productPrice = 20.5
    )

    @Test
    fun `findProductByName() should return all article details associated to product`() {
        val expected = listOf(
                productArticleDetailRepository.save(productArticleDetail),
                productArticleDetailRepository.save(anotherProductArticleDetail)
        )
        val actual = productArticleDetailRepository.findProductByName(productArticleDetail.requiredByProduct)

        Assertions.assertEquals(expected, actual)
    }
}
