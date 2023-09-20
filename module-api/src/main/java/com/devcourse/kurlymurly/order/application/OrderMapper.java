package com.devcourse.kurlymurly.order.application;

import com.devcourse.kurlymurly.module.order.domain.OrderDomain;
import com.devcourse.kurlymurly.module.order.domain.OrderItem;
import com.devcourse.kurlymurly.web.dto.order.CreateOrder;
import com.devcourse.kurlymurly.web.dto.order.CreateOrderItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {
    public OrderDomain toOrderDomain(CreateOrder.Request request) {
        List<OrderItem> orderItems = new ArrayList<>();

        for(CreateOrderItem.Request req : request.orderItems()) {
            orderItems.add(
                    new OrderItem(
                            req.productId(),
                            req.productName(),
                            req.totalPrice(),
                            req.quantity()
                    )
            );
        }

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
