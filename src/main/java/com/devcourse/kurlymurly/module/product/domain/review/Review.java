package com.devcourse.kurlymurly.module.product.domain.review;

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
        SECRET,
        DELETED
    }

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private Integer likes;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status status;

    protected Review() {
    }

    public Review(Long userId, Long productId, Long orderId, Integer likes, String content) {
        this.userId = userId;
        this.productId = productId;
        this.orderId = orderId;
        this.likes = likes;
        this.content = content;
        this.status = Status.NORMAL;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Integer getLikes() {
        return likes;
    }

    public String getContent() {
        return content;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isSecreted() {
        if (this.status == Status.SECRET) {
            return true;
        }

        return false;
    }

    public Integer increaseLikes() {
        this.likes += 1;
        return this.likes;
    }

    public Integer decreaseLikes() {
        this.likes -= 1;
        return this.likes;
    }

    public void updateReview(String content, boolean isSecreted) {
        this.content = content;

        if (isSecreted) {
            toSecret();
        }
    }

    public void toNormal() {
        this.status = Status.NORMAL;
    }

    public void toBanned() {
        this.status = Status.BANNED;
    }

    public void toSecret() {
        this.status = Status.SECRET;
    }

    public void toBest() {
        this.status = Status.BEST;
    }

    public void softDeleted() {
        this.status = Status.DELETED;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }
}
