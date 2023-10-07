package com.devcourse.kurlymurly.domain.order;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PaymentInfo {
    private static final int FREE_DELIVERY_STANDARD = 40000;
    private static final int BASIC_DELIVERY_FEE = 4000;
    private static final int DELIVERY_FREE = 0;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private int deliveryFee;

    @Column(nullable = false)
    private int totalDiscount;

    @Column(nullable = false)
    private int actualPayAmount;

    // 적립금 (Reward Point)

    @Column(nullable = false, length = 10)
    private String payment;

    protected PaymentInfo() {
    }

    public PaymentInfo(int totalPrice, int totalDiscount, String payment) {
        this.totalPrice = totalPrice;
        this.deliveryFee = calculateDeliveryFee(totalPrice);
        this.totalDiscount = totalDiscount;
        this.actualPayAmount = totalPrice - totalDiscount;
        this.payment = payment;
    }

    private int calculateDeliveryFee(int totalPrice) {
        return totalPrice > FREE_DELIVERY_STANDARD ? DELIVERY_FREE : BASIC_DELIVERY_FEE;
    }

    public int getActualPayAmount() {
        return actualPayAmount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getDeliveryFee() {
        return deliveryFee;
    }

    public int getTotalDiscount() {
        return totalDiscount;
    }

    public String getPayment() {
        return payment;
    }
}
