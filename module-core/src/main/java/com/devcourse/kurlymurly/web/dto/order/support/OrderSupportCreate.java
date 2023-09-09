package com.devcourse.kurlymurly.web.dto.order.support;

import com.devcourse.kurlymurly.module.order.domain.support.OrderSupport;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static com.devcourse.kurlymurly.web.dto.order.support.OrderSupportCreate.Request;
import static com.devcourse.kurlymurly.web.dto.order.support.OrderSupportCreate.UpdateRequest;

public sealed interface OrderSupportCreate permits Request, UpdateRequest {
    record Request(
            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "주문 아이디")
            Long orderId,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "주문 고유 번호")
            String orderNumber,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "1대1 문의 타입", allowableValues = { "DELIVERY",  "MISSING",  "PRODUCT",  "ORDER",  "EVENT",  "ETC" })
            OrderSupport.Type type,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "1대1 문의 제목")
            String title,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "1대1 문의 내용")
            String content
    ) implements OrderSupportCreate {
    }

    record UpdateRequest(
            String title,
            String content
    ) implements OrderSupportCreate {
    }
}
