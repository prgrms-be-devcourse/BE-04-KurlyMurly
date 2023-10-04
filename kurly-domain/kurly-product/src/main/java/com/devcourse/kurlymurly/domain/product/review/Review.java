package com.devcourse.kurlymurly.domain.product.review;

import com.devcourse.kurlymurly.common.exception.KurlyBaseException;
import com.devcourse.kurlymurly.data.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.util.Objects;

import static com.devcourse.kurlymurly.common.exception.ErrorCode.INORDERABLE_PRODUCT;
import static com.devcourse.kurlymurly.common.exception.ErrorCode.NOT_AUTHOR;
import static com.devcourse.kurlymurly.domain.product.review.Review.Status.BANNED;
import static com.devcourse.kurlymurly.domain.product.review.Review.Status.BEST;
import static com.devcourse.kurlymurly.domain.product.review.Review.Status.DELETED;
import static com.devcourse.kurlymurly.domain.product.review.Review.Status.NORMAL;

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
        this.status = NORMAL;
        this.isSecret = isSecret;
    }

    public void liked() {
        this.likes += 1;
    }

    public void disliked() {
        this.likes -= 1;
    }

    public void update(String content, boolean isSecret) {
        this.content = content;
        this.isSecret = isSecret;
    }

    public void ban() {
        this.status = BANNED;
    }

    public void toBest() {
        validateInteractive();
        this.status = BEST;
    }

    private void validateInteractive() {
        if (this.status == DELETED || this.status == BANNED) {
            throw KurlyBaseException.withId(INORDERABLE_PRODUCT, this.getId());
        }
    }

    public void validateAuthor(Long userId) {
        if (!Objects.equals(this.userId, userId)) {
            throw KurlyBaseException.withId(NOT_AUTHOR, userId);
        }
    }

    public void softDelete() {
        this.status = DELETED;
    }

    public Long getProductId() {
        return this.productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public String getContent() {
        return content;
    }

    public boolean isSecret() {
        return isSecret;
    }
}
