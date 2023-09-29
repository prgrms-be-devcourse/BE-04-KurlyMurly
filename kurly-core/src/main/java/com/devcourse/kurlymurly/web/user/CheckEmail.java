package com.devcourse.kurlymurly.web.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import static com.devcourse.kurlymurly.web.user.CheckEmail.Request;

public sealed interface CheckEmail permits Request {
    record Request(
            @NotBlank(message = "검사할 이메일을 입력해주세요.")
            @Schema(name = "중복 검사할 이메일")
            String email
    ) implements CheckEmail {
    }
}
