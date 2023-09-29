package com.devcourse.kurlymurly.web.user;

import jakarta.validation.constraints.NotBlank;

import static com.devcourse.kurlymurly.web.user.UpdatePayPassword.Request;

public sealed interface UpdatePayPassword permits Request {
    record Request(
            @NotBlank(message = "설정할 결제 비밀번호를 입력해주세요.")
            String payPassword
    ) implements UpdatePayPassword {
    }
}
