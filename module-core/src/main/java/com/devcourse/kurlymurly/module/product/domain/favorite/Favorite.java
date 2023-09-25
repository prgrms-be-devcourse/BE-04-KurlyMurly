package com.devcourse.kurlymurly.module.product.domain.favorite;

import com.devcourse.kurlymurly.module.BaseEntity;
import com.devcourse.kurlymurly.module.product.domain.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "favorites")
public class Favorite extends BaseEntity {
    @Column(nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Column(nullable = false)
    private boolean isDeleted;

    protected Favorite() {
    }

    public Favorite(Long userId, Product product) {
        this.userId = userId;
        this.product = product;
        this.isDeleted = false;
    }

    public void activate() {
        this.isDeleted = false;
    }

    public void softDelete() {
        this.isDeleted = true;
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }
}
