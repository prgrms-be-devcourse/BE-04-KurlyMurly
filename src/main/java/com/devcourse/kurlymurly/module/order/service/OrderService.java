package com.devcourse.kurlymurly.module.order.service;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.order.domain.Order;
import com.devcourse.kurlymurly.module.order.domain.OrderItem;
import com.devcourse.kurlymurly.module.order.domain.OrderRepository;
import com.devcourse.kurlymurly.module.order.domain.PaymentInfo;
import com.devcourse.kurlymurly.module.order.domain.ShippingInfo;
import com.devcourse.kurlymurly.web.dto.order.CreateOrderItem;
import com.devcourse.kurlymurly.web.dto.order.CreateOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.devcourse.kurlymurly.global.exception.ErrorCode.NOT_FOUND_ORDER;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public CreateOrder.Response createOrder(Long userId, CreateOrder.Request request) {
        Order order = toOrder(userId, request);
        orderRepository.save(order);

        return new CreateOrder.Response(request.address(), order.getOrderNumber(), order.getActualPayAmount());
    }

    private Order toOrder(Long userId, CreateOrder.Request request) {
        List<OrderItem> orderItems = toOrderItems(request.orderItems());
        PaymentInfo paymentInfo = paymentInfo(request);
        ShippingInfo shippingInfo = shippingInfo(request);

        return new Order(userId, orderItems, paymentInfo, shippingInfo);
    }

    public Page<Order> findOrderAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new KurlyBaseException(NOT_FOUND_ORDER));
    }

    public List<Order> findAllByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    // 관리자 영역 (ADMIN 권한 필요)
    @Transactional
    public Order updateOrderToProcessing(Long id) {
        Order order = findById(id);
        order.processingOrder();

        return order;
    }

    @Transactional
    public Order updateOrderToDelivering(Long id) {
        Order order = findById(id);
        order.deliveringOrder();

        return order;
    }

    @Transactional
    public Order updateOrderToDeliveryDone(Long id) {
        Order order = findById(id);
        order.deliveryDoneOrder();

        return order;
    }

    @Transactional
    public void cancelOrder(Long id) {
        Order order = findById(id);
        order.cancelOrder();
    }

    private List<OrderItem> toOrderItems(List<CreateOrderItem.Request> requests) {
        return requests.stream()
                .map(this::toOrderItem)
                .toList();
    }

    private OrderItem toOrderItem(CreateOrderItem.Request request) {
        return new OrderItem(request.productId(), request.name(), request.totalPrice(), request.quantity());
    }

    private PaymentInfo paymentInfo(CreateOrder.Request request) {
        return new PaymentInfo(request.totalPrice(), request.totalDiscount(), request.payment());
    }

    private ShippingInfo shippingInfo(CreateOrder.Request request) {
        return new ShippingInfo(request.receiver(), request.phoneNumber(), request.address(), request.receiveArea(), request.entranceInfo(), request.packaging());
    }
}
