package com.devcourse.kurlymurly.module.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserId(Long userId);
}
