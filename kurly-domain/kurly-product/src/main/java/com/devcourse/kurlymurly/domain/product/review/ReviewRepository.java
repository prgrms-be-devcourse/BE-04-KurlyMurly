package com.devcourse.kurlymurly.domain.product.review;

import com.devcourse.kurlymurly.web.product.ReviewResponse;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("""
            SELECT NEW com.devcourse.kurlymurly.web.product.ReviewResponse$Reviewed(
                 r.id, p.id, p.name, r.content, r.isSecret, r.createAt, r.updatedAt
            )
            FROM Review r
            LEFT JOIN User u ON u.id = :userId
            LEFT JOIN Product p ON p.id = r.productId
            WHERE r.status in ('NORMAL', 'BEST')
            ORDER BY r.id DESC
            """)
    List<ReviewResponse.Reviewed> getAllReviewsByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT NEW com.devcourse.kurlymurly.web.product.ReviewResponse$OfProduct(
                 u.name, CAST(u.tier AS STRING), p.name, r.id, r.content, r.likes, r.createAt, r.isSecret
            )
            FROM Review r
            LEFT JOIN User u ON u.id = r.userId
            LEFT JOIN Product p ON p.id = r.productId
            WHERE r.status in ('NORMAL', 'BEST')
            AND r.id < :startId AND p.id = :productId
            ORDER BY r.id DESC
            LIMIT 10
            """)
    Slice<ReviewResponse.OfProduct> getTenReviewsOfProductFromStart(@Param("productId") Long productId, @Param("startId") Long startId);
}
