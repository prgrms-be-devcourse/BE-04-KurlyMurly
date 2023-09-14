package com.devcourse.kurlymurly.web.dto.order.support;

import com.devcourse.kurlymurly.module.order.domain.support.OrderSupport;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static com.devcourse.kurlymurly.web.dto.order.support.CreateOrderSupport.*;
import static com.devcourse.kurlymurly.web.dto.order.support.CreateOrderSupport.Request;
import static com.devcourse.kurlymurly.web.dto.order.support.CreateOrderSupport.UpdateRequest;

public sealed interface CreateOrderSupport permits AnswerRequest, Request, UpdateRequest {
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
    ) implements CreateOrderSupport {
    }

    record UpdateRequest(
            String title,
            String content
    ) implements CreateOrderSupport {
    }

    record AnswerRequest(
            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "1:1 문의 아이디")
            Long orderSupportId,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "문의 답변 내용")
            String content
    ) implements CreateOrderSupport {
    }
}
