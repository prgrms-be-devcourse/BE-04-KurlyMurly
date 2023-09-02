package com.devcourse.kurlymurly.web.product.review;

import com.devcourse.kurlymurly.module.product.domain.review.Review;
import com.devcourse.kurlymurly.module.product.domain.review.service.ReviewService;
import com.devcourse.kurlymurly.web.common.KurlyResponse;
import com.devcourse.kurlymurly.web.common.PageParam;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewCreate;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public KurlyResponse<ReviewCreate.Response> registerReview(@RequestBody ReviewCreate.Request request) {
        ReviewCreate.Response response = reviewService.registerReview(request);
        return KurlyResponse.ok(response);
    }

    @GetMapping
    public Page<Review> findReviewAll(@RequestBody PageParam param) {
        Pageable pageable = PageRequest.of(param.page(), param.size());
        return reviewService.findReviewAll(pageable);
    }

    @GetMapping("/{id}")
    public KurlyResponse<Review> findById(@PathVariable Long id) {
        Review review = reviewService.findById(id);
        return KurlyResponse.ok(review);
    }

    @GetMapping("/{userId}") // userId는 노출 x, TODO
    public List<Review> findAllByUserId(@PathVariable Long userId) {
        return reviewService.findAllByUserId(userId);
    }

    @PatchMapping("/{id}")
    public KurlyResponse<Void> updateReviewContent(@PathVariable Long id
            , @RequestBody ReviewCreate.UpdateRequest request) {
        reviewService.updateReviewContent(id, request.content());
        return KurlyResponse.noData();
    }

    @PatchMapping("/{id}/normal")
    public KurlyResponse<Void> updateOrderToProcessing(@PathVariable Long id) {
        reviewService.updateToNormal(id);
        return KurlyResponse.noData();
    }

    @PatchMapping("/{id}/banned")
    public KurlyResponse<Void> updateOrderToDelivering(@PathVariable Long id) {
        reviewService.updateToBanned(id);
        return KurlyResponse.noData();
    }

    @PatchMapping("/{id}/best")
    public KurlyResponse<Void> updateOrderToDeliveryDone(@PathVariable Long id) {
        reviewService.updateToBest(id);
        return KurlyResponse.noData();
    }

    @DeleteMapping("/{id}")
    public KurlyResponse<Void> cancelOrder(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return KurlyResponse.noData();
    }

}
