package com.devcourse.kurlymurly.web.dto.product.review;

import java.time.LocalDateTime;

import static com.devcourse.kurlymurly.web.dto.product.review.ReviewResponse.Reviewable;
import static com.devcourse.kurlymurly.web.dto.product.review.ReviewResponse.Reviewed;

public sealed interface ReviewResponse permits Reviewable, Reviewed {
    record Reviewable(
        Long productId,
        String name,
        LocalDateTime deliveredAt,
        LocalDateTime reviewDeadLine
    ) implements ReviewResponse {
    }

    record Reviewed(
            Long productId,
            String name,
            String content,
            LocalDateTime createdAt,
            boolean isSecret
    ) implements ReviewResponse {
    }
}
