package com.devcourse.kurlymurly.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static com.devcourse.kurlymurly.web.dto.SupportProduct.Request;

public sealed interface SupportProduct permits Request {
    record Request(
            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "문의 제목")
            String title,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "문의 내용")
            String content,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "비밀글 여부")
            boolean isSecret
    ) implements SupportProduct {
    }
}
