package com.devcourse.kurlymurly.domain.order.state;

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
