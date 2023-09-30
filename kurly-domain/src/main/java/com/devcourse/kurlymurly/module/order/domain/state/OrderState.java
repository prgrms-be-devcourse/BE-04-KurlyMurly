package com.devcourse.kurlymurly.module.order.domain.state;

public interface OrderState {
    OrderState nextState();
    OrderState cancel();
}
