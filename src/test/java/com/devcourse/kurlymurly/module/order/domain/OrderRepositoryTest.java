package com.devcourse.kurlymurly.module.order.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static com.devcourse.kurlymurly.module.order.OrderFixture.HEJOW_ORDER;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("유저의 30일 이내의 배달 완료된 주문만 가져올 수 있어야 한다.")
    void findAllReviewableOrdersByUserIdWithinThirtyDays() {
        // given
        Long userId = 1L;
        LocalDateTime allowed = LocalDateTime.now().minusDays(30);

        Order order = HEJOW_ORDER.toEntity();
        order.delivered();
        orderRepository.save(order);

        // when
        List<Order> orders = orderRepository.findAllReviewableOrdersByUserIdWithinThirtyDays(userId, allowed);

        // then
        assertThat(orders).isNotEmpty().hasSize(1);
    }
}
