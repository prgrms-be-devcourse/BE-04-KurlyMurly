package com.devcourse.kurlymurly.module.product;

import com.devcourse.kurlymurly.module.product.domain.SupportDomain;
import com.devcourse.kurlymurly.module.product.domain.support.ProductSupport;
import com.devcourse.kurlymurly.web.dto.product.support.SupportProduct;

public enum ProductSupportFixture {
    SUPPORT_FIXTURE(1L, 1L, "LA갈비", "고기 맛이 이상해요", "좀 그렇네요", false),
    SECRET_SUPPORT_FIXTURE(2L, 1L, "LA갈비", "아 좀 그런데요?", "진짜 좀 그렇네요", true),
    ;

    private final Long userId;
    private final Long productId;
    private final String productName;
    private final String title;
    private final String content;
    private final boolean isSecret;

    ProductSupportFixture(Long userId, Long productId, String productName, String title, String content, boolean isSecret) {
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
        this.title = title;
        this.content = content;
        this.isSecret = isSecret;
    }

    public ProductSupport toEntity() {
        return new ProductSupport(
                this.userId,
                this.productId,
                this.productName,
                this.title,
                this.content,
                this.isSecret
        );
    }

    public SupportDomain toDomain() {
        return new SupportDomain(
                this.title,
                this.content,
                this.isSecret
        );
    }

    public SupportDomain toSecretDomain() {
        return new SupportDomain(
                this.title,
                this.content,
                !this.isSecret
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
