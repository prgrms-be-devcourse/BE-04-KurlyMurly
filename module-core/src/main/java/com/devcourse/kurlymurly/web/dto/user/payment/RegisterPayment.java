package com.devcourse.kurlymurly.web.dto.user.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

import static com.devcourse.kurlymurly.web.dto.user.payment.RegisterPayment.creditRequest;
import static com.devcourse.kurlymurly.web.dto.user.payment.RegisterPayment.easyPayRequest;

public sealed interface RegisterPayment permits creditRequest, easyPayRequest {
    record creditRequest(
            @NotBlank(message = "결제수단 정보를 입력해주세요")
            @Schema(name = "결제 수단 정보")
            String payInfo,

            @NotBlank(message = "은행명을 입력해주세요.")
            @Schema(name = "은행명")
            String bank,

            @NotNull(message = "신용카드의 만료 날짜를 입력해주세요.")
            @Schema(name = "신용카드 만료일")
            Date expiredDate,

            @NotBlank(message = "신용카드의 비밀번호를 입력해주세요.")
            @Column(length = 2)
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
