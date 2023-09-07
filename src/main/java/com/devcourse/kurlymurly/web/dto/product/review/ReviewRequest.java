package com.devcourse.kurlymurly.web.dto.product.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import static com.devcourse.kurlymurly.web.dto.product.review.ReviewRequest.*;

public sealed interface ReviewRequest permits OfProduct {
    record OfProduct(
            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "slice 페이지 시작점")
            Long start
    ) implements ReviewRequest {
    }
}
