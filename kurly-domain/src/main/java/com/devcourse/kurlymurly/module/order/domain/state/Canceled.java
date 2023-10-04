package com.devcourse.kurlymurly.module.order.domain.state;

import com.devcourse.kurlymurly.core.exception.KurlyBaseException;

import static com.devcourse.kurlymurly.core.exception.ErrorCode.CANCELED_ORDER;

public class Canceled implements OrderState {
    @Override
    public OrderState nextState() {
        throw new KurlyBaseException(CANCELED_ORDER);
    }

    @Override
    public OrderState cancel() {
        throw new KurlyBaseException(CANCELED_ORDER);
    }
}