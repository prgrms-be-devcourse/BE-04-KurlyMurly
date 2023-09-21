package com.devcourse.kurlymurly.web.dto.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

import static com.devcourse.kurlymurly.web.dto.payment.RegisterPayment.creditRequest;
import static com.devcourse.kurlymurly.web.dto.payment.RegisterPayment.easyPayRequest;

public sealed interface RegisterPayment permits creditRequest, easyPayRequest {
    record creditRequest(
            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "결제 수단 정보")
            String payInfo,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "은행명")
            String bank,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "신용카드 만료일")
            Date expiredDate,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "비밀번호 앞 2자리")
            String password
    ) implements RegisterPayment {
    }

    record easyPayRequest(
            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "결제 수단 정보")
            String payInfo,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "은행명")
            String bank
    ) implements RegisterPayment {
    }
}
