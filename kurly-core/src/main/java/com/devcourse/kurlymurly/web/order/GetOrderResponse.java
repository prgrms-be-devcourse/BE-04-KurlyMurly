package com.devcourse.kurlymurly.web.order;

import java.time.LocalDateTime;
import java.util.List;

import static com.devcourse.kurlymurly.web.order.GetOrderResponse.DetailInfo;
import static com.devcourse.kurlymurly.web.order.GetOrderResponse.SimpleInfo;

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
            List<ItemInfo> orderItems,

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

    record ItemInfo(
            Long productId,
            String productName,
            String imageUrl,
            String description,
            int totalPrice,
            int quantity,
            boolean isReviewed
    ) {
    }
}
