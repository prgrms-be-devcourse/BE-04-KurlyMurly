package com.devcourse.kurlymurly.web.dto.user.payment;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public sealed interface UpdatePayPassword permits UpdatePayPassword.Request {
    record Request(
            @NotBlank(message = "설정할 결제 비밀번호를 입력해주세요.")
            @Column(length = 6)
            String payPassword
    ) implements UpdatePayPassword {
    }
}
