package com.devcourse.kurlymurly.domain.order.state;

import com.devcourse.kurlymurly.domain.order.Order;

import static com.devcourse.kurlymurly.domain.order.Order.*;

public class Processing implements OrderState {
    @Override
    public OrderState nextState(Order order) {
        order.updateStatus(Status.DELIVERING);
        return new Delivering();
    }

    @Override
    public OrderState cancel(Order order) {
        // TODO: 리뷰 작성 제한,, 어떻게,,
        order.updateStatus(Status.CANCELED);
        return new Canceled();
    }
}
