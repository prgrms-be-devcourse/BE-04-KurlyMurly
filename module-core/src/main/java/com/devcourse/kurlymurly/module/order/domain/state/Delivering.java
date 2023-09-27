package com.devcourse.kurlymurly.module.order.domain.state;

import com.devcourse.kurlymurly.core.exception.KurlyBaseException;

import static com.devcourse.kurlymurly.core.exception.ErrorCode.DELIVERED_ORDER;

public class Delivering implements OrderState {
    @Override
    public OrderState nextState() {
        return new Delivered();
    }

    @Override
    public OrderState cancel() {
        // todo: 리뷰 작성 제한
        throw new KurlyBaseException(DELIVERED_ORDER);
    }
}
