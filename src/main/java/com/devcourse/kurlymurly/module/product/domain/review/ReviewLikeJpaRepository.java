package com.devcourse.kurlymurly.module.product.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ReviewLikeJpaRepository extends JpaRepository<ReviewLike, Long> {
    Optional<ReviewLike> findByUserIdAndReviewId(Long userId, Long reviewId);
}
