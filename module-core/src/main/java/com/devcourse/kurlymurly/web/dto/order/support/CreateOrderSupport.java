package com.devcourse.kurlymurly.web.dto.order.support;

import com.devcourse.kurlymurly.module.order.domain.support.OrderSupport;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

import static com.devcourse.kurlymurly.web.dto.order.support.CreateOrderSupport.Request;
import static com.devcourse.kurlymurly.web.dto.order.support.CreateOrderSupport.Response;
import static com.devcourse.kurlymurly.web.dto.order.support.CreateOrderSupport.UpdateRequest;

public sealed interface CreateOrderSupport permits Request, Response, UpdateRequest {
    record Request(
            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "주문 아이디")
            Long orderId,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "주문 고유 번호")
            String orderNumber,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "1대1 문의 타입", allowableValues = {"DELIVERY", "MISSING", "PRODUCT", "ORDER", "EVENT", "ETC"})
            OrderSupport.Type type,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "1대1 문의 제목")
            String title,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "1대1 문의 내용")
            String content
    ) implements CreateOrderSupport {
    }

    record Response(
            OrderSupport.Type type,
            String title,
            String content,
            OrderSupport.Status status,
            LocalDateTime createdAt
    ) implements CreateOrderSupport {
    }

    record UpdateRequest(
            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "수정할 1대1 문의 제목")
            String title,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "수정할 1대1 문의 내용")
            String content
    ) implements CreateOrderSupport {
    }
}
