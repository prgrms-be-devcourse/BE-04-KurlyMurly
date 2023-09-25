package com.devcourse.kurlymurly.module.product.domain.review;

import com.devcourse.kurlymurly.module.product.ProductFixture;
import com.devcourse.kurlymurly.module.product.domain.ProductRepository;
import com.devcourse.kurlymurly.module.user.UserFixture;
import com.devcourse.kurlymurly.module.auth.AuthRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static com.devcourse.kurlymurly.module.product.ReviewFixture.REVIEW_FIXTURE;
import static com.devcourse.kurlymurly.module.product.ReviewFixture.SECRET_REVIEW_FIXTURE;

@DataJpaTest
class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void findAllByUserIdAndStatusIsNotOrderByIdDesc_Success() {
        // given
        Long userId = 1L;
        Review review = REVIEW_FIXTURE.toEntity();
        Review review2 = SECRET_REVIEW_FIXTURE.toEntity();
        reviewRepository.saveAll(List.of(review, review2));

        // when
       // List<Review> result = reviewRepository.findAllByUserIdAndStatusIsNotOrderByIdDesc(userId, DELETED);

        // TODO: then
//        assertThat(result).isNotEmpty().hasSize(2);
//        assertThat(result).extracting("productName").containsExactlyInAnyOrder(review.getProductName(), review2.getProductName());
//        assertThat(result).extracting("isSecret").containsExactlyInAnyOrder(true, false);
    }

    @Test
    void getTenReviewsOfProductFromStart() {
        productRepository.save(ProductFixture.LA_GOGI.toEntity());
        authRepository.save(UserFixture.USER_FIXTURE.toEntity());
        reviewRepository.save(REVIEW_FIXTURE.toEntity());

//        Slice<Review> tenReviewsOfProductFromStart = reviewRepository.getTenReviewsOfProductFromStart(1L, 10L);
//        System.out.println(userRepository.findById(1L).get());
//        testEntityManager.flush();

//        Assertions.assertNotEquals(0, tenReviewsOfProductFromStart.getSize());
//        System.out.println(tenReviewsOfProductFromStart.get());
    }
}
