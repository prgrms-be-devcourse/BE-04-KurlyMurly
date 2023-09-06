package com.devcourse.kurlymurly.web.dto.product.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

import static com.devcourse.kurlymurly.web.dto.product.review.ReviewResponse.*;
import static com.devcourse.kurlymurly.web.dto.product.review.ReviewResponse.Reviewable;
import static com.devcourse.kurlymurly.web.dto.product.review.ReviewResponse.Reviewed;

public sealed interface ReviewResponse permits Reviewable, Reviewed, ReviewOfProduct {
    record Reviewable(
            Long productId,
            String name,
            LocalDateTime deliveredAt,
            LocalDateTime reviewDeadLine
    ) implements ReviewResponse {
    }

    record Reviewed(
            Long productId,
            String name,
            String content,
            LocalDateTime createdAt,
            boolean isSecret
    ) implements ReviewResponse {
    }

    record ReviewOfProduct(
            @NotNull(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "상품 아이디")
            Long productId,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "상품 이름")
            String productName,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "유저 이름")
            String userName,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "상품 등급")
            String userTier,

            @NotBlank(message = "빈 값이 들어올 수 없습니다.")
            @Schema(name = "리뷰 내용")
            String content,

            @PositiveOrZero(message = "도움돼요의 갯수는 음수일 수 없습니다.")
            @Schema(name = "도움돼요 갯수")
            Integer likes,

            @PastOrPresent(message = "리뷰 작성 시간은 조회 이전의 시간이어야 합니다.")
            @Schema(name = "리뷰 작성 시간")
            LocalDateTime createdAt,

            @AssertFalse(message = "공개 리뷰만 확인할 수 있습니다.")
            @Schema(name = "리뷰 비공개 유무")
            boolean isSecret
    ) implements ReviewResponse {
    }
}
