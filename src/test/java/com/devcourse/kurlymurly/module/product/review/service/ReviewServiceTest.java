package com.devcourse.kurlymurly.module.product.review.service;

import com.devcourse.kurlymurly.module.product.domain.review.ReviewLikeRepository;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewRepository;
import com.devcourse.kurlymurly.module.product.service.ProductRetrieve;
import com.devcourse.kurlymurly.module.product.service.ReviewService;
import com.devcourse.kurlymurly.module.user.domain.UserRepository;
import com.devcourse.kurlymurly.web.dto.product.review.CreateReview;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewLikeCreate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewLikeRepository reviewLikeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRetrieve productRetrieve;

    private static CreateReview.Request reviewRequest;

    private static ReviewLikeCreate.Request reviewLikeRequest;


}
