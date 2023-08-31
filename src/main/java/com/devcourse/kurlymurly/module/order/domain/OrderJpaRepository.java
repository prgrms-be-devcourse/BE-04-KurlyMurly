package com.devcourse.kurlymurly.module.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
    Optional<List<Order>> findAllByUserId(Long userId);
}
