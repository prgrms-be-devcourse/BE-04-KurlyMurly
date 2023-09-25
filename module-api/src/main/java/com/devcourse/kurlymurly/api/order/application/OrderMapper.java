package com.devcourse.kurlymurly.api.order.application;

import com.devcourse.kurlymurly.module.order.domain.OrderDomain;
import com.devcourse.kurlymurly.module.order.domain.OrderItem;
import com.devcourse.kurlymurly.web.dto.order.CreateOrder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {
    public OrderDomain toOrderDomain(CreateOrder.Request request) {
        List<OrderItem> orderItems = request.orderItems().stream()
                .map((req) -> new OrderItem(
                        req.productId(),
                        req.productName(),
                        req.totalPrice(),
                        req.quantity())
                )
                .toList();

        return new OrderDomain(
                orderItems,
                request.totalPrice(),
                request.totalDiscount(),
                request.payment(),
                request.receiver(),
                request.phoneNumber(),
                request.address(),
                request.receiveArea(),
                request.entranceInfo(),
                request.packaging()
        );
    }
}
