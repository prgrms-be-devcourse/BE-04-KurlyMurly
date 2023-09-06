package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.module.product.domain.support.ProductSupport;
import com.devcourse.kurlymurly.module.product.domain.support.ProductSupportRepository;
import com.devcourse.kurlymurly.web.dto.product.support.SupportProduct;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ProductSupportCreate {
    private final ProductSupportRepository productSupportRepository;

    public ProductSupportCreate(ProductSupportRepository productSupportRepository) {
        this.productSupportRepository = productSupportRepository;
    }

    public void create(Long userId, Long productId, String productName, SupportProduct.Request request) {
        ProductSupport support = new ProductSupport(userId, productId, productName, request.title(), request.content(), request.isSecret());
        productSupportRepository.save(support);
    }
}
