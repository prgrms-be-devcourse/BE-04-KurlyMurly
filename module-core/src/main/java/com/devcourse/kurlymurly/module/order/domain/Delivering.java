package com.devcourse.kurlymurly.module.order.domain;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;

import static com.devcourse.kurlymurly.global.exception.ErrorCode.DELIVERED_ORDER;

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
