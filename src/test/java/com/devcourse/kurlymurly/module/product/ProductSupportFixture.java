package com.devcourse.kurlymurly.module.product;

import com.devcourse.kurlymurly.module.product.domain.support.ProductSupport;
import com.devcourse.kurlymurly.web.dto.product.SupportProduct;

public enum ProductSupportFixture {
    SUPPORT_FIXTURE(99L, 1L, "고기 맛이 이상해요", "좀 그렇네요", false),
    SECRET_SUPPORT_FIXTURE(100L, 1L, "아 좀 그런데요?", "진짜 좀 그렇네요", true),
    ;

    private final Long userId;
    private final Long productId;
    private final String title;
    private final String content;
    private final boolean isSecret;

    ProductSupportFixture(Long userId, Long productId, String title, String content, boolean isSecret) {
        this.userId = userId;
        this.productId = productId;
        this.title = title;
        this.content = content;
        this.isSecret = isSecret;
    }

    public ProductSupport toEntity() {
        return new ProductSupport(
                this.userId,
                this.productId,
                this.title,
                this.content,
                this.isSecret
        );
    }

    public SupportProduct.Request toRequest() {
        return new SupportProduct.Request(
                this.title,
                this.content,
                this.isSecret
        );
    }

    public Long getUserId() {
        return this.userId;
    }
}
