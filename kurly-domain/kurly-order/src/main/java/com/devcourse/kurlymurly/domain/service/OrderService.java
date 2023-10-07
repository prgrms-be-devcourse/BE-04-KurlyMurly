package com.devcourse.kurlymurly.domain.service;

import com.devcourse.kurlymurly.common.exception.KurlyBaseException;
import com.devcourse.kurlymurly.domain.order.Order;
import com.devcourse.kurlymurly.domain.order.OrderLine;
import com.devcourse.kurlymurly.domain.order.OrderRepository;
import com.devcourse.kurlymurly.domain.order.PaymentInfo;
import com.devcourse.kurlymurly.domain.order.ShippingInfo;
import com.devcourse.kurlymurly.domain.user.User;
import com.devcourse.kurlymurly.web.order.CreateOrder;
import com.devcourse.kurlymurly.web.order.CreateOrderLine;
import com.devcourse.kurlymurly.web.order.GetOrderResponse;
import com.devcourse.kurlymurly.web.product.ReviewResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.devcourse.kurlymurly.common.exception.ErrorCode.NOT_CORRECT_PAY_PASSWORD;
import static com.devcourse.kurlymurly.common.exception.ErrorCode.NOT_ORDER_HOST;
import static com.devcourse.kurlymurly.common.exception.ErrorCode.ORDER_NOT_FOUND;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void reviewOrderLine(Long id, int lineIndex) {
        Order order = findByIdOrThrow(id);
        order.reviewOrderLine(lineIndex);
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
        List<OrderLine> orderItems = toOrderItems(request.orderItems());
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
                new ArrayList<>(),
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

    // todo: 삭제된 것 제외하고 가져오기
    public List<GetOrderResponse.SimpleInfo> findOrderListSimpleFormByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId).stream()
                .map(this::toSimpleInfo)
                .toList();
    }

    private GetOrderResponse.SimpleInfo toSimpleInfo(Order order) {
        return new GetOrderResponse.SimpleInfo(
                order.summarizeOrderLines(),
                order.getOrderNumber(),
                order.getPaymentInfo().getPayment(),
                order.getPaymentInfo().getActualPayAmount(),
                order.getStatus().name(),
                order.getCreateAt()
        );
    }

    public List<ReviewResponse.Reviewable> getAllReviewableOrdersByUserId(Long userId) {
        return orderRepository.findReviewableOrdersByUserIdWithinThirtyDays(userId);
    }

    @Transactional
    public void toNextState(Long id) {
        Order order = findByIdOrThrow(id); // todo: entity에서 domain으로 변경
        order.nextState();
    }

    @Transactional
    public void toCancel(Long id) {
        Order order = findByIdOrThrow(id); // todo: entity에서 domain으로 변경
        order.cancel();
    }

    @Transactional
    public void toCancelByUser(Long id, Long userId) {
        Order order = orderRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new KurlyBaseException(NOT_ORDER_HOST));

        order.validateOrdersOwner(userId);
        order.cancel();
    }

    private List<OrderLine> toOrderItems(List<CreateOrderLine.Request> requests) {
        return requests.stream()
                .map(this::toOrderItem)
                .toList();
    }

    private OrderLine toOrderItem(CreateOrderLine.Request request) {
        return new OrderLine(request.productId(), request.productName(), request.imageUrl(), request.totalPrice(), request.quantity());
    }

    // todo: 의존성 제거
    private PaymentInfo paymentInfo(CreateOrder.Request request) {
        return new PaymentInfo(request.totalPrice(), request.totalDiscount(), request.payment());
    }

    // todo: 의존성 제거
    private ShippingInfo shippingInfo(CreateOrder.Request request) {
        return new ShippingInfo(request.receiver(), request.phoneNumber(), request.address(), request.receiveArea(), request.entranceInfo(), request.packaging());
    }
}
