package com.devcourse.kurlymurly.module.order.domain.state;

import com.devcourse.kurlymurly.common.exception.KurlyBaseException;

import static com.devcourse.kurlymurly.common.exception.ErrorCode.DELIVERED_ORDER;

public class Delivered implements OrderState {
    @Override
    public OrderState nextState() {
        // todo: 적립금 지급, 리뷰 작성 권한 부여
        throw new KurlyBaseException(DELIVERED_ORDER);
    }

    @Override
    public OrderState cancel() {
        throw new KurlyBaseException(DELIVERED_ORDER);
    }
}
