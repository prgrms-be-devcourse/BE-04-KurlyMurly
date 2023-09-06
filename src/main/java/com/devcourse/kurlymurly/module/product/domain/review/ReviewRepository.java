package com.devcourse.kurlymurly.module.product.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static com.devcourse.kurlymurly.module.product.domain.review.Review.Status;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByUserIdAndStatusIsNotOrderByIdDesc(Long userId, Status status);
}
