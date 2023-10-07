package com.devcourse.kurlymurly.domain.order.state;

import com.devcourse.kurlymurly.domain.order.Order;

import static com.devcourse.kurlymurly.domain.order.Order.*;

public class Ordered implements OrderState {
    @Override
    public OrderState nextState(Order order) {
        order.updateStatus(Status.PROCESSING);
        return new Processing();
    }

    @Override
    public OrderState cancel(Order order) {
        order.updateStatus(Status.CANCELED);
        return new Canceled();
    }
}
