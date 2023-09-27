package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.core.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.product.domain.Product;
import com.devcourse.kurlymurly.module.product.domain.review.Review;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewLike;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewLikeRepository;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewRepository;
import com.devcourse.kurlymurly.module.user.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.devcourse.kurlymurly.core.exception.ErrorCode.NEVER_LIKED;

@Component
@Transactional
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

    public void create(User user, Product product, String content, boolean isSecret) {
        Review review = new Review(user, product, content, isSecret);
        reviewRepository.save(review);
    }

    public void update(Long userId, Long id, String content, boolean isSecret) {
        Review review = reviewQuery.findReviewByIdOrThrow(id);
        review.validateAuthor(userId);
        review.update(content, isSecret);
    }

    public void banned(Long id) {
        Review review = reviewQuery.findReviewByIdOrThrow(id);
        review.ban();
    }

    public void toBestReview(Long id) {
        Review review = reviewQuery.findReviewByIdOrThrow(id);
        review.toBest();
    }

    public void delete(Long userId, Long id) {
        Review review = reviewQuery.findReviewByIdOrThrow(id);
        review.validateAuthor(userId);
        review.softDelete();
    }

    public void likeReview(Long userId, Long reviewId) {
        ReviewLike like = reviewLikeRepository.findByUserIdAndReviewId(userId, reviewId)
                .orElseGet(() -> createLike(userId, reviewId));
        Review review = reviewQuery.findReviewByIdOrThrow(like.getReviewId());

        like.activate();
        review.liked();
    }

    private ReviewLike createLike(Long userId, Long reviewId) {
        ReviewLike like = new ReviewLike(userId, reviewId);
        return reviewLikeRepository.save(like);
    }

    public void cancelLike(Long userId, Long reviewId) {
        ReviewLike like = reviewLikeRepository.findByUserIdAndReviewId(userId, reviewId)
                .orElseThrow(() -> KurlyBaseException.withId(NEVER_LIKED, userId));
        Review review = reviewQuery.findReviewByIdOrThrow(like.getReviewId());

        like.cancel();
        review.disliked();
    }
}
