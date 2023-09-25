package com.devcourse.kurlymurly.web.dto.product.review;

import com.devcourse.kurlymurly.module.user.domain.User;

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
            Long reviewId,
            Long productId,
            String productName,
            String content,
            boolean isSecret,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) implements ReviewResponse {
    }

    record ReviewOfProduct(
            String userName,
            User.Tier tier,
            String productName,
            Long reviewId,
            String content,
            Integer likes,
            LocalDateTime createdAt,
            boolean isSecret
    ) implements ReviewResponse {
    }
}
