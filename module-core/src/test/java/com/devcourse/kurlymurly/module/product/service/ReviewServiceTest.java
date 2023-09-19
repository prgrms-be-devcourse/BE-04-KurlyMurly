package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.module.product.domain.review.Review;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewLikeRepository;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewRepository;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.devcourse.kurlymurly.module.product.ReviewFixture.REVIEW_FIXTURE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    @InjectMocks
    private ReviewQuery reviewQuery;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewLikeRepository reviewLikeRepository;

    private Long userId = 1L;
    private Review review;

    @BeforeEach
    void initReview() {
        review = REVIEW_FIXTURE.toEntity();
    }

    @Test
    @DisplayName("유저가 작성한 리뷰가 응답 객체로 잘 변환되어야 한다")
    void findAllByUserIdAndStatusIsNotOrderByIdDesc_Success() {
        // given
        review.softDelete();

        given(reviewRepository.findAllByUserIdAndStatusIsNotOrderByIdDesc(any(), any())).willReturn(List.of(review));

        // when
        List<Review> reviews = reviewQuery.getAllReviewsOfUser(userId);

        // then
        then(reviewRepository).should(times(1)).findAllByUserIdAndStatusIsNotOrderByIdDesc(any(), any());
        assertThat(reviews).isNotEmpty().hasSize(1);

        Review response = reviews.get(0);
        assertThat(response.getProductId()).isEqualTo(review.getProductId());
        assertThat(response.isSecret()).isFalse();
    }
}
