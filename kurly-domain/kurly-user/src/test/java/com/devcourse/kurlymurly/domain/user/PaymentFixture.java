package com.devcourse.kurlymurly.module.user;

import com.devcourse.kurlymurly.domain.user.payment.Payment;

public enum PaymentFixture {
    PAYMENT_FIXTURE(1L, null);

    private final Long userId;
    private final String payInfo;

    PaymentFixture(Long userId, String payInfo) {
        this.userId = userId;
        this.payInfo = payInfo;
    }

    public Payment toEntity() {
        return new Payment(userId, payInfo);
    }
}
