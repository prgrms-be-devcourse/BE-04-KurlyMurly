package com.devcourse.kurlymurly.web.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import static com.devcourse.kurlymurly.web.user.CreateCart.Request;

public sealed interface CreateCart permits Request {
    record Request(
            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(description = "상품 아이디")
            Long productId,

            @Positive(message = "0보다 작은 값이 들어올 수 없습니다.")
            @Schema(description = "상품 개수")
            int quantity
    ) implements CreateCart {
    }
}
