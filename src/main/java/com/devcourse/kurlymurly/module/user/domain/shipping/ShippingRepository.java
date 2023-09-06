package com.devcourse.kurlymurly.module.user.domain.shipping;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShippingRepository extends JpaRepository<Shipping, Long> {
    List<Shipping> findAllByUserId(Long userId);

    Optional<Shipping> findByIdAndUserId(Long addressId, Long userId);
}
