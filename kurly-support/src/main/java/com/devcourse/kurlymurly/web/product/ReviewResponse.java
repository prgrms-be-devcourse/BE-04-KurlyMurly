package com.devcourse.kurlymurly.web.product;

import java.time.LocalDateTime;

import static com.devcourse.kurlymurly.web.product.ReviewResponse.Like;
import static com.devcourse.kurlymurly.web.product.ReviewResponse.OfProduct;
import static com.devcourse.kurlymurly.web.product.ReviewResponse.Reviewable;
import static com.devcourse.kurlymurly.web.product.ReviewResponse.Reviewed;

public sealed interface ReviewResponse permits Reviewable, Reviewed, OfProduct, Like {
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

    record OfProduct(
            String userName,
            String tier,
            String productName,
            Long reviewId,
            String content,
            Integer likes,
            LocalDateTime createdAt,
            boolean isSecret
    ) implements ReviewResponse {
    }

    record Like(
            Long likeUserId,
            Long reviewId
    ) implements ReviewResponse {
    }
}
