package com.devcourse.kurlymurly.module.product;

import com.devcourse.kurlymurly.domain.user.User;
import com.devcourse.kurlymurly.module.product.domain.Product;
import com.devcourse.kurlymurly.module.product.domain.review.Review;

public enum ReviewFixture {
    REVIEW_FIXTURE(1L, "LA갈비", "고기가 너무 맛있어요!", false),
    SECRET_REVIEW_FIXTURE(1L, "LA갈비","고기가 너무 맛있어요! 비밀이에요!!", true)
    ;

    private final Long productId;
    private final String productName;
    private final String content;
    private final boolean isSecret;

    ReviewFixture(Long productId, String productName, String content, boolean isSecret) {
        this.productId = productId;
        this.productName = productName;
        this.content = content;
        this.isSecret = isSecret;
    }

    public Review toEntity(User user, Product product) {
        return new Review(user, product, content, isSecret);
    }
}
