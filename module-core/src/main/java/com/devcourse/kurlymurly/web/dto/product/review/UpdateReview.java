package com.devcourse.kurlymurly.web.dto.product.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static com.devcourse.kurlymurly.web.dto.product.review.UpdateReview.*;

public sealed interface UpdateReview permits Request {
    record Request(
            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "수정한 리뷰 내용")
            String content,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "리뷰 비공개 유무")
            boolean isSecret
    ) implements UpdateReview {
    }
}
