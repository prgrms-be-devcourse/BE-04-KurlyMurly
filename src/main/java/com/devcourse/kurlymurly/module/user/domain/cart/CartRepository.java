package com.devcourse.kurlymurly.module.user.domain.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByProductIdAndUserId(Long productId, Long userId);
    List<Cart> findAllByUserId(Long userId);
}
