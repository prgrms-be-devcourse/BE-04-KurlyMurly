package com.devcourse.kurlymurly.module.product.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewLikeJpaRepository extends JpaRepository<ReviewLikes, Long> {
}
