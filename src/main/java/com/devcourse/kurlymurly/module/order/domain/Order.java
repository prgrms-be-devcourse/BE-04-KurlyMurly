package com.devcourse.kurlymurly.module.order.domain;

import com.devcourse.kurlymurly.module.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    public enum Status {
        PROCESSING,
        DELIVERY_ING,
        DELIVERY_DONE,
        CANCELED
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

    public Order(Long userId, Long shippingId, int totalPrice, String payment) {
        this.userId = userId;
        this.shippingId = shippingId;
        this.orderNumber = generateOrderNumber();
        this.deliveryFee = checkDeliveryFee();
        this.totalPrice = totalPrice;
        this.payment = payment;
        this.status = Status.PROCESSING;
    }

    public void processingOrder() {
        this.status = Status.PROCESSING;
    }

    public void deliveringOrder() {
        this.status = Status.DELIVERY_ING;
    }

    public void deliveryDoneOrder() {
        this.status = Status.DELIVERY_DONE;
    }

    public void cancelOrder() {
        this.status = Status.CANCELED;
    }

    private int checkDeliveryFee() {
        if(totalPrice > 40000) {
            return 0;
        }

        return 4000;
    }

    private String generateOrderNumber() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String currentDate = localDateTime.format(DateTimeFormatter.ofPattern("yyMMddss"));

        int randomDigits = new Random().nextInt(10000);

        return currentDate + randomDigits;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status.toString();
    }
}
