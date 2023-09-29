package com.devcourse.kurlymurly.web.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import static com.devcourse.kurlymurly.web.order.CreateOrderItem.Request;

public sealed interface CreateOrderItem permits Request {
    record Request(
            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(description = "상품 아이디")
            Long productId,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(description = "상품 이름")
            String productName,

            @NotBlank(message = "이미지 경로에 빈 값이 들어올 수 없습니다.")
            @Schema(description = "상품 사진")
            String imageUrl,

            @Positive(message = "0보다 작은 값이 들어올 수 없습니다.")
            @Schema(description = "총 주문 금액")
            int totalPrice,

            @Positive(message = "0보다 작은 값이 들어올 수 없습니다.")
            @Schema(description = "주문 수량")
            int quantity
    ) implements CreateOrderItem {
    }
}
