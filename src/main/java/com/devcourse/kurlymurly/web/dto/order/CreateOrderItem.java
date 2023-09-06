package com.devcourse.kurlymurly.web.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import static com.devcourse.kurlymurly.web.dto.order.CreateOrderItem.Request;

public sealed interface CreateOrderItem permits Request {
    record Request(
            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "상품 아이디")
            Long productId,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "상품 이름")
            String name,

            @Positive(message = "0보다 작은 값이 들어올 수 없습니다.")
            @Schema(name = "총 주문 금액")
            int totalPrice,

            @Positive(message = "0보다 작은 값이 들어올 수 없습니다.")
            @Schema(name = "주문 수량")
            int quantity
    ) implements CreateOrderItem {
    }
}
