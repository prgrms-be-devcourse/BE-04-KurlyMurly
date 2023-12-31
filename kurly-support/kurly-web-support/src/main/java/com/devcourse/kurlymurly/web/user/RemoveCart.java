package com.devcourse.kurlymurly.web.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import static com.devcourse.kurlymurly.web.user.RemoveCart.Request;

public sealed interface RemoveCart permits Request {
    record Request(
            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(description = "장바구니 아이디 리스트")
            @Valid List<Long> cartIds
    ) implements RemoveCart {
    }
}
