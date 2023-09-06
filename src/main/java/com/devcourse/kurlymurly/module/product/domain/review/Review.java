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
    public enum Status { NORMAL, BANNED, BEST, DELETED,}

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long productId;

    @Column(length = 50, nullable = false)
    private String productName;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Column(nullable = false)
    private Integer likes;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private boolean isSecret;

    protected Review() {
    }

    public Review(Long userId, Long productId, String productName, String content, boolean isSecret) {
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
        this.content = content;
        this.likes = 0;
        this.status = Status.NORMAL;
        this.isSecret = isSecret;
    }

    public Integer liked() {
        this.likes += 1;
        return this.likes;
    }

    public Integer disliked() {
        this.likes -= 1;
        return this.likes;
    }

    public void updateReview(String content, boolean isSecret) {
        this.content = content;
        this.isSecret = isSecret;
    }

    public void toBanned() {
        this.status = Status.BANNED;
    }

    public void secret() {
        this.isSecret = true;
    }

    public void toBest() {
        this.status = Status.BEST;
    }

    public void softDeleted() {
        this.status = Status.DELETED;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getContent() {
        return content;
    }

    public boolean isSecret() {
        return isSecret;
    }
}
