package com.devcourse.kurlymurly.domain.order.state;

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