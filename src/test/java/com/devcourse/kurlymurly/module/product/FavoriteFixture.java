package com.devcourse.kurlymurly.module.product;

import com.devcourse.kurlymurly.module.product.domain.favorite.Favorite;

public enum FavoriteFixture {
    FAVORITE_FIXTURE(1L, 1L),
    ;

    private final Long userId;
    private final Long productId;

    FavoriteFixture(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public Favorite toEntity() {
        return new Favorite(this.userId, this.productId);
    }

    public Long userId() {
        return userId;
    }

    public Long productId() {
        return productId;
    }
}
