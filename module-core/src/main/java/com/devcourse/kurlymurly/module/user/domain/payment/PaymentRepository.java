package com.devcourse.kurlymurly.module.user.domain.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByUserIdAndId(Long userId, Long paymentId);

    @Query("""
            SELECT CONCAT(SUBSTRING(p.payInfo, 1, 6), '******', SUBSTRING(p.payInfo, 13, 4))
            FROM Payment p 
            WHERE p.userId = :userId AND p.status IN ('DEFAULT','NORMAL')
            """)
    List<String> findAllByUserId(@Param("userId") Long userId);
}
