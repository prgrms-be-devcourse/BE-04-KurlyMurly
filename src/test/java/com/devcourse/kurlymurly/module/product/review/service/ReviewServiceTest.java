package com.devcourse.kurlymurly.module.product.review.service;

import com.devcourse.kurlymurly.module.product.domain.Product;
import com.devcourse.kurlymurly.module.product.domain.review.Review;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewRepository;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewLikeJpaRepository;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewLike;
import com.devcourse.kurlymurly.module.product.domain.review.service.ReviewService;
import com.devcourse.kurlymurly.module.product.service.ProductRetrieve;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.module.user.domain.UserRepository;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewCreate;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewLikeCreate;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewResponse;
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

import static com.devcourse.kurlymurly.module.product.ProductFixture.LA_GOGI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewLikeJpaRepository reviewLikeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRetrieve productRetrieve;

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
    @DisplayName("해당 상품의 리뷰 리스트를 조회한다")
    void getReviewsOfProduct_test() {
        // given
        ReviewCreate.Request request1 = new ReviewCreate.Request (1L, 1L, 1L, 2, "좋아요.~");
        ReviewCreate.Request request2 = new ReviewCreate.Request (2L, 1L, 4L, 7, "별루에영.~");
        ReviewCreate.Request request3 = new ReviewCreate.Request (3L, 1L, 8L, 0, "시이원하네염.~");

        Review review1 = createReviewEntity(request1);
        Review review2 = createReviewEntity(request2);
        Review review3 = createReviewEntity(request3);

        User user1 = new User("수연장", "abd1234", "11", "aaaa@gmail.com", null, "1234-4567");
        User user2 = new User("장수연", "abd1234", "11", "aaaa@gmail.com", null, "1234-4567");
        User user3 = new User("연장수", "abd1234", "11", "aaaa@gmail.com", null, "1234-4567");

        Product product = LA_GOGI.toEntity();
        List<Review> reviewList = List.of(review1, review2, review3);

        // mocking
        given(reviewRepository.save(any())).willReturn(review1);
        given(reviewRepository.save(any())).willReturn(review2);
        given(reviewRepository.save(any())).willReturn(review3);
        given(reviewRepository.findAllByProductIdOrderByCreateAt(any())).willReturn(reviewList);
        given(userRepository.findById(any())).willReturn(Optional.of(user1));
        given(productRetrieve.findByIdOrThrow(any())).willReturn(product);
        given(userRepository.findById(any())).willReturn(Optional.of(user2));
        given(userRepository.findById(any())).willReturn(Optional.of(user3));

        // when
        reviewService.registerReview(request1);
        reviewService.registerReview(request2);
        reviewService.registerReview(request3);
        List<ReviewResponse.ReviewOfProduct> reviewOfProductList = reviewService.getReviewsOfProduct(1L);

        // given
        Assertions.assertEquals(3, reviewOfProductList.size());
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
