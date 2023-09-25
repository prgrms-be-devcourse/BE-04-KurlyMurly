package com.devcourse.kurlymurly.module.product.domain.review;

import com.devcourse.kurlymurly.web.dto.product.review.ReviewResponse;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("""
           SELECT NEW com.devcourse.kurlymurly.web.dto.product.review.ReviewResponse$Reviewed(
                r.id, p.id, p.name, r.content, r.isSecret, r.createAt, r.updatedAt
           )
           FROM Review r
           LEFT JOIN r.user u ON u.id = :userId
           LEFT JOIN r.product p
           WHERE r.status in ('NORMAL', 'BEST')
           ORDER BY r.id DESC
           """)
    List<ReviewResponse.Reviewed> getAllReviewsByUserId(@Param("userId") Long userId);

    @Query("SELECT r FROM Review r JOIN FETCH r.user JOIN FETCH r.product " +
            "WHERE r.status = 'NORMAL' " +
            "OR r.status = 'BEST' " +
            "AND r.product.id = :productId " +
            "AND r.id < :start " +
            "ORDER BY r.id DESC " +
            "LIMIT 10")
    Slice<Review> getTenReviewsOfProductFromStart(@Param("productId") Long productId, @Param("start") Long start);
}
