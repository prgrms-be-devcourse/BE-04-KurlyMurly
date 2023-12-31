package com.devcourse.kurlymurly.web.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import static com.devcourse.kurlymurly.web.product.ReviewRequest.Create;
import static com.devcourse.kurlymurly.web.product.ReviewRequest.Like;
import static com.devcourse.kurlymurly.web.product.ReviewRequest.OfProduct;
import static com.devcourse.kurlymurly.web.product.ReviewRequest.Update;

public sealed interface ReviewRequest permits Create, Update, Like, OfProduct {
    record Create(
            @NotNull(message = "주문 번호로 빈 값이 들어올 수 없습니다.")
            @Schema(description = "주문 ID")
            Long orderId,

            @NotNull(message = "상품 번호로 빈 값이 들어올 수 없습니다.")
            @Schema(description = "상품 id")
            Long productId,

            @PositiveOrZero(message = "주문 상품 번호로 음수가 입력될 수 없습니다.")
            @Schema(description = "주문 상품 인덱스")
            int lineIndex,

            @NotBlank(message = "내용에는 빈 값이 들어올 수 없습니다.")
            @Schema(description = "리뷰 내용")
            String content,

            @NotNull(message = "비밀 여부에 빈 값이 들어올 수 없습니다.")
            @Schema(description = "리뷰 비공개 유무")
            boolean isSecret
    ) implements ReviewRequest {
    }

    record Update(
            @NotBlank(message = "내용에는 빈 값이 들어올 수 없습니다.")
            @Schema(description = "수정한 리뷰 내용")
            String content,

            @NotNull(message = "비밀 여부에 빈 값이 들어올 수 없습니다.")
            @Schema(description = "리뷰 비공개 유무")
            boolean isSecret
    ) implements ReviewRequest {
    }

    record OfProduct(
            @NotNull(message = "시작점으로 빈 값이 들어올 수 없습니다.")
            @Schema(description = "slice 페이지 시작점")
            Long start
    ) implements ReviewRequest {
    }

    record Like(
            @NotNull(message = "사용자 번호로 빈 값이 들어올 수 없습니다.")
            @Schema(description = "좋아요 누른 사용자")
            Long likeUserId,

            @NotNull(message = "후기 번호에 빈 값이 들어올 수 없습니다.")
            @Schema(description = "리뷰 id")
            Long reviewId
    ) implements ReviewRequest {
    }
}
