package com.devcourse.kurlymurly.module.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserId(Long userId);

    @Query("SELECT o " +
            "FROM Order o JOIN FETCH o.orderItems ot " +
            "WHERE o.status = 'DELIVERED' AND o.updatedAt > :allowed " +
            "AND o.userId = :userId AND ot.isReviewed = FALSE ")
    List<Order> findAllReviewableOrdersByUserIdWithinThirtyDays(@Param("userId") Long userId, @Param("allowed") LocalDateTime allowed);
}
