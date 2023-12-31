package com.devcourse.kurlymurly.web.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import static com.devcourse.kurlymurly.web.order.AnswerOrderSupport.Request;

public sealed interface AnswerOrderSupport permits Request {
    record Request(
            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(description = "문의 답변 내용")
            String content
    ) implements AnswerOrderSupport {
    }
}
