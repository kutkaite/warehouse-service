package com.ikea.warehouse.service.service

import com.ikea.warehouse.service.dto.ProductInventoryResponseDto
import com.ikea.warehouse.service.entity.ProductArticleDetail
import com.ikea.warehouse.service.entity.ProductInventory
import com.ikea.warehouse.service.facade.ProductArticleDetailFacade
import com.ikea.warehouse.service.facade.ArticleInventoryFacade
import com.ikea.warehouse.service.facade.ProductInventoryFacade
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class InventoryServiceTest {

    private val articleInventoryFacade = mockk<ArticleInventoryFacade>()
    private val productInventoryFacade = mockk<ProductInventoryFacade>()
    private val productArticleDetailFacade = mockk<ProductArticleDetailFacade>()

    private val inventoryService = InventoryService(
            articleInventoryFacade = articleInventoryFacade,
            productInventoryFacade = productInventoryFacade,
            productArticleDetailFacade = productArticleDetailFacade
    )

    private val productArticleDetails = mutableListOf(
            ProductArticleDetail(articleId = 1, quantityRequired = 4, requiredByProduct = "Table", productPrice = 120.5)
    )

    private val productInventory = listOf(
            ProductInventory("Table", 2, price = 120.5)
    )

    @BeforeEach
    fun setUp() {
        every { articleInventoryFacade.findStockByArticleId(any()) } returns 1
        every { productArticleDetailFacade.findAll() } returns productArticleDetails
        every { articleInventoryFacade.getArticleWithMostStock(mutableListOf(1, 2)) } returns 20
        every { productInventoryFacade.findAll() } returns productInventory
        every { inventoryService.updateProductInventory() } just runs
    }

    @Test
    fun `getMaxProductAvailability() should return the max amount of products available`() {
        every { articleInventoryFacade.findStockByArticleId(1) } returns 10
        every { articleInventoryFacade.findStockByArticleId(2) } returns 20
        every { articleInventoryFacade.getArticleWithMostStock(mutableListOf(1, 2)) } returns 20

        val productName = "Lamp"
        val articleDetails = listOf(
                ProductArticleDetail(articleId = 1, quantityRequired = 1, requiredByProduct = productName, productPrice = 120.5),
                ProductArticleDetail(articleId = 2, quantityRequired = 2, requiredByProduct = productName, productPrice = 120.5))

        val actual = inventoryService.maxPerPurchase(productName, articleDetails)
        val expected = 10

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `getMaxProductAvailability() should return 0 if not enough articles available for a product`() {
        val productName = "Lamp"
        val articleDetails = listOf(
                ProductArticleDetail(articleId = 1, quantityRequired = 1, requiredByProduct = productName, productPrice = 120.5),
                ProductArticleDetail(articleId = 2, quantityRequired = 2, requiredByProduct = productName, productPrice = 120.5))

        val actual = inventoryService.maxPerPurchase(productName, articleDetails)
        val expected = 0

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `getAllAvailableProducts() should exclude products with 0 stock`() {
        val productInventoryWithZeroStock = listOf(
                ProductInventory("Table", 2, price = 120.5),
                ProductInventory("Lamp", 0, price = 10.0)
        )
        every { productInventoryFacade.findAll() } returns productInventoryWithZeroStock

        val expected = ProductInventoryResponseDto(
                listOf(ProductInventory("Table", 2, price = 120.5)),
                "Shop responsibly! 😇 🌍")
        val actual = inventoryService.getAllAvailableProducts()
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `getAllAvailableProducts() should return message when empty inventory`() {
        val productInventoryWithZeroStock = listOf(
                ProductInventory("Table", 0, price = 50.0),
                ProductInventory("Lamp", 0, price = 20.5)
        )
        every { productInventoryFacade.findAll() } returns productInventoryWithZeroStock

        val expected = ProductInventoryResponseDto(emptyList(), "No products are available")
        val actual = inventoryService.getAllAvailableProducts()
        Assertions.assertEquals(expected, actual)
    }
}

