package com.devcourse.kurlymurly.web.dto.order;

import static com.devcourse.kurlymurly.web.dto.order.OrderCreate.Request;
import static com.devcourse.kurlymurly.web.dto.order.OrderCreate.Response;

public sealed interface OrderCreate permits Request, Response {
    record Request(Long userId, Long shippingId, int totalPrice, String payment) implements OrderCreate {
    }

    record Response() implements OrderCreate {
    }
}
