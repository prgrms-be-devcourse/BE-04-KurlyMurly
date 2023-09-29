package com.devcourse.kurlymurly.web.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

import static com.devcourse.kurlymurly.web.common.UserRegexp.EMAIL_REGEXP;
import static com.devcourse.kurlymurly.web.common.UserRegexp.PASSWORD_REGEXP;
import static com.devcourse.kurlymurly.web.common.UserRegexp.PHONE_NUMBER_REGEXP;
import static com.devcourse.kurlymurly.web.common.UserRegexp.USER_NAME_REGEXP;
import static com.devcourse.kurlymurly.web.user.UpdateUser.Request;

public sealed interface UpdateUser permits Request {
    record Request(
            @NotBlank(message = "현재 비밀번호를 확인해주세요.")
            @Schema(description = "현재 비밀번호")
            String currentPassword,

            @NotBlank(message = "변경할 비밀번호를 입력해주세요.")
            @Pattern(regexp = PASSWORD_REGEXP, message = "비밀번호 형식을 지켜주세요")
            @Schema(description = "변경할 비밀번호")
            String password,

            @NotBlank(message = "비밀번호를 다시 입력해주세요.")
            @Schema(description = "비밀번호 확인")
            String checkPassword,

            @NotBlank(message = "변경할 이름을 입력해주세요.")
            @Pattern(regexp = USER_NAME_REGEXP, message = "이름 형식이 올바르지 않습니다.")
            @Schema(description = "변경할 이름")
            String name,

            @NotBlank(message = "변경할 이메일을 입력해주세요.")
            @Pattern(regexp = EMAIL_REGEXP, message = "이메일 형식이 아닙니다.")
            @Schema(description = "변경할 이메일")
            String email,

            @NotBlank(message = "변경할 휴대폰 번호를 입력해주세요.")
            @Pattern(regexp = PHONE_NUMBER_REGEXP, message = "핸드폰 번호 형식이 아닙니다.")
            @Schema(description = "변경할 휴대폰 번호")
            String phoneNumber,

            @NotBlank(message = "변경할 성별을 입력해주세요.")
            @Schema(description = "변경할 성별", allowableValues = {"male", "female", "none"})
            String sex,

            @NotNull(message = "변경할 생년월일을 입력해주세요.")
            @Schema(description = "변경할 생년월일")
            LocalDate birth
    ) implements UpdateUser {
    }
}
