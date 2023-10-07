package com.devcourse.kurlymurly.domain.order;

import com.devcourse.kurlymurly.web.product.ReviewResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.devcourse.kurlymurly.domain.order.Order.*;
import static com.devcourse.kurlymurly.domain.order.OrderFixture.HEJOW_ORDER;
import static org.assertj.core.api.Assertions.assertThat;

@Disabled
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("유저의 30일 이내의 배달 완료되고 후기가 작성되지 않은 주문 상품만 가져와야 한다.")
    void findAllReviewableOrdersByUserIdWithinThirtyDays() {
        // given
        Long userId = 1L;
        int reviewedIndex = 1;

        Order hejowOrder = HEJOW_ORDER.toSpecificStateEntity(Status.DELIVERED);
        hejowOrder.reviewOrderLine(reviewedIndex);

        Order order = orderRepository.save(hejowOrder);

        // when
        List<ReviewResponse.Reviewable> responses = orderRepository.findReviewableOrdersByUserIdWithinThirtyDays(userId);

        // then
        assertThat(responses).hasSize(1);

        ReviewResponse.Reviewable response = responses.get(0);
        assertThat(response.deliveredAt()).isEqualTo(order.getDeliveredAt());
        assertThat(response.reviewDeadLine()).isEqualTo(response.deliveredAt().plusDays(30));
        assertThat(response.lineIndex()).isNotEqualTo(reviewedIndex);
    }
}
