package com.devcourse.kurlymurly.web.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

import static com.devcourse.kurlymurly.web.order.CreateOrder.Request;
import static com.devcourse.kurlymurly.web.order.CreateOrder.Response;

public sealed interface CreateOrder permits Request, Response {
    record Request(
            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(description = "주문 상품 정보들")
            @Valid List<CreateOrderItem.Request> orderItems,

            @Positive(message = "0보다 작은 값이 들어올 수 없습니다.")
            @Schema(description = "총 주문 금액")
            int totalPrice,

            @Positive(message = "0보다 작은 값이 들어올 수 없습니다.")
            @Schema(description = "총 할인 금액")
            int totalDiscount,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(description = "결제 수단")
            String payment,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(description = "결제비밀번호")
            String payPassword,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(description = "수령인")
            String receiver,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(description = "수령인 연락처")
            String phoneNumber,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(description = "수령인 주소")
            String address,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(description = "수령 장소")
            String receiveArea,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(description = "공동 현관 정보")
            String entranceInfo,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(description = "포장 방법")
            String packaging
    ) implements CreateOrder {
    }

    record Response(
            String address,
            String orderNumber,
            int totalPrice
    ) implements CreateOrder {
    }
}
