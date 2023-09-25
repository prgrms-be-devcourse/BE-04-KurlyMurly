package com.devcourse.kurlymurly.web.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import static com.devcourse.kurlymurly.web.product.ProductRequest.Create;

public sealed interface ProductRequest permits Create {
    record Create(
            @NotNull(message = "카테고리 번호로 빈 값이 들어올 수 없습니다.")
            @Schema(name = "카테고리 아이디")
            Long categoryId,

            @NotBlank(message = "상품 이름에 빈 값이 들어올 수 없습니다.")
            @Schema(name = "상품 이름")
            String name,

            @NotBlank(message = "상품 설명에 빈 값이 들어올 수 없습니다.")
            @Schema(name = "가벼운 상품 설명")
            String description,

            @Positive(message = "상품 가격에 0보다 작은 값이 들어올 수 없습니다.")
            @Schema(name = "상품 가격")
            int price,

            @NotNull(message = "배송 정보로 빈 값이 들어올 수 없습니다.")
            @Schema(name = "배송 정보", allowableValues = {"EXPRESS", "NORMAL"})
            String delivery,

            @NotBlank(message = "판매자 정보로 빈 값이 들어올 수 없습니다.")
            @Schema(name = "판매자 정보")
            String seller,

            @NotNull(message = "상품 보관 정보로 빈 값이 들어올 수 없습니다.")
            @Schema(name = "상품 보관 정보", allowableValues = {"FROZEN", "REFRIGERATE", "ROOM_TEMPERATURE"})
            String storageType,

            @NotBlank(message = "판매 단위로 빈 값이 들어올 수 없습니다.")
            @Schema(name = "판매 단위")
            String saleUnit,

            @NotBlank(message = "용량 정보로 빈 값이 들어올 수 없습니다.")
            @Schema(name = "중량/용량 정보")
            String weight,

            @NotBlank(message = "원산지 정보로 빈 값이 들어올 수 없습니다.")
            @Schema(name = "원산지 정보")
            String origin,

            @NotBlank(message = "알레르기 정보로 빈 값이 들어올 수 없습니다.")
            @Schema(name = "알레르기 정보")
            String allergyInfo,

            @NotBlank(message = "유통기한 정보로 빈 값이 들어올 수 없습니다.")
            @Schema(name = "유통기한 정보")
            String expirationInformation,

            @NotNull(message = "컬리 Only 상품에 빈 값이 들어올 수 없습니다.")
            @Schema(name = "컬리 Only")
            boolean isKurlyOnly
    ) implements ProductRequest {
    }
}
