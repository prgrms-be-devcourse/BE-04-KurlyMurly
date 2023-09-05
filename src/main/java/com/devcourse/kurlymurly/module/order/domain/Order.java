package com.devcourse.kurlymurly.module.order.domain;

import com.devcourse.kurlymurly.module.BaseEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
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
    @CollectionTable(joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderItem> orderItems = new ArrayList<>();

    @Embedded
    private PaymentInfo paymentInfo;

    @Embedded
    private ShippingInfo shippingInfo;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Status status;

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

    public void processingOrder() {
        this.status = Status.PROCESSING;
    }

    public void deliveringOrder() {
        this.status = Status.DELIVERING;
    }

    public void deliveryDoneOrder() {
        this.status = Status.DELIVERED;
    }

    public void cancelOrder() {
        this.status = Status.CANCELED;
    }

    private String generateOrderNumber() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String currentDate = localDateTime.format(DateTimeFormatter.ofPattern("yyMMddss"));

        int randomDigits = new Random().nextInt(RANDOM_BOUND);

        return currentDate + randomDigits;
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
}
