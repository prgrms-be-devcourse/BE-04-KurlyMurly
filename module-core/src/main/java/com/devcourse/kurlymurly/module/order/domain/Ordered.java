package com.devcourse.kurlymurly.module.order.domain;

public class Ordered implements OrderState {
    @Override
    public OrderState nextState() {
        return new Processing();
    }

    @Override
    public OrderState cancel() {
        return new Canceled();
    }
}
