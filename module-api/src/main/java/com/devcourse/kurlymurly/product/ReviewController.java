package com.devcourse.kurlymurly.product;

import com.devcourse.kurlymurly.module.product.domain.review.Review;
import com.devcourse.kurlymurly.module.product.service.ReviewService;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.web.common.KurlyResponse;
import com.devcourse.kurlymurly.web.dto.product.review.CreateReview;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewRequest;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewResponse;
import com.devcourse.kurlymurly.web.dto.product.review.UpdateReview;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Slice;
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
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Tag(name = "review")
    @Operation(description = "리뷰 등록 API", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 review를 등록한 경우"),
            @ApiResponse(responseCode = "400", description = "삭제된 상품에 후기를 작성해서 발생하는 에러"),
            @ApiResponse(responseCode = "404", description = "주문 정보를 읽어오지 못해서 발생하는 에러")
    })
    @PostMapping
    @ResponseStatus(OK)
    public KurlyResponse<Void> registerReview(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid CreateReview.Request request
    ) {
        reviewService.registerReview(user, request);
        return KurlyResponse.noData();
    }

    @Tag(name = "review")
    @Operation(description = "특정 리뷰 조회 API", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 후기를 조회한 경우"),
            @ApiResponse(responseCode = "400", description = "후기를 조회하기 위한 Path Variable을 명시하지 않은 경우 발생하는 상태"),
            @ApiResponse(responseCode = "404", description = "조회 할 후기 정보가 없어서 발생하는 에러")
    })
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public KurlyResponse<Review> findById(@PathVariable Long id) { // todo: response 수정
        Review review = reviewService.findReviewById(id);
        return KurlyResponse.ok(review);
    }

    @Tag(name = "review")
    @Operation(description = "해당 상품에 대한 리뷰 조회 API", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 상품의 후기를 가져온 상태"),
            @ApiResponse(responseCode = "400", description = "review를 조회하기 위한 상품 id를 명시하지 않은 경우")
    })
    @GetMapping("/{productId}")
    @ResponseStatus(OK)
    public KurlyResponse<Slice<ReviewResponse.ReviewOfProduct>> getReviewsOfProduct(
            @PathVariable Long productId,
            @RequestBody @Valid ReviewRequest.OfProduct request) {
        Slice<ReviewResponse.ReviewOfProduct> reviewsOfProduct = reviewService.getReviewsOfProduct(productId, request.start());
        return KurlyResponse.ok(reviewsOfProduct);
    }


    @Tag(name = "review")
    @Operation(description = "[토큰 필요] 사용자가 작성한 리뷰 조회 API", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 상품의 후기를 가져온 상태"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않아서 발생하는 에러")
    })
    @GetMapping
    @ResponseStatus(OK)
    public KurlyResponse<List<ReviewResponse.Reviewed>> getAllReviewsOnMyPage(
            @AuthenticationPrincipal User user
    ) {
        List<ReviewResponse.Reviewed> response = reviewService.getAllReviewsOfUser(user.getId());
        return KurlyResponse.ok(response);
    }

    @Tag(name = "review")
    @Operation(description = "[토큰 필요] 리뷰 수정 API", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 상품 후기를 수정한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않아서 발생하는 에러"),
            @ApiResponse(responseCode = "404", description = "존재하지 않은 상품 후기")
    })
    @PatchMapping("/{id}")
    @ResponseStatus(OK)
    public KurlyResponse<Void> updateReviewContent(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody @Valid UpdateReview.Request request
    ) {
        reviewService.updateReviewContent(id, request.content(), request.isSecret());
        return KurlyResponse.noData();
    }

    @Tag(name = "review")
    @Operation(description = "[토큰 필요] 작성한 리뷰 삭제 API", responses = {
            @ApiResponse(responseCode = "200", description = "작성한 review를 삭제한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않은 상품 후기")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public KurlyResponse<Void> deleteReview(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        reviewService.deleteReview(id);
        return KurlyResponse.noData();
    }

    @Tag(name = "review")
    @Operation(description = "[토큰 필요] 리뷰 좋아요 API", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 리뷰 좋아요를 활성화한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않은 상품 후기")
    })
    @PatchMapping("/{reviewId}/likes")
    @ResponseStatus(OK)
    public KurlyResponse<Void> activeReviewLike(
            @AuthenticationPrincipal User user,
            @PathVariable Long reviewId
    ) {
        reviewService.activeReviewLike(user.getId(), reviewId);
        return KurlyResponse.noData();
    }

    @Tag(name = "review")
    @Operation(description = "[토큰 필요] 리뷰 좋아요 취소 API", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 리뷰 좋아요를 취소한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않은 상품 후기")
    })
    @DeleteMapping("/{reviewId}/likes")
    @ResponseStatus(OK)
    public KurlyResponse<Void> cancelReviewLike(
            @AuthenticationPrincipal User user,
            @PathVariable Long reviewId
    ) {
        reviewService.cancelReviewLike(reviewId);
        return KurlyResponse.noData();
    }
}