package com.devcourse.kurlymurly.module.product.domain.review.service;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.product.domain.review.Review;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewLikeJpaRepository;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewLikes;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewLikeCreate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.devcourse.kurlymurly.global.exception.ErrorCode.NOT_FOUND_REVIEW_LIKE;

@Service
@Transactional(readOnly = true)
public class ReviewLikeService {
    private final ReviewLikeJpaRepository reviewLikeRepository;
    private final ReviewService reviewService;

    public ReviewLikeService(ReviewLikeJpaRepository reviewLikeRepository, ReviewService reviewService) {
        this.reviewLikeRepository = reviewLikeRepository;
        this.reviewService = reviewService;
    }

    private ReviewLikeCreate.Response toResponse(ReviewLikes reviewLikes) {
        return new ReviewLikeCreate.Response(
                reviewLikes.getLikeUserId(),
                reviewLikes.getReviewId()
        );
    }

    private ReviewLikes findReviewLikes(Long id) {
        return reviewLikeRepository.findById(id)
                .orElseThrow(() -> new KurlyBaseException(NOT_FOUND_REVIEW_LIKE));
    }

    @Transactional
    public ReviewLikeCreate.Response createReviewLikes(Long likeUserId, Long reviewId) {
        ReviewLikes reviewLikes = new ReviewLikes(likeUserId, reviewId);
        reviewLikeRepository.save(reviewLikes);

        Review review = reviewService.findById(reviewId);
        review.increaseLikes();

        return toResponse(reviewLikes);
    }

    @Transactional
    public void activeReviewLike(Long id) {
        ReviewLikes reviewLikes = findReviewLikes(id);

        Review review = reviewService.findById(reviewLikes.getReviewId());

        reviewLikes.activeLike();
        review.increaseLikes();
    }


    @Transactional
    public void cancelReviewLike(Long id) {
        ReviewLikes reviewLikes = findReviewLikes(id);

        Review review = reviewService.findById(reviewLikes.getReviewId());

        reviewLikes.cancelLike();
        review.decreaseLikes();
    }
}
