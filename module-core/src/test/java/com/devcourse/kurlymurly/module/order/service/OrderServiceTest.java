package com.devcourse.kurlymurly.module.order.service;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.order.domain.Order;
import com.devcourse.kurlymurly.module.order.domain.OrderRepository;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.web.dto.order.CreateOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.devcourse.kurlymurly.module.order.OrderFixture.HEJOW_ORDER;
import static com.devcourse.kurlymurly.module.order.OrderFixture.HEJOW_ORDER2;
import static com.devcourse.kurlymurly.module.user.UserFixture.USER_FIXTURE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("주문하면 주문 번호가 생성되고 실제 주문 금액이 계산된다.")
    void createOrder_test() {
        // given
        Order order = HEJOW_ORDER.toEntity();
        User user = USER_FIXTURE.toEntity();
        given(orderRepository.save(any())).willReturn(order);
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);

        // when
        CreateOrder.Response response = orderService.createOrder(user, HEJOW_ORDER.toRequest());

        // then
        assertThat(response.orderNumber()).isNotNull();
        assertThat(response.totalPrice()).isEqualTo(order.getActualPayAmount());
    }

    @Test
    @DisplayName("잘못된 결제 비밀번호를 입력하면 주문이 생성되지 않는다.")
    void createOrder_fail_test() {
        // given
        User user = USER_FIXTURE.toEntity();
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

        // when, then
        assertThrows(KurlyBaseException.class, () -> orderService.createOrder(user, HEJOW_ORDER2.toRequest()));
    }
}
