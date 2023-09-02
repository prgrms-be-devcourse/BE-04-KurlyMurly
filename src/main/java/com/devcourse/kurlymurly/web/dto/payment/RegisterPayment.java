package com.devcourse.kurlymurly.web.dto.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public sealed interface RegisterPayment permits RegisterPayment.creditRequest, RegisterPayment.easyPayRequest {
    record creditRequest(
            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "결제 수단 정보")
            String payInfo,

            @NotNull
            @Schema(name = "은행명")
            String bank,

            @NotNull
            @Schema(name = "신용카드 만료일")
            Date expiredDate,

            @NotNull
            @Schema(name = "비밀번호 앞 2자리")
            int password
    ) implements RegisterPayment {
    }

    record easyPayRequest(
            @NotNull
            @Schema(name = "결제 수단 정보")
            String payInfo,

            @NotNull
            @Schema(name = "은행명")
            String bank
    ) implements RegisterPayment {
    }
}
