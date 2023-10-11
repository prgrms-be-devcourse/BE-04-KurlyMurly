package com.devcourse.kurlymurly.domain.product.review;

import com.devcourse.kurlymurly.data.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "review_likes")
public class ReviewLike extends BaseEntity {
    @Column(name = "like_user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long reviewId;

    @Column(nullable = false)
    private boolean isDeleted;

    protected ReviewLike() {
    }

    public ReviewLike(Long userId, Long reviewId) {
        this.userId = userId;
        this.reviewId = reviewId;
        this.isDeleted = false;
    }

    public void cancel() {
        this.isDeleted = true;
    }

    public void activate() {
        this.isDeleted = false;
    }

    public Long getReviewId() {
        return reviewId;
    }
}
