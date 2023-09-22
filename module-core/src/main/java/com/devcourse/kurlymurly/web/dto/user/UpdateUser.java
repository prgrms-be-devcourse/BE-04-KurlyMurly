package com.devcourse.kurlymurly.web.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public sealed interface UpdateUser permits UpdateUser.Request {
    record Request(
            @NotNull(message = "현재 비밀번호를 확인해주세요")
            @Schema(name = "현재 비밀번호")
            String currentPassword,

            @Length(min = 10)
            @Schema(name = "변경할 비밀번호")
            String password,

            @Schema(name = "비밀번호 확인")
            String checkPassword,

            @Pattern(regexp = "^[가-힣a-z]{2,20}")
            @Schema(name = "변경할 이름")
            String name,

            @Pattern(regexp = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b")
            @Schema(name = "변경할 이메일")
            String email,

            @Schema(name = "변경할 휴대폰 번호")
            String phoneNumber,

            @Schema(name = "변경할 성별")
            String sex,

            @Schema(name = "변경할 생년월일")
            LocalDate birth
    ) implements UpdateUser {
    }
}
