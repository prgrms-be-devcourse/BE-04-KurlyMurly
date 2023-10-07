package com.devcourse.kurlymurly.domain.order.state;

import com.devcourse.kurlymurly.domain.order.Order;

public interface OrderState {
    OrderState nextState(Order order);
    OrderState cancel(Order order);
}
