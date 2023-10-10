package com.devcourse.kurlymurly.api.product;

import com.devcourse.kurlymurly.application.product.ProductFacade;
import com.devcourse.kurlymurly.auth.AuthUser;
import com.devcourse.kurlymurly.web.common.KurlyResponse;
import com.devcourse.kurlymurly.web.product.ReviewRequest;
import com.devcourse.kurlymurly.web.product.ReviewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Tag(name = "review", description = "리뷰 API")
@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ProductFacade productFacade;

    public ReviewController(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @Tag(name = "review")
    @Operation(summary = "특정 리뷰 조회", description = "리뷰 ID로 특정 리뷰 조회 API", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 특정한 후기를 조회한 경우"),
            @ApiResponse(responseCode = "404", description = "조회 할 후기 정보가 없어서 발생하는 에러")
    })
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public KurlyResponse<ReviewResponse.Reviewed> getSpecificReview(@PathVariable Long id) {
        ReviewResponse.Reviewed response = productFacade.loadSpecificReviewById(id);
        return KurlyResponse.ok(response);
    }

    @Tag(name = "review")
    @Operation(summary = "[토큰] 리뷰 수정", description = "[토큰 필요] 작성한 리뷰 수정 API", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 상품 후기를 수정한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않아서 발생하는 에러"),
            @ApiResponse(responseCode = "404", description = "존재하지 않은 상품 후기"),
            @ApiResponse(responseCode = "409", description = "작성자가 아닙니다.")
    })
    @PatchMapping("/{id}")
    @ResponseStatus(OK)
    public KurlyResponse<Void> updateReviewContent(
            @AuthenticationPrincipal AuthUser user,
            @PathVariable Long id,
            @RequestBody ReviewRequest.Update request
    ) {
        productFacade.updateReview(user.getId(), id, request);
        return KurlyResponse.noData();
    }

    @Tag(name = "review")
    @Operation(summary = "[토큰] 리뷰 삭제", description = "[토큰 필요] 작성한 리뷰 삭제 API", responses = {
            @ApiResponse(responseCode = "200", description = "작성한 review를 삭제한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않은 상품 후기"),
            @ApiResponse(responseCode = "409", description = "작성자가 아닙니다.")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public KurlyResponse<Void> deleteReview(
            @AuthenticationPrincipal AuthUser user,
            @PathVariable Long id
    ) {
        productFacade.deleteReview(user.getId(), id);
        return KurlyResponse.noData();
    }

    @Tag(name = "review")
    @Operation(summary = "[토큰] 리뷰 좋아요", description = "[토큰 필요] 리뷰 좋아요 API", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 리뷰 좋아요를 활성화한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않은 상품 후기")
    })
    @PostMapping("/{reviewId}/like")
    @ResponseStatus(OK)
    public KurlyResponse<Void> activeReviewLike(
            @AuthenticationPrincipal AuthUser user,
            @PathVariable Long reviewId
    ) {
        productFacade.likeReview(user.getId(), reviewId);
        return KurlyResponse.noData();
    }

    @Tag(name = "review")
    @Operation(summary = "[토큰] 리뷰 좋아요 취소", description = "[토큰 필요] 리뷰 좋아요 취소 API", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 리뷰 좋아요를 취소한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않은 상품 후기")
    })
    @DeleteMapping("/{reviewId}/like")
    @ResponseStatus(OK)
    public KurlyResponse<Void> cancelReviewLike(
            @AuthenticationPrincipal AuthUser user,
            @PathVariable Long reviewId
    ) {
        productFacade.cancelReviewLike(user.getId(), reviewId);
        return KurlyResponse.noData();
    }
}
