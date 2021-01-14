package com.ikea.warehouse.service.configuration

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.ikea.warehouse.service.dataloader.ArticleDataLoader
import com.ikea.warehouse.service.dataloader.ProductDataLoader
import com.ikea.warehouse.service.facade.ArticleInventoryFacade
import com.ikea.warehouse.service.facade.ProductArticleDetailFacade
import com.ikea.warehouse.service.facade.ProductInventoryFacade
import com.ikea.warehouse.service.repository.ArticleInventoryRepository
import com.ikea.warehouse.service.repository.ProductArticleDetailRepository
import com.ikea.warehouse.service.repository.ProductInventoryRepository
import com.ikea.warehouse.service.service.InventoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader

@ComponentScan
@Configuration
class InventoryConfiguration(
        @Autowired var resourceLoader: ResourceLoader
) {

    @Bean
    fun databaseInitializer(
            productInventoryRepository: ProductInventoryRepository,
            articleInventoryRepository: ArticleInventoryRepository,
            productArticleDetailRepository: ProductArticleDetailRepository
    ) = ApplicationRunner {
        val objectMapper = ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)

        val articleInventoryFacade = ArticleInventoryFacade(articleInventoryRepository = articleInventoryRepository)
        val productInventoryFacade = ProductInventoryFacade(productInventoryRepository = productInventoryRepository)
        val productArticleDetailFacade = ProductArticleDetailFacade(productArticleDetailRepository = productArticleDetailRepository)

        val inventoryService = InventoryService(
                articleInventoryFacade = articleInventoryFacade,
                productInventoryFacade = productInventoryFacade,
                productArticleDetailFacade = productArticleDetailFacade)

        val articleDataLoader = ArticleDataLoader(articleInventoryFacade = articleInventoryFacade)
        articleDataLoader.load(dataType = "inventory", resourceLoader = resourceLoader, objectMapper = objectMapper)

        val productDataLoader = ProductDataLoader(productArticleDetailFacade = productArticleDetailFacade)
        productDataLoader.load(dataType = "products", resourceLoader = resourceLoader, objectMapper = objectMapper)

        inventoryService.updateProductInventory()
    }
}
