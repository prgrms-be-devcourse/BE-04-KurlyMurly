package com.devcourse.kurlymurly.web.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public sealed interface LoginUser permits LoginUser.Request {
    record Request(
            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "로그인 아이디")
            String loginId,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "로그인 비밀번호")
            String password
    ) implements LoginUser {
    }
}