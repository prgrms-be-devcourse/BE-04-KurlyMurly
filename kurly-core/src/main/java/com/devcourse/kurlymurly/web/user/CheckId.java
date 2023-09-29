package com.devcourse.kurlymurly.web.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import static com.devcourse.kurlymurly.web.user.CheckId.Request;

public sealed interface CheckId permits Request {
    record Request(
            @NotBlank(message = "검사할 아이디를 입력해주세요.")
            @Schema(name = "중복 검사할 아이디")
            String loginId
    ) implements CheckId {
    }
}
