package com.devcourse.kurlymurly.domain.order;

import com.devcourse.kurlymurly.web.product.ReviewResponse;

import java.util.List;

public interface CustomOrderRepository {
    List<ReviewResponse.Reviewable> findAllReviewableOrdersByUserIdWithinThirtyDays(Long userId);
}
