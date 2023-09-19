package com.devcourse.kurlymurly.web.dto.product;

import com.devcourse.kurlymurly.module.product.domain.Product;
import com.devcourse.kurlymurly.module.product.domain.ProductDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import static com.devcourse.kurlymurly.web.dto.product.CreateProduct.Request;
import static com.devcourse.kurlymurly.web.dto.product.CreateProduct.Response;

public sealed interface CreateProduct permits Request, Response {
    record Request(
            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "카테고리 아이디")
            Long categoryId,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "상품 이름")
            String name,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "가벼운 상품 설명")
            String description,

            @Positive(message = "0보다 작은 값이 들어올 수 없습니다.")
            @Schema(name = "상품 가격")
            int price,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "배송 정보", allowableValues = {"EXPRESS", "NORMAL"})
            Product.Delivery delivery,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "판매자 정보")
            String seller,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "상품 보관 정보", allowableValues = {"FROZEN", "REFRIGERATE", "ROOM_TEMPERATURE"})
            ProductDetail.StorageType storageType,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "판매 단위")
            String saleUnit,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "중량/용량 정보")
            String weight,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "원산지 정보")
            String origin,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "알레르기 정보")
            String allergyInfo,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "유통기한 정보")
            String expirationInformation,

            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "컬리 Only")
            boolean isKurlyOnly
    ) implements CreateProduct {
    }

    record Response(
            String productName,
            int price,
            String delivery,
            String StorageType,
            String saleUnit
    ) implements CreateProduct {
    }
}
