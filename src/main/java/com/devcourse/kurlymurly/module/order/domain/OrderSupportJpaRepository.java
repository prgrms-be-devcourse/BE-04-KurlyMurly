package com.devcourse.kurlymurly.module.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderSupportJpaRepository extends JpaRepository<OrderSupport, Long> {
    List<OrderSupport> findAllByUserId(Long userId);
    List<OrderSupport> findByOrderId(Long orderId);
}
