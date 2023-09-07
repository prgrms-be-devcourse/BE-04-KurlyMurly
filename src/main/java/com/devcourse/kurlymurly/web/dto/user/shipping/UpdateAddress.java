package com.devcourse.kurlymurly.web.dto.user.shipping;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public sealed interface UpdateAddress permits UpdateAddress.Request {
    record Request(
            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "배송 주소 id값")
            Long addressId,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "상세 주소")
            String description,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "수취인")
            String receiver,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "연락처")
            String contact
    ) implements UpdateAddress {
    }
}
