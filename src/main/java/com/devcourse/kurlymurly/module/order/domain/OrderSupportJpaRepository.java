package com.devcourse.kurlymurly.module.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderSupportJpaRepository extends JpaRepository<OrderSupport, Long> {
    Optional<List<OrderSupport>> findAllByUserId(Long userId);
    Optional<OrderSupport> findByOrderId(Long orderId);
}
