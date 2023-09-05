package com.devcourse.kurlymurly.web.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import static com.devcourse.kurlymurly.web.dto.product.RemoveCart.*;

public sealed interface RemoveCart permits Request {
    record Request(
            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "상품 아이디 리스트")
            List<Long> products
    ) implements RemoveCart {
    }
}
