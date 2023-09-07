package com.devcourse.kurlymurly.module.product.domain.review;

import com.devcourse.kurlymurly.module.BaseEntity;
import com.devcourse.kurlymurly.module.product.domain.Product;
import com.devcourse.kurlymurly.module.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {
    public enum Status { NORMAL, BANNED, BEST, DELETED,}

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Product product;

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

    public Review(User user, Product product, String content, boolean isSecret) {
        this.user = user;
        this.product = product;
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

    public void toSecret() {
        this.isSecret = true;
    }

    public void toBest() {
        this.status = Status.BEST;
    }

    public void softDeleted() {
        this.status = Status.DELETED;
    }

    public Long getProductId() {
        return product.getId();
    }

    public String getProductName() {
        return product.getName();
    }

    public String getContent() {
        return content;
    }

    public Integer getLikes() {
        return this.likes;
    }

    public boolean isSecret() {
        return isSecret;
    }

    public String getMaskedUserName() {
        return user.getMaskedUserName();
    }

    public String getUserTier() {
        return user.getTier().name();
    }
}
