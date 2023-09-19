package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.module.product.domain.Product;
import com.devcourse.kurlymurly.module.product.domain.review.Review;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewLike;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewLikeRepository;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewRepository;
import com.devcourse.kurlymurly.module.user.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class ReviewCommand {
    private final ReviewQuery reviewQuery;
    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    public ReviewCommand(
            ReviewQuery reviewQuery,
            ReviewRepository reviewRepository,
            ReviewLikeRepository reviewLikeRepository
    ) {
        this.reviewQuery = reviewQuery;
        this.reviewRepository = reviewRepository;
        this.reviewLikeRepository = reviewLikeRepository;
    }

    @Transactional
    public void create(User user, Product product, String content, boolean isSecret) {
        Review review = new Review(user, product, content, isSecret);
        reviewRepository.save(review);
    }

    @Transactional
    public void updateReviewContent(Long id, String content, boolean isSecret) {
        Review review = reviewQuery.findReviewByIdOrThrow(id);
        review.updateReview(content, isSecret);
    }

    @Transactional
    public void updateToBanned(Long id) {
        Review review = reviewQuery.findReviewByIdOrThrow(id);
        review.toBanned();
    }

    @Transactional
    public void updateToBest(Long id) {
        Review review = reviewQuery.findReviewByIdOrThrow(id);
        review.toBest();
    }

    @Transactional
    public void deleteReview(Long id) {
        Review review = reviewQuery.findReviewByIdOrThrow(id);
        review.softDeleted();
    }

    @Transactional
    public void activeReviewLike(Long userId, Long reviewId) {
        ReviewLike reviewLike = reviewLikeRepository.findByUserIdAndReviewId(userId, reviewId)
                .orElseGet(() -> createLike(userId, reviewId));
        Review review = reviewQuery.findReviewByIdOrThrow(reviewLike.getReviewId());

        reviewLike.activeLike();
        review.liked();
    }

    private ReviewLike createLike(Long userId, Long reviewId) {
        ReviewLike reviewLike = new ReviewLike(userId, reviewId);
        return reviewLikeRepository.save(reviewLike);
    }

    @Transactional
    public void cancelReviewLike(Long likeId) {
        ReviewLike reviewLike = reviewQuery.findLikesByIdOrThrow(likeId);
        Review review = reviewQuery.findReviewByIdOrThrow(reviewLike.getReviewId());

        reviewLike.cancelLike();
        review.disliked();
    }
}
