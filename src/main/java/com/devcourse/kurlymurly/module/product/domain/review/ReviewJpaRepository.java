package com.devcourse.kurlymurly.module.product.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByUserId(Long userId);
}
