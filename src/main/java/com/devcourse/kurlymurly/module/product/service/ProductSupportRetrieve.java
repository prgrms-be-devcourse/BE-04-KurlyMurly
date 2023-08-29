package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.module.product.domain.support.ProductSupport;
import com.devcourse.kurlymurly.module.product.domain.support.ProductSupportRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class ProductSupportRetrieve {
    private final ProductSupportRepository productSupportRepository;

    public ProductSupportRetrieve(ProductSupportRepository productSupportRepository) {
        this.productSupportRepository = productSupportRepository;
    }

    public ProductSupport findByIdOrThrow(Long id) {
        return productSupportRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
