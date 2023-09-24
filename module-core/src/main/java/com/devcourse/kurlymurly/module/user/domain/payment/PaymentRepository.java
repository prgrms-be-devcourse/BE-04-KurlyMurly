package com.devcourse.kurlymurly.module.user.domain.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByUserIdAndId(Long userId, Long paymentId);

    @Query("SELECT e FROM Payment e WHERE e.userId = :userId AND (e.status = 'DEFAULT' OR e.status = 'NORMAL')")
    List<Payment> findByUserIdAndStatus(@Param("userId") Long userId);
}
