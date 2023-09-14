package com.devcourse.kurlymurly.web.dto.product.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static com.devcourse.kurlymurly.web.dto.product.review.CreateReview.Request;

public sealed interface CreateReview permits Request {
    record Request(
            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "주문 ID")
            Long orderId,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "상품 id")
            Long productId,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "상품 이름")
            String productName,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "리뷰 내용")
            String content,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "리뷰 비공개 유무")
            boolean isSecret
    ) implements CreateReview {
    }
}
