package com.devcourse.kurlymurly.module.order.service;

import com.devcourse.kurlymurly.module.order.domain.Order;
import com.devcourse.kurlymurly.module.order.domain.OrderRepository;
import com.devcourse.kurlymurly.web.dto.order.CreateOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.devcourse.kurlymurly.module.order.OrderFixture.HEJOW_ORDER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    private final Long userId = 1L;

    @Test
    @DisplayName("주문하면 주문 번호가 생성되고 실제 주문 금액이 계산된다.")
    void createOrder_test() {
        // given
        Order order = HEJOW_ORDER.toEntity();
        given(orderRepository.save(any())).willReturn(order);

        // when
        CreateOrder.Response response = orderService.createOrder(userId, HEJOW_ORDER.toRequest());

        // then
        assertThat(response.orderNumber()).isNotNull();
        assertThat(response.totalPrice()).isEqualTo(order.getActualPayAmount());
    }
}
