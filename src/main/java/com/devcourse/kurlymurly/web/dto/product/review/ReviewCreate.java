package com.devcourse.kurlymurly.web.dto.product.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import static com.devcourse.kurlymurly.web.dto.product.review.ReviewCreate.*;

public sealed interface ReviewCreate permits Request, UpdateRequest, Response {
    record Request(
            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "리뷰 작성자")
            Long userId,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "상품 id")
            Long productId,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "주문 id")
            Long orderId,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "좋아요 수")
            Integer likes,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "리뷰 내용")
            String content
    ) implements ReviewCreate {
    }

    record UpdateRequest(
            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "수정한 리뷰 내용")
            String content
    ) implements ReviewCreate {
    }

    record Response(
            Long userId,
            Long productId,
            Long orderId,
            Integer likes,
            String content
    ) implements ReviewCreate {
    }
}