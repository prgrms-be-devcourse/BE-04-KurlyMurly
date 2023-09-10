package com.devcourse.kurlymurly.web.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public sealed interface CheckId permits CheckId.Request{
    record Request(
            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "중복 검사할 아이디")
            String loginId
    ) implements CheckId {
    }
}