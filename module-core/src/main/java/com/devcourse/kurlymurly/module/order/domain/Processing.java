package com.devcourse.kurlymurly.module.order.domain;

public class Processing implements OrderState {
    @Override
    public OrderState nextState() {
        return new Delivering();
    }

    @Override
    public OrderState cancel() {
        // TODO: 리뷰 작성 제한,, 어떻게,,
        return new Canceled();
    }
}
