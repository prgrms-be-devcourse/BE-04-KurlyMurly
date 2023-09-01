package com.devcourse.kurlymurly.module.product.domain.review;

import com.devcourse.kurlymurly.module.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "review_likes")
public class ReviewLikes extends BaseEntity {
    @Column(nullable = false)
    private Long likeUserId;

    @Column(nullable = false)
    private Long reviewId;

    @Column(nullable = false)
    private boolean isDeleted;

    protected ReviewLikes() {
    }

    public ReviewLikes(Long likeUserId, Long reviewId) {
        this.likeUserId = likeUserId;
        this.reviewId = reviewId;
        this.isDeleted = false;
    }

    public void cancelLike() {
        this.isDeleted = true;
    }

    public void activeLike() {
        this.isDeleted = false;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public Long getLikeUserId() {
        return likeUserId;
    }
}
