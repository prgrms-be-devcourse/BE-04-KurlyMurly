package com.devcourse.kurlymurly.web.dto.order;

import com.devcourse.kurlymurly.module.order.domain.OrderSupport;

import static com.devcourse.kurlymurly.web.dto.order.OrderSupportCreate.*;

public sealed interface OrderSupportCreate permits Request, UpdateRequest {
    record Request(Long userId, Long orderId, OrderSupport.Type type, String title, String content) implements OrderSupportCreate {
    }

    record UpdateRequest(String title, String content) implements OrderSupportCreate {
    }
}