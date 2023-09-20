package com.devcourse.kurlymurly.module.order.domain;

public interface OrderState {
    OrderState nextState();
    OrderState cancel();
}
