package com.devcourse.kurlymurly.web.product.review;

import com.devcourse.kurlymurly.module.product.domain.review.Review;
import com.devcourse.kurlymurly.module.product.domain.review.service.ReviewService;
import com.devcourse.kurlymurly.web.common.KurlyResponse;
import com.devcourse.kurlymurly.web.common.PageParam;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewCreate;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewLikeCreate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import static org.springframework.http.HttpStatus.*;

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
    public KurlyResponse<ReviewCreate.Response> registerReview(@RequestBody ReviewCreate.Request request) {
        ReviewCreate.Response response = reviewService.registerReview(request);
        return KurlyResponse.ok(response);
    }

    @GetMapping
    public Page<Review> findReviewAll(@RequestBody PageParam param) {
        Pageable pageable = PageRequest.of(param.page(), param.size());
        return reviewService.findReviewAll(pageable);
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

    // TODO: userId 노출 삭제
    @Tag(name = "review")
    @Operation(description = "사용자가 작성한 리뷰 조회 API")
    @ApiResponse(responseCode = "200", description = "성공적으로 review를 조회한 경우")
    @ApiResponse(responseCode = "400", description = "review를 조회하기 위한 user_id를 명시하지 않은 경우")
    @GetMapping("/{userId}")
    @ResponseStatus(OK)
    public List<Review> findAllByUserId(@PathVariable Long userId) {
        return reviewService.findAllByUserId(userId);
    }

    @Tag(name = "review")
    @Operation(description = "리뷰 수정 API")
    @ApiResponse(responseCode = "200", description = "성공적으로 review를 수정한 경우")
    @ApiResponse(responseCode = "400", description = "수정할 리뷰 id와 수정할 내용이 명시되지 않은 경우")
    @PatchMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> updateReviewContent(
            @PathVariable Long id,
            @RequestBody ReviewCreate.UpdateRequest request
    ) {
        reviewService.updateReviewContent(id, request.content(), request.isSecreted());
        return KurlyResponse.noData();
    }

    @Tag(name = "review")
    @Operation(description = "NORMAL 리뷰로 변환 API")
    @ApiResponse(responseCode = "200", description = "review 상태를 NORMAL로 변경한 경우")
    @ApiResponse(responseCode = "400", description = "리뷰 id가 명시되지 않은 경우")
    @PatchMapping("/{id}/normal")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> updateToNormal(@PathVariable Long id) {
        reviewService.updateToNormal(id);
        return KurlyResponse.noData();
    }

    @Tag(name = "review")
    @Operation(description = "BANNED 리뷰로 변환 API")
    @ApiResponse(responseCode = "200", description = "review 상태를 BANNED로 변경한 경우")
    @ApiResponse(responseCode = "400", description = "리뷰 id가 명시되지 않은 경우")
    @PatchMapping("/{id}/banned")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> updateToBanned(@PathVariable Long id) {
        reviewService.updateToBanned(id);
        return KurlyResponse.noData();
    }

    @Tag(name = "review")
    @Operation(description = "BEST 리뷰로 변환 API")
    @ApiResponse(responseCode = "200", description = "review 상태를 BEST로 변경한 경우")
    @ApiResponse(responseCode = "400", description = "리뷰 id가 명시되지 않은 경우")
    @PatchMapping("/{id}/best")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> updateToBest(@PathVariable Long id) {
        reviewService.updateToBest(id);
        return KurlyResponse.noData();
    }

    @Tag(name = "review")
    @Operation(description = "작성한 리뷰 삭제 API")
    @ApiResponse(responseCode = "200", description = "작성한 review를 삭제한 경우")
    @ApiResponse(responseCode = "400", description = "삭제할 리뷰 id가 명시되지 않은 경우")
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return KurlyResponse.noData();
    }

    @Tag(name = "review")
    @Operation(description = "리뷰 좋아요 API")
    @ApiResponse(responseCode = "200", description = "리뷰에 대한")
    @ApiResponse(responseCode = "400", description = "활성화를 위한 id가 넘어오지 않은 경우")
    @PostMapping("/likes")
    @ResponseStatus(OK)
    public KurlyResponse<ReviewLikeCreate.Response> createReviewLikes(@RequestBody ReviewLikeCreate.Request request) {
        ReviewLikeCreate.Response response = reviewService.createReviewLikes(request.likeUserId(), request.reviewId());
        return KurlyResponse.ok(response);
    }

    @Tag(name = "review")
    @Operation(description = "리뷰 좋아요 재활성화 API")
    @ApiResponse(responseCode = "200", description = "성공적으로 리뷰 좋아요를 재활성화한 경우")
    @ApiResponse(responseCode = "400", description = "활성화를 위한 id가 넘어오지 않은 경우")
    @PatchMapping("/likes/{id}")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> activeReviewLike(@PathVariable Long id) {
        reviewService.activeReviewLike(id);
        return KurlyResponse.noData();
    }

    @Tag(name = "review")
    @Operation(description = "리뷰 좋아요 취소 API")
    @ApiResponse(responseCode = "200", description = "성공적으로 리뷰 좋아요를 취소한 경우")
    @ApiResponse(responseCode = "400", description = "취소를 위한 id가 넘어오지 않은 경우")
    @DeleteMapping("/likes/{id}")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> cancelReviewLike(@PathVariable Long id) {
        reviewService.cancelReviewLike(id);
        return KurlyResponse.noData();
    }
}
