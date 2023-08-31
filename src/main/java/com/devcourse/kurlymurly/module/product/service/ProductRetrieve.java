package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.product.domain.Product;
import com.devcourse.kurlymurly.module.product.domain.ProductRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.devcourse.kurlymurly.global.exception.ErrorCode.PRODUCT_NOT_FOUND;

@Component
@Transactional(readOnly = true)
public class ProductRetrieve {
    private final ProductRepository productRepository;

    public ProductRetrieve(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findByIdOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> KurlyBaseException.withId(PRODUCT_NOT_FOUND, id));
    }
}
