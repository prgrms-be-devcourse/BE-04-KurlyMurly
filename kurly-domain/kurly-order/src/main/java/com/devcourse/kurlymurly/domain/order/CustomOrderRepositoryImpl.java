package com.devcourse.kurlymurly.domain.order;

import com.devcourse.kurlymurly.web.product.ReviewResponse;
import jakarta.persistence.EntityManager;

import java.sql.Timestamp;
import java.util.List;

class CustomOrderRepositoryImpl implements CustomOrderRepository {
    private final EntityManager entityManager;

    public CustomOrderRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<ReviewResponse.Reviewable> findReviewableOrdersByUserIdWithinThirtyDays(Long userId) {
        String sql = """
                SELECT ol.product_id AS productId, ol.product_name AS productName, ol.line_index AS lineIndex,
                    ol.image_url AS imageUrl, o.delivered_at AS deliveredAt, DATE_ADD(o.delivered_at, INTERVAL 30 DAY) AS reviewDeadLine
                FROM orders o
                JOIN order_lines ol ON o.id = ol.order_id
                WHERE o.status = 'DELIVERED' AND o.user_id = :userId AND ol.is_reviewed = false
                AND o.delivered_at BETWEEN DATE_SUB(NOW(), INTERVAL 30 DAY) AND NOW()
                """;

        List<Object[]> results = entityManager.createNativeQuery(sql)
                .setParameter("userId", userId)
                .getResultList();

        return results.stream()
                .map(this::toReviewable)
                .toList();
    }

    private ReviewResponse.Reviewable toReviewable(Object[] result) {
        return new ReviewResponse.Reviewable(
                (Long) result[0],
                (String) result[1],
                (Integer) result[2],
                (String) result[3],
                ((Timestamp) result[4]).toLocalDateTime(),
                ((Timestamp) result[5]).toLocalDateTime()
        );
    }
}
