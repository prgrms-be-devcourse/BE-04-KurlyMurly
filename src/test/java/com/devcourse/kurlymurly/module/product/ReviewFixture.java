package com.devcourse.kurlymurly.module.product;

import com.devcourse.kurlymurly.module.product.domain.review.Review;
import com.devcourse.kurlymurly.web.dto.product.review.CreateReview;

public enum ReviewFixture {
    REVIEW_FIXTURE(1L, 1L, "LA갈비", "고기가 너무 맛있어요!", false),
    SECRET_REVIEW_FIXTURE(1L, 1L, "LA갈비", "고기가 너무 맛있어요!", true)
    ;

    private final Long userId;
    private final Long productId;
    private final String productName;
    private final String content;
    private final boolean isSecret;

    ReviewFixture(Long userId, Long productId, String productName, String content, boolean isSecret) {
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
        this.content = content;
        this.isSecret = isSecret;
    }

    public Review toEntity() {
        return new Review(userId, productId, productName, content, isSecret);
    }

    public CreateReview.Request toRequest() {
        return new CreateReview.Request(productId, productName, content, isSecret);
    }
}
