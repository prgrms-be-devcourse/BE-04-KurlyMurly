package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.product.domain.support.ProductSupport;
import com.devcourse.kurlymurly.module.product.domain.support.ProductSupportRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.devcourse.kurlymurly.global.exception.ErrorCode.NEVER_WRITE_PRODUCT_SUPPORT;

@Component
@Transactional(readOnly = true)
public class ProductSupportRetrieve {
    private final ProductSupportRepository productSupportRepository;

    public ProductSupportRetrieve(ProductSupportRepository productSupportRepository) {
        this.productSupportRepository = productSupportRepository;
    }

    public ProductSupport findByIdOrThrow(Long id) {
        return productSupportRepository.findById(id)
                .orElseThrow(() -> KurlyBaseException.withId(NEVER_WRITE_PRODUCT_SUPPORT, id));
    }
}
