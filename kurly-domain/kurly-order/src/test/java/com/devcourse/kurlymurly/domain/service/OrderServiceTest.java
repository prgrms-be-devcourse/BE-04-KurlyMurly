package com.devcourse.kurlymurly.domain.service;

import com.devcourse.kurlymurly.domain.order.OrderRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

//    @Mock
//    private PasswordEncoder passwordEncoder;

    // todo: after seperate layer
//    @Test
//    @DisplayName("주문하면 주문 번호가 생성되고 실제 주문 금액이 계산된다.")
//    void createOrder_test() {
//        // given
//        Order order = HEJOW_ORDER.toEntity();
//        User user = USER_FIXTURE.toEntity();
//        given(orderRepository.save(any())).willReturn(order);
//        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
//
//        // when
//        CreateOrder.Response response = orderService.createOrder(user, HEJOW_ORDER.toRequest());
//
//        // then
//        assertThat(response.orderNumber()).isNotNull();
//        assertThat(response.totalPrice()).isEqualTo(order.getActualPayAmount());
//    }
//
//    @Test
//    @DisplayName("잘못된 결제 비밀번호를 입력하면 주문이 생성되지 않는다.")
//    void createOrder_fail_test() {
//        // given
//        User user = USER_FIXTURE.toEntity();
//        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);
//
//        // when, then
//        assertThrows(KurlyBaseException.class, () -> orderService.createOrder(user, HEJOW_ORDER2.toRequest()));
//    }
}
