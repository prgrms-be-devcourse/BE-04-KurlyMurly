package com.devcourse.kurlymurly.domain.product;

import com.devcourse.kurlymurly.domain.product.review.Review;

public enum ReviewFixture {
    REVIEW_FIXTURE("[고기반찬] 양념 LA 갈비 800g", "고기가 너무 맛있어요!", false),
    SECRET_REVIEW_FIXTURE("[고기반찬] 양념 LA 갈비 800g","고기가 너무 맛있어요! 비밀이에요!!", true)
    ;

    private final String productName;
    private final String content;
    private final boolean isSecret;

    ReviewFixture(String productName, String content, boolean isSecret) {
        this.productName = productName;
        this.content = content;
        this.isSecret = isSecret;
    }

    public Review toEntity(Long productId) {
        return new Review(1L, productId, productName, content, isSecret);
    }
}
