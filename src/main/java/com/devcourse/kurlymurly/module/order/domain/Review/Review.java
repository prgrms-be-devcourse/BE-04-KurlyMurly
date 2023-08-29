package com.devcourse.kurlymurly.module.order.domain.Review;

import com.devcourse.kurlymurly.module.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {

    public enum Status {
        NORMAL,
        BANNED,
        BEST,
        DELETED
    }

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private int likes;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status status;

    protected Review() {}

    public Review(Long userId, Long productId, Long orderId, int likes, String content) {
        this.userId = userId;
        this.productId = productId;
        this.orderId = orderId;
        this.likes = likes;
        this.content = content;
        this.status = Status.NORMAL;
    }
}
