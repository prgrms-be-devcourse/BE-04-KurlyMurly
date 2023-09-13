package com.devcourse.kurlymurly.web.dto.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public sealed interface UpdatePayPassword permits UpdatePayPassword.Request {
    record Request(
            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "결제 비밀번호 6자리")
            String payPassword
    ) implements UpdatePayPassword {
    }
}
