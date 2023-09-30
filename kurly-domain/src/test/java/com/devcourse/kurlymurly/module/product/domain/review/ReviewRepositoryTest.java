package com.devcourse.kurlymurly.module.product.domain.review;

import com.devcourse.kurlymurly.domain.user.User;
import com.devcourse.kurlymurly.domain.user.UserRepository;
import com.devcourse.kurlymurly.module.product.ProductFixture;
import com.devcourse.kurlymurly.module.product.domain.Product;
import com.devcourse.kurlymurly.module.product.domain.ProductRepository;
import com.devcourse.kurlymurly.web.product.ReviewResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Slice;

import java.util.Collections;
import java.util.List;

import static com.devcourse.kurlymurly.module.UserFixture.USER_FIXTURE;
import static com.devcourse.kurlymurly.module.product.ReviewFixture.REVIEW_FIXTURE;
import static com.devcourse.kurlymurly.module.product.ReviewFixture.SECRET_REVIEW_FIXTURE;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User user;
    private Product product;

    @BeforeEach
    void setup() {
        user = userRepository.save(USER_FIXTURE.toEntity());
        product = productRepository.save(ProductFixture.LA_GOGI.toEntity());
    }

    @Test
    @DisplayName("유저가 작성한 리뷰를 아이디 내림차순으로 가져올 수 있어야한다.")
    void getAllReviewsByUserId_Success() {
        // given
        Long userId = user.getId();
        Review review = REVIEW_FIXTURE.toEntity(user, product);
        Review review2 = SECRET_REVIEW_FIXTURE.toEntity(user, product);
        reviewRepository.saveAll(List.of(review, review2));

        entityManager.clear(); // to detach entities

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
        Review review = reviewRepository.save(REVIEW_FIXTURE.toEntity(user, product));

        entityManager.clear(); // to detach entities

        // when
        Slice<ReviewResponse.OfProduct> responses = reviewRepository.getTenReviewsOfProductFromStart(product.getId(), 10L);

        // then
        assertThat(responses).isNotEmpty().hasSize(1);

        ReviewResponse.OfProduct response = responses.get().toList().get(0);
        assertThat(response.productName()).isEqualTo(product.getName());
        assertThat(response.reviewId()).isEqualTo(review.getId());
    }
}
