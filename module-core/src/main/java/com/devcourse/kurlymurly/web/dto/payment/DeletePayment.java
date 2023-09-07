package com.devcourse.kurlymurly.web.dto.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public sealed interface DeletePayment permits DeletePayment.Request {
    record Request(
            @NotNull(message = "빈 값은 들어올 수 없습니다.")
            @Schema(name = "삭제 할 조회 수단 id값")
            Long paymentId
    ) implements DeletePayment {
    }
}
