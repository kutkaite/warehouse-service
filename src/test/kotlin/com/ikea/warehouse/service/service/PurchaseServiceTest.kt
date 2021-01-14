package com.ikea.warehouse.service.service

import com.ikea.warehouse.service.dto.PurchaseResponseDto
import com.ikea.warehouse.service.entity.ArticleInventory
import com.ikea.warehouse.service.entity.ProductArticleDetail
import com.ikea.warehouse.service.facade.ArticleInventoryFacade
import com.ikea.warehouse.service.facade.ProductArticleDetailFacade
import com.ikea.warehouse.service.facade.ProductInventoryFacade
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PurchaseServiceTest {
    private val articleInventoryFacade = mockk<ArticleInventoryFacade>()
    private val productInventoryFacade = mockk<ProductInventoryFacade>()
    private val productArticleDetailFacade = mockk<ProductArticleDetailFacade>()
    private val inventoryService = mockk<InventoryService>()

    private val purchaseService = PurchaseService(
            articleInventoryFacade = articleInventoryFacade,
            productInventoryFacade = productInventoryFacade,
            productArticleDetailFacade = productArticleDetailFacade,
            inventoryService = inventoryService
    )

    private val maxStock = 5

    private val articleInventoryForArticle = ArticleInventory(
            articleId = 1,
            name = "leg",
            stock = maxStock
    )

    private val productArticleDetails = mutableListOf(
            ProductArticleDetail(articleId = 1, quantityRequired = 4, requiredByProduct = "Table", productPrice = 50.5)
    )

    @BeforeEach
    fun setUp() {
        every { articleInventoryFacade.findByArticleId(any()) } returns articleInventoryForArticle
        every { productArticleDetailFacade.getProductByName(any()) } returns productArticleDetails
        every { productInventoryFacade.findAvailableQuantity(any()) } returns maxStock
        every { articleInventoryFacade.save(any()) } returns articleInventoryForArticle
        every { inventoryService.updateProductInventory() } just runs
    }

    @Test
    fun `purchaseProduct() should return success message when order can be fulfilled`() {
        val productName = "Table"
        val productQuantity = 2

        val articleInventoryForArticleUpdated = ArticleInventory(
                articleId = 1,
                name = "leg",
                stock = maxStock - productQuantity
        )

        every { articleInventoryFacade.save(any()) } returns articleInventoryForArticleUpdated

        val totalAmount = productQuantity * 50.5

        val actual = purchaseService.purchaseProduct(productName, productQuantity)
        val expected = PurchaseResponseDto(
                productName,
                productQuantity,
                "Thanks for purchasing!",
                totalAmount)

        Assertions.assertEquals(expected, actual)
        Assertions.assertEquals(3, articleInventoryForArticleUpdated.stock)
    }

    @Test
    fun `purchaseProduct() should return failure message when not enough product stock`() {
        every { productInventoryFacade.findAvailableQuantity(any()) } returns 1

        val productName = "Table"
        val productQuantity = 2

        val actual = purchaseService.purchaseProduct(productName, productQuantity)
        val expected = PurchaseResponseDto(
                productName,
                productQuantity,
                "There was a problem with your purchase! Not enough stock for this product."
        )

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `purchaseProduct() should return failure message when not enough product doesn't exist`() {
        val productName = "Non existent product"
        val productQuantity = 2

        every { productArticleDetailFacade.getProductByName(any()) } returns emptyList()

        val actual = purchaseService.purchaseProduct(productName, productQuantity)
        val expected = PurchaseResponseDto(
                productName,
                productQuantity,
                "There was a problem with your purchase! There is no such product."
        )

        Assertions.assertEquals(expected, actual)
    }
}
