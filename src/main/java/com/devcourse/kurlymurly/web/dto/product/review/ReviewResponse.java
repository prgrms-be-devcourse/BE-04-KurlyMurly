package com.devcourse.kurlymurly.web.dto.product.review;

import java.time.LocalDateTime;

import static com.devcourse.kurlymurly.web.dto.product.review.ReviewResponse.Writable;
import static com.devcourse.kurlymurly.web.dto.product.review.ReviewResponse.Wrote;

public sealed interface ReviewResponse permits Writable, Wrote {
    record Writable(
        Long productId,
        String name,
        LocalDateTime deliveredAt,
        LocalDateTime reviewDeadLine
    ) implements ReviewResponse {
    }

    record Wrote(
            Long productId,
            String name,
            String content,
            LocalDateTime createdAt,
            boolean isSecret
    ) implements ReviewResponse {
    }
}
