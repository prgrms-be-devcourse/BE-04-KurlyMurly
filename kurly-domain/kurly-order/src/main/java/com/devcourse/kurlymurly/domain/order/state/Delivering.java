package com.devcourse.kurlymurly.domain.order.state;

import com.devcourse.kurlymurly.common.exception.KurlyBaseException;
import com.devcourse.kurlymurly.domain.order.Order;

import static com.devcourse.kurlymurly.common.exception.ErrorCode.DELIVERED_ORDER;

public class Delivering implements OrderState {
    @Override
    public OrderState nextState(Order order) {
        order.delivered();
        return new Delivered();
    }

    @Override
    public OrderState cancel(Order order) {
        // todo: 리뷰 작성 제한
        throw new KurlyBaseException(DELIVERED_ORDER);
    }
}
