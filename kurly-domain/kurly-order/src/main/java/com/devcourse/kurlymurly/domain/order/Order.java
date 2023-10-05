package com.devcourse.kurlymurly.domain.order;

import com.devcourse.kurlymurly.common.exception.KurlyBaseException;
import com.devcourse.kurlymurly.data.BaseEntity;
import com.devcourse.kurlymurly.domain.order.state.OrderState;
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

    public enum Status {
        ORDERED,
        PROCESSING,
        DELIVERING,
        DELIVERED,
        CANCELED
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

    public Order(Long userId, List<OrderLine> orderLines, PaymentInfo paymentInfo, ShippingInfo shippingInfo) {
        this.userId = userId;
        this.orderNumber = generateOrderNumber();
        this.orderLines = orderLines;
        this.paymentInfo = paymentInfo;
        this.shippingInfo = shippingInfo;
        this.status = Status.ORDERED;
    }

    public void toNextState() {
        this.orderState = orderState.nextState();
    }

    public void cancel() {
        this.status = Status.CANCELED;
        //this.orderState = orderState.cancel();
    }

    private String generateOrderNumber() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String currentDate = localDateTime.format(dateFormat);

        int randomDigits = random.nextInt(RANDOM_BOUND);

        return currentDate + randomDigits;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public Status getStatus() {
        return status;
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

    public String getSimpleProducts() {
        if(orderLines.size() == 1) {
            return orderLines.get(0).getProductName();
        }

        String productName = orderLines.get(0).getProductName() + " 외 " ;
        int size = orderLines.size() - 1;

        return productName + size + "건";
    }

    public void validateOrdersOwner(Long userId) {
        if (!Objects.equals(this.userId, userId)) {
            throw KurlyBaseException.withId(NOT_OWNER, userId);
        }
    }
}
