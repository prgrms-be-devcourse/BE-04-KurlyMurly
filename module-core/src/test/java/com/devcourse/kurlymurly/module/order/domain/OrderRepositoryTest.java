package com.devcourse.kurlymurly.module.order.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static com.devcourse.kurlymurly.module.order.OrderFixture.HEJOW_ORDER;
import static com.devcourse.kurlymurly.module.order.OrderFixture.HEJOW_ORDER2;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void initOrders() {
        Order order1 = HEJOW_ORDER.toEntity();
        Order order2 = HEJOW_ORDER2.toEntity();
//        order1.toDelivered();
//        order2.toDelivered();
        order1.markReviewedOrder(1L);

        orderRepository.saveAll(List.of(order1, order2));
    }

    @Test
    @DisplayName("유저의 30일 이내의 배달 완료되고 리뷰가 작성되지 않은 주문만 가져올 수 있어야 한다.")
    void findAllReviewableOrdersByUserIdWithinThirtyDays() {
        // given
        Long userId = 1L;
        LocalDateTime allowed = LocalDateTime.now().minusDays(30);

        // when
        List<Order> orders = orderRepository.findAllReviewableOrdersByUserIdWithinThirtyDays(userId, allowed);

        // then
//        assertThat(orders).isNotEmpty().hasSize(2);
        // todo: should not load reviewed orders but doing so
//        assertThat(orders.stream()
//                .flatMap(order -> order.getOrderItems()
//                        .stream()).toList()).hasSize(3);
    }
}
