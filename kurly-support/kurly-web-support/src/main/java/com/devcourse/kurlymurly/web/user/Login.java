package com.devcourse.kurlymurly.web.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import static com.devcourse.kurlymurly.web.user.Login.Request;
import static com.devcourse.kurlymurly.web.user.Login.Response;

public sealed interface Login permits Request, Response {
    record Request(
            @NotBlank(message = "아이디를 입력해주세요.")
            @Schema(description = "로그인 아이디")
            String loginId,

            @NotBlank(message = "비밀번호를 입력해주세요.")
            @Schema(description = "로그인 비밀번호")
            String password
    ) implements Login {
    }

    record Response(
            String token,
            Long expiration
    ) implements Login {
    }
}
