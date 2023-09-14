package com.devcourse.kurlymurly.module.order.service;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.order.domain.Order;
import com.devcourse.kurlymurly.module.order.domain.OrderItem;
import com.devcourse.kurlymurly.module.order.domain.OrderRepository;
import com.devcourse.kurlymurly.module.order.domain.PaymentInfo;
import com.devcourse.kurlymurly.module.order.domain.ShippingInfo;
import com.devcourse.kurlymurly.web.dto.order.CreateOrder;
import com.devcourse.kurlymurly.web.dto.order.CreateOrderItem;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.devcourse.kurlymurly.global.exception.ErrorCode.NOT_FOUND_ORDER;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private static final int REVIEWABLE_DEADLINE = 30;

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

    public Order findByIdOrThrow(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new KurlyBaseException(NOT_FOUND_ORDER));
    }

    public List<Order> findAllByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    // todo: 쿼리에서 리뷰가 작성되지 않은 주문 가져오도록 수정
    public List<ReviewResponse.Reviewable> getAllReviewableOrdersByUserId(Long userId) {
        LocalDateTime allowedPeriod = LocalDateTime.now().minusDays(REVIEWABLE_DEADLINE);

        return orderRepository.findAllReviewableOrdersByUserIdWithinThirtyDays(userId, allowedPeriod).stream()
                .flatMap(order -> order.getOrderItems().stream()
                        .filter(OrderItem::isNotReviewed)
                        .map(orderItem -> toReviewableResponse(order, orderItem)))
                .toList();
    }

    private ReviewResponse.Reviewable toReviewableResponse(Order order, OrderItem orderItem) {
        LocalDateTime delivered = order.getUpdatedAt();
        return new ReviewResponse.Reviewable(
                orderItem.getProductId(),
                orderItem.getName(),
                order.getOrderNumber(),
                delivered,
                delivered.plusDays(REVIEWABLE_DEADLINE)
        );
    }

    @Transactional
    public void toProcessing(Long id) {
        Order order = findByIdOrThrow(id);
        order.toProcessing();
    }

    @Transactional
    public void toDelivering(Long id) {
        Order order = findByIdOrThrow(id);
        order.toDelivering();
    }

    @Transactional
    public void toDelivered(Long id) {
        Order order = findByIdOrThrow(id);
        order.toDelivered();
    }

    @Transactional
    public void toCancel(Long id) {
        Order order = findByIdOrThrow(id);
        order.toCancel();
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
