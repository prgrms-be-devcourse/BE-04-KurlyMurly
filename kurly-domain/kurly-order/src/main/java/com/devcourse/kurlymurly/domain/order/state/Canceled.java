package com.devcourse.kurlymurly.domain.order.state;

import com.devcourse.kurlymurly.common.exception.KurlyBaseException;

import static com.devcourse.kurlymurly.common.exception.ErrorCode.CANCELED_ORDER;

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
