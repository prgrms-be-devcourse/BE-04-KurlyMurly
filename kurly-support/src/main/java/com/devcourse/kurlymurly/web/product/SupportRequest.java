package com.devcourse.kurlymurly.web.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static com.devcourse.kurlymurly.web.product.SupportRequest.Create;
import static com.devcourse.kurlymurly.web.product.SupportRequest.Update;

public sealed interface SupportRequest permits Create, Update {
    String title();
    String content();
    boolean isSecret();

    record Create(
            @NotBlank(message = "제목에는 빈 값이 들어올 수 없습니다.")
            @Schema(description = "문의 제목")
            String title,

            @NotBlank(message = "내용에는 빈 값이 들어올 수 없습니다.")
            @Schema(description = "문의 내용")
            String content,

            @NotNull(message = "비밀 여부에 빈 값이 들어올 수 없습니다.")
            @Schema(description = "비밀글 여부")
            boolean isSecret
    ) implements SupportRequest {
    }

    record Update(
            @NotBlank(message = "제목에는 빈 값이 들어올 수 없습니다.")
            @Schema(description = "문의 제목")
            String title,

            @NotBlank(message = "내용에는 빈 값이 들어올 수 없습니다.")
            @Schema(description = "문의 내용")
            String content,

            @NotNull(message = "비밀 여부에 빈 값이 들어올 수 없습니다.")
            @Schema(description = "비밀글 여부")
            boolean isSecret
    ) implements SupportRequest {
    }
}
