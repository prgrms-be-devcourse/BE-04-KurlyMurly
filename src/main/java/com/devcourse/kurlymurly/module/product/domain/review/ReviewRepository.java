package com.devcourse.kurlymurly.module.product.domain.review;

import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.devcourse.kurlymurly.module.product.domain.review.Review.Status;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByUserIdAndStatusIsNotOrderByIdDesc(Long userId, Status status);

    @Query("SELECT r FROM Review r JOIN FETCH r.user JOIN FETCH r.product " +
            "WHERE r.status = 'NORMAL' " +
            "AND r.status = 'BEST' " +
            "AND r.product.id = :productId " +
            "AND r.id < :start " +
            "ORDER BY r.id DESC " +
            "LIMIT 10")
    Slice<Review> getTenReviewsOfProductFromStart(@Param("productId") Long productId, @Param("start") Long start);
}
