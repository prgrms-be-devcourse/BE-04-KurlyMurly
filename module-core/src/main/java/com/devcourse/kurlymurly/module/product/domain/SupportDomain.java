package com.devcourse.kurlymurly.module.product.domain;

import com.devcourse.kurlymurly.module.product.domain.support.ProductSupport;

public class SupportDomain {
    private final String title;
    private final String content;
    private final boolean isSecret;

    public SupportDomain(String title, String content, boolean isSecret) {
        this.title = title;
        this.content = content;
        this.isSecret = isSecret;
    }

    public ProductSupport toSupportEntity(Long userId, Long productId, String productName) {
        return new ProductSupport(userId, productId, productName, title, content, isSecret);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public boolean isSecret() {
        return isSecret;
    }
}
