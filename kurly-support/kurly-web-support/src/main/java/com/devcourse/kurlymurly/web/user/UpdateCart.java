package com.devcourse.kurlymurly.web.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import static com.devcourse.kurlymurly.web.user.UpdateCart.Request;

public sealed interface UpdateCart permits Request {
    record Request(
            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(description = "장바구니 아이디")
            Long cartId,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(description = "갯수 증가 유무")
            Boolean isIncrease
    ) implements UpdateCart {
    }
}
