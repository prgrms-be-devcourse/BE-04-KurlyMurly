package com.devcourse.kurlymurly.web.dto.user.shipping;

import com.devcourse.kurlymurly.module.user.domain.shipping.Info;
import com.devcourse.kurlymurly.module.user.domain.shipping.Shipping;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public sealed interface UpdateAddress permits UpdateAddress.Request, UpdateAddress.InfoRequest {
    record Request(
            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "배송 주소 id값")
            Long addressId,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "상세 주소")
            String description,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "수취인")
            String receiver,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "연락처")
            String contact
    ) implements UpdateAddress {
    }

    record InfoRequest(
            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "배송 주소 id값")
            Long addressId,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "수취인")
            String receiver,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "연락처")
            String contact,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "받으실 장소", allowableValues = {"DOOR", "OFFICE", "LOCKER", "ETC"})
            Info.Area receiveArea,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "공용 현관 비밀번호")
            String entrancePassword,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "배송 메세지 전송 시간", allowableValues = {"ALWAYS", "AFTER_7_AM"})
            Info.AlertTime messageAlertTime
    ) implements UpdateAddress {

    }
}
