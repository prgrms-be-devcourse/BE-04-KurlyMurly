package com.devcourse.kurlymurly.web.dto.product.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import static com.devcourse.kurlymurly.web.dto.product.review.CreateReview.Request;
import static com.devcourse.kurlymurly.web.dto.product.review.CreateReview.UpdateRequest;

public sealed interface CreateReview permits Request, UpdateRequest {
    record Request(
            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "리뷰 작성자")
            Long userId,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "상품 id")
            Long productId,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "리뷰 내용")
            String content
    ) implements CreateReview {
    }

    record UpdateRequest(
            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "수정한 리뷰 내용")
            String content,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "리뷰 비공개 유무")
            boolean isSecreted
    ) implements CreateReview {
    }
}
