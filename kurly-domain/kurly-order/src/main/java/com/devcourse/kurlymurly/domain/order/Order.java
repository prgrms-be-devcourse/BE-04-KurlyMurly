package com.devcourse.kurlymurly.domain.order;

import com.devcourse.kurlymurly.common.exception.KurlyBaseException;
import com.devcourse.kurlymurly.data.BaseEntity;
import com.devcourse.kurlymurly.domain.order.state.Canceled;
import com.devcourse.kurlymurly.domain.order.state.Delivered;
import com.devcourse.kurlymurly.domain.order.state.Delivering;
import com.devcourse.kurlymurly.domain.order.state.OrderState;
import com.devcourse.kurlymurly.domain.order.state.Ordered;
import com.devcourse.kurlymurly.domain.order.state.Processing;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static com.devcourse.kurlymurly.common.exception.ErrorCode.INVALID_ORDER_LINE_INDEX;
import static com.devcourse.kurlymurly.common.exception.ErrorCode.NOT_OWNER;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyMMddss");
    private static final Random random = new Random();
    private static final int RANDOM_BOUND = 10000;

    public enum Status { // 주문 완료, 출고 중, 배달 중, 배달 완료, 취소
        ORDERED, PROCESSING, DELIVERING, DELIVERED, CANCELED;

        OrderState generateState() {
            return switch (this) {
                case ORDERED -> new Ordered();
                case PROCESSING -> new Processing();
                case DELIVERING -> new Delivering();
                case DELIVERED -> new Delivered();
                case CANCELED -> new Canceled();
            };
        }
    }

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String orderNumber;

    @ElementCollection
    @CollectionTable(name = "order_lines", joinColumns = @JoinColumn(name = "order_id"))
    @OrderColumn(name = "line_index")
    private List<OrderLine> orderLines = new ArrayList<>();

    @Embedded
    private PaymentInfo paymentInfo;

    @Embedded
    private ShippingInfo shippingInfo;

    @Column
    private LocalDateTime deliveredAt;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Transient
    private OrderState orderState;

    protected Order() {
    }

    Order(Long userId, List<OrderLine> orderLines, PaymentInfo paymentInfo, ShippingInfo shippingInfo,
                 LocalDateTime deliveredAt, Status status, OrderState orderState) {
        this.userId = userId;
        this.orderNumber = generateOrderNumber();
        this.orderLines = orderLines;
        this.paymentInfo = paymentInfo;
        this.shippingInfo = shippingInfo;
        this.deliveredAt = deliveredAt;
        this.status = status;
        this.orderState = orderState;
    }

    public Order(Long userId, List<OrderLine> orderLines, PaymentInfo paymentInfo, ShippingInfo shippingInfo) {
        this(userId, orderLines, paymentInfo, shippingInfo, null, Status.ORDERED, new Ordered());
    }

    public void nextState() {
        if (orderState == null) {
            this.orderState = status.generateState();
        }

        this.orderState = orderState.nextState(this);
    }

    public void cancel() {
        this.orderState = orderState.cancel(this);
    }

    public void updateStatus(Order.Status status) {
        this.status = status;
    }

    public void delivered() {
        updateStatus(Status.DELIVERED);
        this.deliveredAt = LocalDateTime.now();
    }

    private String generateOrderNumber() {
        String currentDate = LocalDateTime.now().format(dateFormat);
        int randomDigits = random.nextInt(RANDOM_BOUND);

        return currentDate + randomDigits;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public ShippingInfo getShippingInfo() {
        return shippingInfo;
    }

    public void reviewOrderLine(int lineIndex) {
        validateOrderLineIndex(lineIndex);
        OrderLine orderLine = orderLines.get(lineIndex);
        orderLine.reviewed();
    }

    private void validateOrderLineIndex(int lineIndex) {
        if (orderLines.size() <= lineIndex) {
            throw new KurlyBaseException(INVALID_ORDER_LINE_INDEX);
        }
    }

    public String summarizeOrderLines() {
        int size = orderLines.size();
        String firstLineName = orderLines.get(0).getProductName();

        return size != 1 ? String.format("%s 외 %d건", firstLineName, size - 1) : firstLineName;
    }

    public void validateOrdersOwner(Long userId) {
        if (!Objects.equals(this.userId, userId)) {
            throw KurlyBaseException.withId(NOT_OWNER, userId);
        }
    }
}
