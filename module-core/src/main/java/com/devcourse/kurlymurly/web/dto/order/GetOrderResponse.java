package com.devcourse.kurlymurly.web.dto.order;

import com.devcourse.kurlymurly.module.order.domain.OrderItem;

import java.time.LocalDateTime;
import java.util.List;

import static com.devcourse.kurlymurly.web.dto.order.GetOrderResponse.*;

public sealed interface GetOrderResponse permits SimpleInfo, DetailInfo {
    record SimpleInfo(
            String productName,
            String orderNumber,
            String payment,
            int actualPayAmount,
            String status,
            LocalDateTime createdAt
    ) implements GetOrderResponse {
    }

    record DetailInfo(
        Long orderId,
        String orderNumber,
        List<OrderItem> orderItems,

        // paymentInfo
        int totalPrice,
        int deliverFee,
        int totalDiscount,
        int actualPayAmount,
        String payment,

        // shippingInfo
        String receiver,
        String phoneNumber,
        String address,
        String receiveArea,
        String entranceInfo,
        String packaging
    ) implements GetOrderResponse {
    }
}
