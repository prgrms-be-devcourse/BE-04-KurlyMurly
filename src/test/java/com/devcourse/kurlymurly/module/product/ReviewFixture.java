package com.devcourse.kurlymurly.module.product;

import com.devcourse.kurlymurly.module.product.domain.Product;
import com.devcourse.kurlymurly.module.product.domain.review.Review;
import com.devcourse.kurlymurly.module.user.UserFixture;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.web.dto.product.review.CreateReview;

public enum ReviewFixture {
    REVIEW_FIXTURE(UserFixture.USER_FIXTURE.toEntity(), ProductFixture.LA_GOGI.toEntity(), "LA갈비", "고기가 너무 맛있어요!", false),
    SECRET_REVIEW_FIXTURE(UserFixture.USER_FIXTURE.toEntity(), ProductFixture.LA_GOGI.toEntity(), "LA갈비", "고기가 너무 맛있어요!", true)
    ;

    private final User user;
    private final Product product;
    private final String productName;
    private final String content;
    private final boolean isSecret;

    ReviewFixture(User user, Product product, String productName, String content, boolean isSecret) {
        this.user = user;
        this.product = product;
        this.productName = productName;
        this.content = content;
        this.isSecret = isSecret;
    }

    public Review toEntity() {
        return new Review(user, product, content, isSecret);
    }

    public CreateReview.Request toRequest() {
        return new CreateReview.Request(product.getId(), productName, content, isSecret);
    }
}
