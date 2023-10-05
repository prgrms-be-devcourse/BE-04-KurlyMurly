package com.devcourse.kurlymurly.domain.order.state;

public interface OrderState {
    OrderState nextState();
    OrderState cancel();
}
