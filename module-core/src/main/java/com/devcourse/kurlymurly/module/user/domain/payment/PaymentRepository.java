package com.devcourse.kurlymurly.module.user.domain.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Optional<Payment> findByUserIdAndId(Long userId,Long paymentId);
}
