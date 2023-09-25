package com.devcourse.kurlymurly.web.dto.product.support;

import com.devcourse.kurlymurly.module.product.domain.support.ProductSupport;

import java.time.LocalDateTime;

import static com.devcourse.kurlymurly.web.dto.product.support.SupportResponse.Create;

public sealed interface SupportResponse permits Create {
    record Create(
            Long id,
            Long productId,
            String productName,
            String title,
            String content,
            boolean isSecret,
            ProductSupport.Status status,
            LocalDateTime createdAt
    ) implements SupportResponse {
    }
}
