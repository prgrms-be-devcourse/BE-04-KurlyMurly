package com.devcourse.kurlymurly.web.dto.product.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static com.devcourse.kurlymurly.web.dto.product.review.ReviewRequest.*;
import static com.devcourse.kurlymurly.web.dto.product.review.ReviewRequest.Create;
import static com.devcourse.kurlymurly.web.dto.product.review.ReviewRequest.OfProduct;
import static com.devcourse.kurlymurly.web.dto.product.review.ReviewRequest.Update;

public sealed interface ReviewRequest permits Create, Update, Like, OfProduct {
    record Create(
            @NotNull(message = "주문 번호로 빈 값이 들어올 수 없습니다.")
            @Schema(name = "주문 ID")
            Long orderId,

            @NotNull(message = "상품 번호로 빈 값이 들어올 수 없습니다.")
            @Schema(name = "상품 id")
            Long productId,

            @NotBlank(message = "내용에는 빈 값이 들어올 수 없습니다.")
            @Schema(name = "리뷰 내용")
            String content,

            @NotNull(message = "비밀 여부에 빈 값이 들어올 수 없습니다.")
            @Schema(name = "리뷰 비공개 유무")
            boolean isSecret
    ) implements ReviewRequest {
    }

    record Update(
            @NotBlank(message = "내용에는 빈 값이 들어올 수 없습니다.")
            @Schema(name = "수정한 리뷰 내용")
            String content,

            @NotNull(message = "비밀 여부에 빈 값이 들어올 수 없습니다.")
            @Schema(name = "리뷰 비공개 유무")
            boolean isSecret
    ) implements ReviewRequest {
    }

    record OfProduct(
            @NotNull(message = "시작점으로 빈 값이 들어올 수 없습니다.")
            @Schema(name = "slice 페이지 시작점")
            Long start
    ) implements ReviewRequest {
    }

    record Like(
            @NotNull(message = "사용자 번호로 빈 값이 들어올 수 없습니다.")
            @Schema(name = "좋아요 누른 사용자")
            Long likeUserId,

            @NotNull(message = "후기 번호에 빈 값이 들어올 수 없습니다.")
            @Schema(name = "리뷰 id")
            Long reviewId
    ) implements ReviewRequest {
    }
}
