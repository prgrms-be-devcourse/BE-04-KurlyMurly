package com.devcourse.kurlymurly.module.order.domain;

import com.devcourse.kurlymurly.core.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.BaseEntity;
import com.devcourse.kurlymurly.module.order.domain.state.OrderState;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static com.devcourse.kurlymurly.core.exception.ErrorCode.NOT_OWNER;

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
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderItem> orderItems = new ArrayList<>();

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

    public Order(Long userId, List<OrderItem> orderItems, PaymentInfo paymentInfo, ShippingInfo shippingInfo) {
        this.userId = userId;
        this.orderNumber = generateOrderNumber();
        this.orderItems = orderItems;
        this.paymentInfo = paymentInfo;
        this.shippingInfo = shippingInfo;
        this.status = Status.ORDERED;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }

    public void toNextState() {
        this.orderState = orderState.nextState();
    }

    public void toCancel() {
        this.status = Status.CANCELED;
        //this.orderState = orderState.cancel();
    }

    private String generateOrderNumber() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String currentDate = localDateTime.format(dateFormat);

        int randomDigits = random.nextInt(RANDOM_BOUND);

        return currentDate + randomDigits;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public Status getStatus() {
        return status;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public int getActualPayAmount() {
        return paymentInfo.getActualPayAmount();
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public ShippingInfo getShippingInfo() {
        return shippingInfo;
    }

    public void markReviewedOrder(Long productId) {
        this.getOrderItems().stream()
                .filter(orderItem -> orderItem.isSameProduct(productId))
                .findFirst()
                .ifPresent(OrderItem::reviewed);
    }

    public String getSimpleProducts() {
        if(orderItems.size() == 1) {
            return orderItems.get(0).getProductName();
        }

        String productName = orderItems.get(0).getProductName() + " 외 " ;
        int size = orderItems.size() - 1;

        return productName + size + "건";
    }

    public void validateOrdersOwner(Long userId) {
        if (!Objects.equals(this.userId, userId)) {
            throw KurlyBaseException.withId(NOT_OWNER, userId);
        }
    }
}
