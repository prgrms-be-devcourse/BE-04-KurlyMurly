package com.devcourse.kurlymurly.module.order.domain;

import com.devcourse.kurlymurly.module.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    public enum Status {
        PROCESSING,
        DELIVERING,
        DELIVERED
    }

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long shippingId;

    @Column(nullable = false)
    private String orderNumber;

    @Column(nullable = false)
    private int deliveryFee;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false, length = 10)
    private String payment;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status status;

    protected Order() {
    }

    public Order(Long userId, Long shippingId, String orderNumber, int deliveryFee, int totalPrice, String payment) {
        this.userId = userId;
        this.shippingId = shippingId;
        this.orderNumber = orderNumber;
        this.deliveryFee = deliveryFee;
        this.totalPrice = totalPrice;
        this.payment = payment;
        this.status = Status.PROCESSING;
    }
}
