package com.devcourse.kurlymurly.web.product.review;

import com.devcourse.kurlymurly.module.product.domain.review.service.ReviewLikeService;
import com.devcourse.kurlymurly.web.common.KurlyResponse;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewLikeCreate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
public class ReviewLikeController {
    private final ReviewLikeService reviewLikeService;

    public ReviewLikeController(ReviewLikeService reviewLikeService) {
        this.reviewLikeService = reviewLikeService;
    }

    @PostMapping
    public KurlyResponse<ReviewLikeCreate.Response> createReviewLikes(@RequestBody ReviewLikeCreate.Request request) {
        ReviewLikeCreate.Response response = reviewLikeService.createReviewLikes(request.likeUserId(), request.reviewId());
        return KurlyResponse.ok(response);
    }
    @PatchMapping("/{id}")
    public KurlyResponse<Void> activeReviewLike(@PathVariable Long id) {
        reviewLikeService.activeReviewLike(id);
        return KurlyResponse.noData();
    }
    @DeleteMapping("/{id}")
    public KurlyResponse<Void> cancelReviewLike(@PathVariable Long id) {
        reviewLikeService.cancelReviewLike(id);
        return KurlyResponse.noData();
    }
}
