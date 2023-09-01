package com.devcourse.kurlymurly.web.dto.user.shipping;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public sealed interface AddAddress permits AddAddress.Request {
    record Request(
            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "도로명 주소")
            String roadAddress
    ) implements AddAddress {
    }
}
