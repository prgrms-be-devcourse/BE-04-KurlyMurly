package com.devcourse.kurlymurly.web.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static com.devcourse.kurlymurly.web.order.AnswerOrderSupport.*;

public sealed interface AnswerOrderSupport permits Request {
    record Request(
            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "1:1 문의 아이디")
            Long orderSupportId,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "문의 답변 내용")
            String content
    ) implements AnswerOrderSupport {
    }
}
