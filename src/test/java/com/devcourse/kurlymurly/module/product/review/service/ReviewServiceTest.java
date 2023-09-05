package com.devcourse.kurlymurly.module.product.review.service;

import com.devcourse.kurlymurly.module.product.domain.review.Review;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewJpaRepository;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewLikeJpaRepository;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewLike;
import com.devcourse.kurlymurly.module.product.domain.review.service.ReviewService;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewCreate;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewLikeCreate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewJpaRepository reviewRepository;

    @Mock
    private ReviewLikeJpaRepository reviewLikeRepository;

    private static ReviewCreate.Request reviewRequest;

    private static ReviewLikeCreate.Request reviewLikeRequest;

    private Review createReviewEntity(ReviewCreate.Request request) {
        return new Review(
                request.userId(),
                request.productId(),
                request.orderId(),
                request.likes(),
                request.content()
        );
    }

    private ReviewLike createReviewLikeEntity(ReviewLikeCreate.Request request) {
        return new ReviewLike(
                reviewRequest.userId(),
                request.reviewId()
        );
    }

    @BeforeEach
    void setUp() {
        reviewRequest = new ReviewCreate.Request(1L, 1L, 1L, 10, "해당 상품은 단맛이 너무 강해요!");
        reviewLikeRequest = new ReviewLikeCreate.Request(1L, 1L);
    }

    @Test
    @DisplayName("리뷰를 등록한다")
    void registerReview_test() {
        // given
        Review review = createReviewEntity(reviewRequest);

        // mocking
        given(reviewRepository.save(any())).willReturn(review);

        // when
        ReviewCreate.Response response = reviewService.registerReview(reviewRequest);

        // then
        Assertions.assertEquals(reviewRequest.orderId(), response.orderId());
        Assertions.assertEquals(reviewRequest.userId(), response.userId());
        Assertions.assertEquals(reviewRequest.content(), response.content());
    }

    @Test
    @DisplayName("리뷰 id로 해당 리뷰를 조회한다")
    void findById_test() {
        // given
        Review review = createReviewEntity(reviewRequest);

        // mocking
        given(reviewRepository.save(any())).willReturn(review);
        given(reviewRepository.findById(any())).willReturn(Optional.of(review));

        // when
        reviewService.registerReview(reviewRequest);
        Review reviewEntity = reviewService.findReviewById(review.getId());

        // then
        assertThat(review).usingRecursiveComparison().isEqualTo(reviewEntity);
    }

    @Test
    @DisplayName("해당 user가 작성한 리뷰들을 조회한다")
    void findAllByUserId_test() {
        // given
        Review review = createReviewEntity(reviewRequest);

        // mocking
        given(reviewRepository.save(any())).willReturn(review);
        given(reviewRepository.findAllByUserId(any())).willReturn(List.of(review));

        // when
        reviewService.registerReview(reviewRequest);
        List<Review> reviewEntity = reviewService.findAllByUserId(review.getUserId());

        // then
        assertThat(review).usingRecursiveComparison().isEqualTo(reviewEntity.get(0));
    }

    @Test
    @DisplayName("리뷰 내용을 수정한다")
    void updateReviewContent_test() {
        // given
        Review review = createReviewEntity(reviewRequest);
        String updatedContent = "수정한 리뷰 내용입니다! 이 음식은 달지 않고 매워요!";

        // mocking
        given(reviewRepository.save(any())).willReturn(review);
        given(reviewRepository.findById(any())).willReturn(Optional.of(review));

        // when
        reviewService.registerReview(reviewRequest);
        System.out.println("수정 전 content: " + review.getContent());
        reviewService.updateReviewContent(review.getId(), updatedContent, false);

        // then
        Assertions.assertEquals(updatedContent, review.getContent());
        System.out.println("수정 후 content: " + review.getContent());
    }

    @Test
    @DisplayName("리뷰를 normal 상태로 변경한다")
    void updateToNormal_test() {
        // given
        Review review = createReviewEntity(reviewRequest);

        // mocking
        given(reviewRepository.save(any())).willReturn(review);
        given(reviewRepository.findById(any())).willReturn(Optional.of(review));

        // when
        reviewService.registerReview(reviewRequest);
        reviewService.updateToNormal(review.getId());

        // then
        Assertions.assertEquals("NORMAL", review.getStatus().name());
    }

    @Test
    @DisplayName("리뷰를 banned 상태로 변경한다")
    void updateToBanned_test() {
        // given
        Review review = createReviewEntity(reviewRequest);

        // mocking
        given(reviewRepository.save(any())).willReturn(review);
        given(reviewRepository.findById(any())).willReturn(Optional.of(review));

        // when
        reviewService.registerReview(reviewRequest);
        reviewService.updateToBanned(review.getId());

        // then
        Assertions.assertEquals("BANNED", review.getStatus().name());
    }

    @Test
    @DisplayName("리뷰를 best 상태로 변경한다")
    void updateToBest_test() {
        // given
        Review review = createReviewEntity(reviewRequest);

        // mocking
        given(reviewRepository.save(any())).willReturn(review);
        given(reviewRepository.findById(any())).willReturn(Optional.of(review));

        // when
        reviewService.registerReview(reviewRequest);
        reviewService.updateToBest(review.getId());

        // then
        Assertions.assertEquals("BEST", review.getStatus().name());
    }

    @Test
    @DisplayName("리뷰를 삭제한다")
    void deleteReview_test() {
        // given
        Review review = createReviewEntity(reviewRequest);

        // mocking
        given(reviewRepository.save(any())).willReturn(review);
        given(reviewRepository.findById(any())).willReturn(Optional.of(review));

        // when
        reviewService.registerReview(reviewRequest);
        reviewService.deleteReview(review.getId());

        // then
        Assertions.assertEquals("DELETED", review.getStatus().name());
    }

    @Test
    @DisplayName("해당 리뷰에 좋아요를 누른다")
    void activeReviewLike_test() {
        // given
        Review review = createReviewEntity(reviewRequest);
        ReviewLike reviewLike = createReviewLikeEntity(reviewLikeRequest);

        // mocking
        given(reviewRepository.save(any())).willReturn(review);
        given(reviewLikeRepository.findByUserIdAndReviewId(any(), any())).willReturn(Optional.of(reviewLike));
        given(reviewRepository.findById(any())).willReturn(Optional.of(review));
        given(reviewRepository.findById(any())).willReturn(Optional.of(review));

        // when
        reviewService.registerReview(reviewRequest);
        reviewService.activeReviewLike(reviewLike.getUserId(), review.getId());

        // then
        Assertions.assertEquals(11, review.getLikes());
    }

    @Test
    @DisplayName("등록한 리뷰 좋아요를 취소한다")
    void cancelReviewLike_test() {
        // given
        Review review = createReviewEntity(reviewRequest);
        ReviewLike reviewLike = createReviewLikeEntity(reviewLikeRequest);

        // mocking
        given(reviewRepository.save(any())).willReturn(review);
        given(reviewLikeRepository.findByUserIdAndReviewId(any(), any())).willReturn(Optional.of(reviewLike));
        given(reviewRepository.findById(any())).willReturn(Optional.of(review));
        given(reviewLikeRepository.findById(any())).willReturn(Optional.of(reviewLike));

        // when
        reviewService.registerReview(reviewRequest);
        reviewService.activeReviewLike(reviewLike.getUserId(), reviewLike.getReviewId());
        reviewService.cancelReviewLike(reviewLike.getId());

        // then
        Assertions.assertEquals(10, review.getLikes());
    }

    @Test
    @DisplayName("리뷰 좋아요를 취소하고 다시 활성화")
    void reactiveReviewLike_test() {
        // given
        Review review = createReviewEntity(reviewRequest);
        ReviewLike reviewLike = createReviewLikeEntity(reviewLikeRequest);

        // mocking
        given(reviewRepository.save(any())).willReturn(review);
        given(reviewLikeRepository.findByUserIdAndReviewId(any(), any())).willReturn(Optional.of(reviewLike));
        given(reviewRepository.findById(any())).willReturn(Optional.of(review));
        given(reviewLikeRepository.findById(any())).willReturn(Optional.of(reviewLike));

        // when
        reviewService.registerReview(reviewRequest);
        reviewService.activeReviewLike(reviewLike.getUserId(), reviewLike.getReviewId());
        reviewService.cancelReviewLike(reviewLike.getId());
        reviewService.activeReviewLike(reviewLike.getUserId(), reviewLike.getReviewId());

        // then
        Assertions.assertEquals(11, review.getLikes());
    }
}
