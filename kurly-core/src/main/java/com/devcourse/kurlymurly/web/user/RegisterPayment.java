package com.devcourse.kurlymurly.web.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

import static com.devcourse.kurlymurly.web.user.RegisterPayment.CreditRequest;
import static com.devcourse.kurlymurly.web.user.RegisterPayment.EasyPayRequest;

public sealed interface RegisterPayment permits CreditRequest, EasyPayRequest {
    record CreditRequest(
            @NotBlank(message = "결제수단 정보를 입력해주세요")
            @Schema(description = "결제 수단 정보")
            String payInfo,

            @NotBlank(message = "은행명을 입력해주세요.")
            @Schema(description = "은행명")
            String bank,

            @NotNull(message = "신용카드의 만료 날짜를 입력해주세요.")
            @Schema(description = "신용카드 만료일")
            Date expiredDate,

            @NotBlank(message = "신용카드의 비밀번호를 입력해주세요.")
            @Schema(description = "비밀번호 앞 2자리")
            String password
    ) implements RegisterPayment {
    }

    record EasyPayRequest(
            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(description = "결제 수단 정보")
            String payInfo,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(description = "은행명")
            String bank
    ) implements RegisterPayment {
    }
}
