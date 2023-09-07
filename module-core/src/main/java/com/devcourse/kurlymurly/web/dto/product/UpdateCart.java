package com.devcourse.kurlymurly.web.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import static com.devcourse.kurlymurly.web.dto.product.UpdateCart.Request;

public sealed interface UpdateCart permits Request {
    record Request(
            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "장바구니 아이디")
            Long cartId,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "갯수 증가 유무")
            Boolean isIncrease
    ) implements UpdateCart {
    }
}
