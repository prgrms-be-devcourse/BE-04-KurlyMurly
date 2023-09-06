package com.devcourse.kurlymurly.web.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

import static com.devcourse.kurlymurly.web.dto.order.CreateOrder.Request;
import static com.devcourse.kurlymurly.web.dto.order.CreateOrder.Response;

public sealed interface CreateOrder permits Request, Response {
    record Request(
            @Valid
            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "주문 상품 정보들")
            List<CreateOrderItem.Request> orderItems,

            @Positive(message = "0보다 작은 값이 들어올 수 없습니다.")
            @Schema(name = "총 주문 금액")
            int totalPrice,

            @Positive(message = "0보다 작은 값이 들어올 수 없습니다.")
            @Schema(name = "총 할인 금액")
            int totalDiscount,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "결제 수단")
            String payment,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "수령인")
            String receiver,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "수령인 연락처")
            String phoneNumber,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "수령인 주소")
            String address,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "수령 장소")
            String receiveArea,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "공동 현관 정보")
            String entranceInfo,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "포장 방법")
            String packaging
    ) implements CreateOrder {
    }

    record Response(String address, String orderNumber, int totalPrice) implements CreateOrder {
    }
}
