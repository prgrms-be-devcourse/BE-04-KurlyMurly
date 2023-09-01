package com.devcourse.kurlymurly.web.dto.product.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import static com.devcourse.kurlymurly.web.dto.product.review.ReviewLikeCreate.*;

public sealed interface ReviewLikeCreate permits Request, Response {
    record Request(
            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "좋아요 누른 사용자")
            Long likeUserId,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "리뷰 id")
            Long reviewId
    ) implements ReviewLikeCreate {
    }

    record Response(
            Long likeUserId,
            Long reviewId
    ) implements ReviewLikeCreate {
    }
}