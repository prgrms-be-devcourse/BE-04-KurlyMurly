package com.devcourse.kurlymurly.module.product.domain.favorite;

import com.devcourse.kurlymurly.module.BaseEntity;
import com.devcourse.kurlymurly.module.product.domain.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "favorites")
public class Favorite extends BaseEntity {
    public enum Status { NORMAL, DELETED }

    @Column(nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    protected Favorite() {
    }

    public Favorite(Long userId, Product product) {
        this.userId = userId;
        this.product = product;
        this.status = Status.NORMAL;
    }

    public void activate() {
        this.status = Status.NORMAL;
    }

    public void softDelete() {
        this.status = Status.DELETED;
    }

    public Status getStatus() {
        return status;
    }

    public String getProductName() {
        return product.getName();
    }

    public int getProductPrice() {
        return product.getPrice();
    }
}
