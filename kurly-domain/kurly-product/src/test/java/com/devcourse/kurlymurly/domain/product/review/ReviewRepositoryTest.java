package com.devcourse.kurlymurly.domain.product.review;

import com.devcourse.kurlymurly.domain.product.Product;
import com.devcourse.kurlymurly.domain.product.ProductRepository;
import com.devcourse.kurlymurly.web.product.ReviewResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Slice;

import java.util.Collections;
import java.util.List;

import static com.devcourse.kurlymurly.domain.product.ProductFixture.LA_GOGI;
import static com.devcourse.kurlymurly.domain.product.ReviewFixture.REVIEW_FIXTURE;
import static com.devcourse.kurlymurly.domain.product.ReviewFixture.SECRET_REVIEW_FIXTURE;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ReviewRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    private Product product;

    @BeforeEach
    void initProduct() {
        product = productRepository.save(LA_GOGI.toEntity());
    }

    @Test
    @DisplayName("유저가 작성한 리뷰를 아이디 내림차순으로 가져올 수 있어야한다.")
    void getAllReviewsByUserId_Success() {
        // given
        Long userId = 1L;
        Review review = REVIEW_FIXTURE.toEntity(product.getId());
        Review review2 = SECRET_REVIEW_FIXTURE.toEntity(product.getId());
        reviewRepository.saveAll(List.of(review, review2));

        // when
        List<ReviewResponse.Reviewed> result = reviewRepository.getAllReviewsByUserId(userId);

        // then
        assertThat(result).isNotEmpty().hasSize(2);
        assertThat(result).extracting("reviewId").isSortedAccordingTo(Collections.reverseOrder());
        assertThat(result).extracting("productName").containsExactlyInAnyOrder(review.getProductName(), review2.getProductName());
        assertThat(result).extracting("isSecret").containsExactlyInAnyOrder(true, false);
    }

    @Test
    @DisplayName("마지막 번호부터 상품에 대한 10건의 리뷰를 가져올 수 있어야한다.")
    void getTenReviewsOfProductFromStart_Success() {
        // given
        Review review = reviewRepository.save(REVIEW_FIXTURE.toEntity(product.getId()));

        // when
        Slice<ReviewResponse.OfProduct> responses = reviewRepository.getTenReviewsOfProductFromStart(product.getId(), 11L);

        // then
        assertThat(responses).isNotEmpty().hasSize(1);

        ReviewResponse.OfProduct response = responses.get().toList().get(0);
        assertThat(response.productName()).isEqualTo(product.getName());
        assertThat(response.reviewId()).isEqualTo(review.getId());
    }
}
