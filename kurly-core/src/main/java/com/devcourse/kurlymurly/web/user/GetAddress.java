package com.devcourse.kurlymurly.web.user;

import io.swagger.v3.oas.annotations.media.Schema;

import static com.devcourse.kurlymurly.web.user.GetAddress.Response;

public sealed interface GetAddress permits Response {
    record Response(
            @Schema(name = "기본 배송지 여부")
            boolean isDefault,

            @Schema(name = "샛별 배송지 여부")
            boolean isExpress,

            @Schema(name = "도로명 주소 + 상세 주소")
            String address,

            @Schema(name = "수취인")
            String receiver,

            @Schema(name = "연락처")
            String phoneNumber
    ) implements GetAddress {
    }
}
