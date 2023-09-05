package com.devcourse.kurlymurly.web.dto.product.support;

import com.devcourse.kurlymurly.module.product.domain.support.ProductSupport;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

import static com.devcourse.kurlymurly.web.dto.product.support.SupportProduct.Request;
import static com.devcourse.kurlymurly.web.dto.product.support.SupportProduct.Response;

public sealed interface SupportProduct permits Request, Response {
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

    record Response(
            Long id,
            Long productId,
            String productName,
            String title,
            String content,
            boolean isSecret,
            ProductSupport.Status status,
            LocalDateTime createdAt
    ) implements SupportProduct {
    }
}
