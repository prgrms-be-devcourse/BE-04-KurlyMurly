package com.devcourse.kurlymurly.domain.order.state;

import com.devcourse.kurlymurly.common.exception.KurlyBaseException;
import com.devcourse.kurlymurly.domain.order.Order;

import static com.devcourse.kurlymurly.common.exception.ErrorCode.CANCELED_ORDER;

public class Canceled implements OrderState {
    @Override
    public OrderState nextState(Order order) {
        throw new KurlyBaseException(CANCELED_ORDER);
    }

    @Override
    public OrderState cancel(Order order) {
        throw new KurlyBaseException(CANCELED_ORDER);
    }
}
