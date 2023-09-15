package com.devcourse.kurlymurly.web.dto.product.review;

import java.time.LocalDateTime;

import static com.devcourse.kurlymurly.web.dto.product.review.ReviewResponse.ReviewOfProduct;
import static com.devcourse.kurlymurly.web.dto.product.review.ReviewResponse.Reviewable;
import static com.devcourse.kurlymurly.web.dto.product.review.ReviewResponse.Reviewed;

public sealed interface ReviewResponse permits Reviewable, Reviewed, ReviewOfProduct {
    record Reviewable(
        Long productId,
        String productName,
        String orderNumber,
        LocalDateTime deliveredAt,
        LocalDateTime reviewDeadLine
    ) implements ReviewResponse {
    }

    record Reviewed(
            Long productId,
            String productName,
            String content,
            boolean isSecret,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) implements ReviewResponse {
    }

    record ReviewOfProduct(
            Long productId,
            String productName,
            String userName,
            String userTier,
            String content,
            Integer likes,
            LocalDateTime createdAt,
            boolean isSecret
    ) implements ReviewResponse {
    }
}
