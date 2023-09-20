package com.devcourse.kurlymurly.module.order.domain;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;

import static com.devcourse.kurlymurly.global.exception.ErrorCode.CANCELED_ORDER;

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
