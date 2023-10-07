package com.devcourse.kurlymurly.domain.order.state;

import com.devcourse.kurlymurly.common.exception.KurlyBaseException;
import com.devcourse.kurlymurly.domain.order.Order;

import static com.devcourse.kurlymurly.common.exception.ErrorCode.DELIVERED_ORDER;

public class Delivered implements OrderState {
    @Override
    public OrderState nextState(Order order) {
        // todo: 적립금 지급, 리뷰 작성 권한 부여
        throw new KurlyBaseException(DELIVERED_ORDER);
    }

    @Override
    public OrderState cancel(Order order) {
        throw new KurlyBaseException(DELIVERED_ORDER);
    }
}
