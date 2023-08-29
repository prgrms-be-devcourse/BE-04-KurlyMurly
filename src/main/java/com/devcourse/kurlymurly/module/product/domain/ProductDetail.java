package com.devcourse.kurlymurly.module.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class ProductDetail {
    /** 냉동, 냉장, 상온 */
    public enum Type { FROZEN, REFRIGERATE, ROOM_TEMPERATURE }

    @Column(length = 20, nullable = false)
    private String seller;

    @Column(length = 50, nullable = false)
    private String origin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    @Column(nullable = false)
    private boolean isKurlyOnly;

    protected ProductDetail() {
    }

    public ProductDetail(String seller, String origin, Type type, boolean isKurlyOnly) {
        this.seller = seller;
        this.origin = origin;
        this.type = type;
        this.isKurlyOnly = isKurlyOnly;
    }
}
