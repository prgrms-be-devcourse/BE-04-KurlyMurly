package com.devcourse.kurlymurly.module.product;

import com.devcourse.kurlymurly.module.product.domain.review.Review;
import com.devcourse.kurlymurly.module.user.UserFixture;
import com.devcourse.kurlymurly.web.dto.product.review.CreateReview;

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

    public Review toEntity() {
        return new Review(UserFixture.USER_FIXTURE.toEntity(), ProductFixture.LA_GOGI.toEntity(), content, isSecret);
    }

    public CreateReview.Request toRequest() {
        return new CreateReview.Request(1L, productId, productName, content, isSecret);
    }
}
