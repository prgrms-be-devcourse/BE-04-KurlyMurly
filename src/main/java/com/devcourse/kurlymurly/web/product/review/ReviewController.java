package com.devcourse.kurlymurly.web.product.review;

import com.devcourse.kurlymurly.module.product.domain.review.Review;
import com.devcourse.kurlymurly.module.product.service.ReviewService;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.web.common.KurlyResponse;
import com.devcourse.kurlymurly.web.dto.product.review.CreateReview;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewRequest;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewResponse;
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

import static org.springframework.http.HttpStatus.NO_CONTENT;
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
    @Operation(description = "리뷰 등록 API")
    @ApiResponse(responseCode = "200", description = "성공적으로 review를 등록한 경우")
    @ApiResponse(responseCode = "400", description = "review를 생성하기 위한 request가 올바르지 않게 넘어온 경우")
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
    @Operation(description = "특정 리뷰 조회 API")
    @ApiResponse(responseCode = "200", description = "성공적으로 review를 조회한 경우")
    @ApiResponse(responseCode = "400", description = "review를 조회하기 위한 id를 명시하지 않은 경우")
    @ApiResponse(responseCode = "404", description = "조회 할 review 데이터가 없는 경우")
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public KurlyResponse<Review> findById(@PathVariable Long id) {
        Review review = reviewService.findReviewById(id);
        return KurlyResponse.ok(review);
    }

    @Tag(name = "review")
    @Operation(description = "해당 상품에 대한 리뷰 조회 API")
    @ApiResponse(responseCode = "200", description = "성공적으로 상품의 review를 조회한 경우")
    @ApiResponse(responseCode = "400", description = "review를 조회하기 위한 상품 id를 명시하지 않은 경우")
    @ApiResponse(responseCode = "404", description = "조회 할 review 데이터가 없는 경우")
    @GetMapping("/{productId}")
    @ResponseStatus(OK)
    public KurlyResponse<Slice<ReviewResponse.ReviewOfProduct>> getReviewsOfProduct(
            @PathVariable Long productId,
            @RequestBody @Valid ReviewRequest.OfProduct request) {
        Slice<ReviewResponse.ReviewOfProduct> reviewsOfProduct = reviewService.getReviewsOfProduct(productId, request.start());
        return KurlyResponse.ok(reviewsOfProduct);
    }


    @Tag(name = "review")
    @Operation(description = "[토큰 필요] 사용자가 작성한 리뷰 조회 API")
    @ApiResponse(responseCode = "200", description = "성공적으로 review를 조회한 경우")
    @ApiResponse(responseCode = "400", description = "review를 조회하기 위한 user_id를 명시하지 않은 경우")
    @GetMapping("/{userId}")
    @ResponseStatus(OK)
    public KurlyResponse<List<ReviewResponse.Reviewed>> getAllReviewsOnMyPage(
            @AuthenticationPrincipal User user
    ) {
        List<ReviewResponse.Reviewed> response = reviewService.getAllReviewsOfUser(user.getId());
        return KurlyResponse.ok(response);
    }

    @Tag(name = "review")
    @Operation(description = "[토큰 필요] 리뷰 수정 API")
    @ApiResponse(responseCode = "200", description = "성공적으로 상품 후기를 수정한 경우")
    @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    @ApiResponse(responseCode = "404", description = "존재하지 않은 상품 후기")
    @PatchMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> updateReviewContent(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody @Valid CreateReview.UpdateRequest request
    ) {
        reviewService.updateReviewContent(id, request.content(), request.isSecret());
        return KurlyResponse.noData();
    }

    @Tag(name = "review")
    @Operation(description = "[토큰 필요] 작성한 리뷰 삭제 API")
    @ApiResponse(responseCode = "200", description = "작성한 review를 삭제한 경우")
    @ApiResponse(responseCode = "400", description = "삭제할 리뷰 id가 명시되지 않은 경우")
    @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> deleteReview(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        reviewService.deleteReview(id);
        return KurlyResponse.noData();
    }

    @Tag(name = "review")
    @Operation(description = "[토큰 필요] 리뷰 좋아요 API")
    @ApiResponse(responseCode = "200", description = "성공적으로 리뷰 좋아요를 활성화한 경우")
    @ApiResponse(responseCode = "400", description = "활성화를 위한 id가 넘어오지 않은 경우")
    @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    @PatchMapping("/{reviewId}/likes")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> activeReviewLike(
            @AuthenticationPrincipal User user,
            @PathVariable Long reviewId
    ) {
        reviewService.activeReviewLike(user.getId(), reviewId);
        return KurlyResponse.noData();
    }

    @Tag(name = "review")
    @Operation(description = "[토큰 필요] 리뷰 좋아요 취소 API")
    @ApiResponse(responseCode = "200", description = "성공적으로 리뷰 좋아요를 취소한 경우")
    @ApiResponse(responseCode = "400", description = "취소를 위한 id가 넘어오지 않은 경우")
    @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    @DeleteMapping("/{reviewId}/likes")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> cancelReviewLike(
            @AuthenticationPrincipal User user,
            @PathVariable Long reviewId
    ) {
        reviewService.cancelReviewLike(reviewId);
        return KurlyResponse.noData();
    }
}
