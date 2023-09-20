package com.devcourse.kurlymurly.order.application;

import com.devcourse.kurlymurly.module.order.domain.OrderDomain;
import com.devcourse.kurlymurly.module.order.domain.OrderItem;
import com.devcourse.kurlymurly.web.dto.order.CreateOrder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {
    public OrderDomain toOrderDomain(CreateOrder.Request request) {
        List<OrderItem> orderItems = new ArrayList<>();

        for(int i=0; i<request.orderItems().size(); i++) {
            orderItems.add(
                    new OrderItem(
                            request.orderItems().get(i).productId(),
                            request.orderItems().get(i).productName(),
                            request.orderItems().get(i).totalPrice(),
                            request.orderItems().get(i).quantity()
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
