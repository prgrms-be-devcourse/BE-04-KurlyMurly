package com.devcourse.kurlymurly.web.dto.user.shipping;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public sealed interface AddAddress permits AddAddress.Request {
    record Request(
            @NotBlank(message = "배송받을 주소를 입력해주세요.")
            @Length(max = 100)
            @Schema(name = "도로명 주소")
            String roadAddress
    ) implements AddAddress {
    }
}
