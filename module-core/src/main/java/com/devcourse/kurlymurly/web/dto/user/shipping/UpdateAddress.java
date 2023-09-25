package com.devcourse.kurlymurly.web.dto.user.shipping;

import com.devcourse.kurlymurly.module.user.domain.shipping.Info;
import com.devcourse.kurlymurly.module.user.domain.shipping.Shipping;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import static com.devcourse.kurlymurly.web.dto.UserRegexp.PHONE_NUMBER_REGEXP;
import static com.devcourse.kurlymurly.web.dto.UserRegexp.USER_NAME_REGEXP;

public sealed interface UpdateAddress permits UpdateAddress.Request, UpdateAddress.InfoRequest {
    record Request(
            @NotNull(message = "수정할 주소 id 값이 없습니다.")
            @Schema(name = "배송 주소 id값")
            Long addressId,

            @NotBlank(message = "상서 주소를 입력해주세요.")
            @Length(max = 100)
            @Schema(name = "상세 주소")
            String description,

            @NotBlank(message = "수취인 정보를 입력해주세요.")
            @Pattern(regexp = USER_NAME_REGEXP, message = "이름 형식이 올바르지 않습니다.")
            @Schema(name = "수취인")
            String receiver,

            @NotBlank(message = "수취인 연락처를 입력해주세요.")
            @Pattern(regexp = PHONE_NUMBER_REGEXP, message = "핸드폰 번호 형식이 아닙니다.")
            @Schema(name = "연락처")
            String contact
    ) implements UpdateAddress {
    }

    record InfoRequest(
            @NotNull(message = "요청 사항을 추가 할 주소 id값이 없습니다.")
            @Schema(name = "배송 주소 id값")
            Long addressId,

            @NotBlank(message = "수취인 정보를 입력해주세요.")
            @Schema(name = "수취인")
            String receiver,

            @NotBlank(message = "수취인 연락처를 입력해주세요.")
            @Schema(name = "연락처")
            String contact,

            @NotNull(message = "받으실 장소를 선택해주세요.")
            @Schema(name = "받으실 장소", allowableValues = {"DOOR", "OFFICE", "LOCKER", "ETC"})
            Info.Area receiveArea,

            @Schema(name = "공용 현관 비밀번호")
            String entrancePassword,

            @NotNull(message = "메세지를 받을 시간을 선택해주세요.")
            @Schema(name = "배송 메세지 전송 시간", allowableValues = {"ALWAYS", "AFTER_7_AM"})
            Info.AlertTime messageAlertTime
    ) implements UpdateAddress {
    }
}
