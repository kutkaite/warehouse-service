package com.ikea.warehouse.service.facade

import com.ikea.warehouse.service.entity.ProductArticleDetail
import com.ikea.warehouse.service.repository.ProductArticleDetailRepository
import org.springframework.stereotype.Component

@Component
class ProductArticleDetailFacade(
        private val productArticleDetailRepository: ProductArticleDetailRepository
) {

    fun findAll(): List<ProductArticleDetail> = productArticleDetailRepository.findAll().toList()

    fun getProductByName(productName: String) = productArticleDetailRepository.findProductByName(productName).toList()

    fun save(productArticleDetail: ProductArticleDetail) = productArticleDetailRepository.save(productArticleDetail)
}
