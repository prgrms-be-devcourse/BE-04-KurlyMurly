package com.devcourse.kurlymurly.module.product.domain.favorite;

import com.devcourse.kurlymurly.module.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "favorites")
public class Favorite extends BaseEntity {
    public enum Status { NORMAL, DELETED }

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long productId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    protected Favorite() {
    }

    public Favorite(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
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
}
