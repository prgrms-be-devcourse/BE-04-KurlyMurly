package com.devcourse.kurlymurly.web.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public sealed interface JoinUser permits JoinUser.Request {
    record Request(
            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "사용할 아이디")
            String loginId,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "사용할 비밀번호")
            String password,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "비밀번호 재확인")
            String checkPassword,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "고객 이름")
            String name,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "고객 이메일")
            String email,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "고객 연락처")
            String phoneNumber,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "고객 성별")
            String sex,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "고객 생년월일")
            LocalDate birth,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "추천인")
            String recommender,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "고객 도로명 주소")
            String roadAddress
    ) implements JoinUser {
    }
}
