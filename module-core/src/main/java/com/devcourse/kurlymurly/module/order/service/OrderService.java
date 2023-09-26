package com.devcourse.kurlymurly.module.order.service;

import com.devcourse.kurlymurly.core.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.order.domain.Order;
import com.devcourse.kurlymurly.module.order.domain.OrderItem;
import com.devcourse.kurlymurly.module.order.domain.OrderRepository;
import com.devcourse.kurlymurly.module.order.domain.PaymentInfo;
import com.devcourse.kurlymurly.module.order.domain.ShippingInfo;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.web.dto.order.CreateOrder;
import com.devcourse.kurlymurly.web.dto.order.CreateOrderItem;
import com.devcourse.kurlymurly.web.dto.order.GetOrderResponse;
import com.devcourse.kurlymurly.web.product.ReviewResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.devcourse.kurlymurly.core.exception.ErrorCode.NOT_CORRECT_PAY_PASSWORD;
import static com.devcourse.kurlymurly.core.exception.ErrorCode.ORDER_NOT_FOUND;
import static com.devcourse.kurlymurly.core.exception.ErrorCode.NOT_ORDER_HOST;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private static final int REVIEWABLE_DEADLINE = 30;

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void reviewOrderItem(Long id, Long productId) {
        Order order = findByIdOrThrow(id);
        order.markReviewedOrder(productId);
    }

    @Transactional
    public CreateOrder.Response createOrder(User user, CreateOrder.Request request) {
        // todo: seperate layer
//        if (user.validatePayPassword(request.payPassword(), passwordEncoder)) {
//            Order order = toOrder(user.getId(), request);
//            orderRepository.save(order);
//
//            return new CreateOrder.Response(request.address(), order.getOrderNumber(), order.getActualPayAmount());
//        }
//
        throw new KurlyBaseException(NOT_CORRECT_PAY_PASSWORD);
    }

    private Order toOrder(Long userId, CreateOrder.Request request) {
        List<OrderItem> orderItems = toOrderItems(request.orderItems());
        PaymentInfo paymentInfo = paymentInfo(request);
        ShippingInfo shippingInfo = shippingInfo(request);

        return new Order(userId, orderItems, paymentInfo, shippingInfo);
    }

    public GetOrderResponse.DetailInfo findOrderAndToDetailOrderInfo(Long id) {
        Order order = findByIdOrThrow(id);

        return toDetailInfo(order);
    }

    private GetOrderResponse.DetailInfo toDetailInfo(Order order) {
        PaymentInfo paymentInfo = order.getPaymentInfo();
        ShippingInfo shippingInfo = order.getShippingInfo();

        return new GetOrderResponse.DetailInfo(
                order.getId(),
                order.getOrderNumber(),
                order.getOrderItems(),
                paymentInfo.getTotalPrice(),
                paymentInfo.getDeliveryFee(),
                paymentInfo.getTotalDiscount(),
                paymentInfo.getActualPayAmount(),
                paymentInfo.getPayment(),
                shippingInfo.getReceiver(),
                shippingInfo.getPhoneNumber(),
                shippingInfo.getAddress(),
                shippingInfo.getReceiveArea(),
                shippingInfo.getEntranceInfo(),
                shippingInfo.getPackaging()
        );
    }

    public Order findByIdOrThrow(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new KurlyBaseException(ORDER_NOT_FOUND));
    }

    public List<GetOrderResponse.SimpleInfo> findOrderListSimpleFormByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId).stream()
                .map(this::toSimpleInfo)
                .toList();
    }

    private GetOrderResponse.SimpleInfo toSimpleInfo(Order order) {
        return new GetOrderResponse.SimpleInfo(
                order.getSimpleProducts(),
                order.getOrderNumber(),
                order.getPaymentInfo().getPayment(),
                order.getPaymentInfo().getActualPayAmount(),
                order.getStatus().name(),
                order.getCreateAt()
        );
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
                orderItem.getProductName(),
                order.getOrderNumber(),
                delivered,
                delivered.plusDays(REVIEWABLE_DEADLINE)
        );
    }

    @Transactional
    public void toNextState(Long id) {
        Order order = findByIdOrThrow(id); // todo: entity에서 domain으로 변경
        order.toNextState();
    }

    @Transactional
    public void toCancel(Long id) {
        Order order = findByIdOrThrow(id); // todo: entity에서 domain으로 변경
        order.toCancel();
    }

    @Transactional
    public void toCancelByUser(Long id, Long userId) {
        Order order = orderRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new KurlyBaseException(NOT_ORDER_HOST));

        order.validateOrdersOwner(userId);
        order.toCancel();
    }

    private List<OrderItem> toOrderItems(List<CreateOrderItem.Request> requests) {
        return requests.stream()
                .map(this::toOrderItem)
                .toList();
    }

    private OrderItem toOrderItem(CreateOrderItem.Request request) {
        return new OrderItem(request.productId(), request.productName(), request.totalPrice(), request.quantity());
    }

    private PaymentInfo paymentInfo(CreateOrder.Request request) {
        return new PaymentInfo(request.totalPrice(), request.totalDiscount(), request.payment());
    }

    private ShippingInfo shippingInfo(CreateOrder.Request request) {
        return new ShippingInfo(request.receiver(), request.phoneNumber(), request.address(), request.receiveArea(), request.entranceInfo(), request.packaging());
    }
}
