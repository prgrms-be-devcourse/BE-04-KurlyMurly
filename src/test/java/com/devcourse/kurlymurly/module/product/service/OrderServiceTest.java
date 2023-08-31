package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.module.order.domain.Order;
import com.devcourse.kurlymurly.module.order.domain.OrderJpaRepository;
import com.devcourse.kurlymurly.module.order.service.OrderService;
import com.devcourse.kurlymurly.web.dto.order.OrderCreate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderJpaRepository orderRepository;

    private static OrderCreate.Request request;

    private Order createOrderEntity(OrderCreate.Request request) {
        return new Order(request.userId(), request.shippingId(), request.totalPrice(), request.payment());
    }

    @BeforeEach
    void setUp() {
        request = new OrderCreate.Request(1L, 1L, 22000, "PAYCO");
    }

    @Test
    @DisplayName("주문건을 생성한다")
    void createOrder_test() {
        // given
        Order order = createOrderEntity(request);

        // mocking
        given(orderRepository.save(any())).willReturn(order);

        // when
        Order orderEntity = orderService.createOrder(request.userId(), request.shippingId(), request.totalPrice(), request.payment());

        // then
        Assertions.assertEquals(22000, orderEntity.getTotalPrice());
        assertThat(order).usingRecursiveComparison().isEqualTo(orderEntity);
    };

    @Test
    @DisplayName("주문 id에 해당하는 주문건을 조회한다")
    void findById_test() {
        // given
        Order order = createOrderEntity(request);

        // mocking
        given(orderRepository.save(any())).willReturn(order);
        given(orderRepository.findById(any())).willReturn(Optional.of(order));

        // when
        orderService.createOrder(request.userId(), request.shippingId(), request.totalPrice(), request.payment());
        Order orderEntity = orderService.findById(request.userId());

        // then
        assertThat(order).usingRecursiveComparison().isEqualTo(orderEntity);
    }

    @Test
    @DisplayName("해당 user의 주문건을 조회한다")
    void findAllByUserId_test() {
        // given
        Order order = createOrderEntity(request);

        // mocking
        given(orderRepository.save(any())).willReturn(order);
        given(orderRepository.findAllByUserId(any())).willReturn(Optional.of(List.of(order)));

        // when
        orderService.createOrder(request.userId(), request.shippingId(), request.totalPrice(), request.payment());
        List<Order> orderEntity = orderService.findAllByUserId(request.userId());

        // then
        assertThat(order).usingRecursiveComparison().isEqualTo(orderEntity.get(0));
    }

    @Test
    @DisplayName("주문이 출고중 상태로 수정한다")
    void updateOrderToProcessing_test() {
        // given
        Order order = createOrderEntity(request);

        // mocking
        given(orderRepository.save(any())).willReturn(order);

        // when
        orderService.createOrder(request.userId(), request.shippingId(), request.totalPrice(), request.payment());
        order.processingOrder();

        // then
        Assertions.assertEquals("PROCESSING", order.getStatus());
    }

    @Test
    @DisplayName("주문이 배달중 상태로 수정한다")
    void updateOrderToDelivering_test() {
        // given
        Order order = createOrderEntity(request);

        // mocking
        given(orderRepository.save(any())).willReturn(order);

        // when
        orderService.createOrder(request.userId(), request.shippingId(), request.totalPrice(), request.payment());
        order.deliveringOrder();

        // then
        Assertions.assertEquals("DELIVERY_ING", order.getStatus());
    }

    @Test
    @DisplayName("주문이 배달 완료된 상태로 수정한다")
    void updateOrderToDeliveryDone_test() {
        // given
        Order order = createOrderEntity(request);

        // mocking
        given(orderRepository.save(any())).willReturn(order);

        // when
        orderService.createOrder(request.userId(), request.shippingId(), request.totalPrice(), request.payment());
        order.deliveryDoneOrder();

        // then
        Assertions.assertEquals("DELIVERY_DONE", order.getStatus());
    }

    @Test
    @DisplayName("주문을 취소한다")
    void cancelOrder_test() {
        // given
        Order order = createOrderEntity(request);

        // mocking
        given(orderRepository.save(any())).willReturn(order);

        // when
        orderService.createOrder(request.userId(), request.shippingId(), request.totalPrice(), request.payment());
        order.cancelOrder();

        // then
        Assertions.assertEquals("CANCELED", order.getStatus());
    }
}
