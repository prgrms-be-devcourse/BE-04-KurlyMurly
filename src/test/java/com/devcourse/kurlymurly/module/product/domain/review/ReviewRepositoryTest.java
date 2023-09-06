package com.devcourse.kurlymurly.module.product.domain.review;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.devcourse.kurlymurly.module.product.ReviewFixture.REVIEW_FIXTURE;
import static com.devcourse.kurlymurly.module.product.ReviewFixture.SECRET_REVIEW_FIXTURE;
import static com.devcourse.kurlymurly.module.product.domain.review.Review.Status.DELETED;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void findAllByUserIdAndStatusIsNotOrderByIdDesc_Success() {
        // given
        Long userId = 1L;
        Review review = REVIEW_FIXTURE.toEntity();
        Review review2 = SECRET_REVIEW_FIXTURE.toEntity();
        reviewRepository.saveAll(List.of(review, review2));

        // when
        List<Review> result = reviewRepository.findAllByUserIdAndStatusIsNotOrderByIdDesc(userId, DELETED);

        // then
        assertThat(result).isNotEmpty().hasSize(2);
        assertThat(result).extracting("productName").containsExactlyInAnyOrder(review.getProductName(), review2.getProductName());
        assertThat(result).extracting("isSecret").containsExactlyInAnyOrder(true, false);
    }
}
