package com.devcourse.kurlymurly.web.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

import static com.devcourse.kurlymurly.web.common.UserRegexp.EMAIL_REGEXP;
import static com.devcourse.kurlymurly.web.common.UserRegexp.PASSWORD_REGEXP;
import static com.devcourse.kurlymurly.web.common.UserRegexp.PHONE_NUMBER_REGEXP;
import static com.devcourse.kurlymurly.web.common.UserRegexp.USER_NAME_REGEXP;
import static com.devcourse.kurlymurly.web.user.Join.Request;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

public sealed interface Join permits Request {
    record Request(
            @NotBlank(message = "사용할 아이디를 입력해주세요.")
            @Schema(description = "사용할 아이디")
            String loginId,

            @NotBlank(message = "사용할 비밀번호를 입력해주세요.")
            @Pattern(regexp = PASSWORD_REGEXP, message = "비밀번호 형식을 지켜주세요")
            @Schema(description = "사용할 비밀번호")
            String password,

            @NotBlank(message = "비밀번호를 다시 한번 입력해주세요.")
            @Schema(description = "비밀번호 재확인")
            String checkPassword,

            @NotBlank(message = "이름을 입력해주세요.")
            @Pattern(regexp = USER_NAME_REGEXP, message = "이름 형식이 올바르지 않습니다.")
            @Schema(description = "고객 이름")
            String name,

            @NotBlank(message = "이메일을 입력해주세요.")
            @Pattern(regexp = EMAIL_REGEXP, message = "이메일 형식이 아닙니다.")
            @Schema(description = "고객 이메일")
            String email,

            @NotBlank(message = "연락처를 입력해주세요.")
            @Pattern(regexp = PHONE_NUMBER_REGEXP, message = "핸드폰 번호 형식이 아닙니다.")
            @Schema(description = "고객 연락처")
            String phoneNumber,

            @NotBlank(message = "성별을 입력해주세요.")
            @Schema(description = "고객 성별", allowableValues = {"male", "female", "none"})
            String sex,

            @NotNull(message = "생년월일을 입력해주세요.")
            @Schema(description = "고객 생년월일")
            LocalDate birth,

            @Schema(description = "추천인", requiredMode = NOT_REQUIRED)
            String recommender,

            @NotBlank(message = "배송받을 주소를 입력해주세요.")
            @Length(max = 100)
            @Schema(description = "고객 도로명 주소")
            String roadAddress
    ) implements Join {
    }
}
