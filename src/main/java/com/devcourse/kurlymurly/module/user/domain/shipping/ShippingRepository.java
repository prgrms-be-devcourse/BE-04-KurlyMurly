package com.devcourse.kurlymurly.module.user.domain.shipping;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShippingRepository extends JpaRepository<Shipping, Long> {
    List<Shipping> findAllByUserId(Long userId);
}
