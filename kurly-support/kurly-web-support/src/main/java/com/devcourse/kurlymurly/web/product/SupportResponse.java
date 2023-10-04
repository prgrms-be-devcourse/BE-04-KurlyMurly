package com.devcourse.kurlymurly.web.product;

import java.time.LocalDateTime;

import static com.devcourse.kurlymurly.web.product.SupportResponse.Create;

public sealed interface SupportResponse permits Create {
    record Create(
            Long id,
            Long productId,
            String productName,
            String title,
            String content,
            boolean isSecret,
            String status,
            LocalDateTime createdAt
    ) implements SupportResponse {
    }
}
