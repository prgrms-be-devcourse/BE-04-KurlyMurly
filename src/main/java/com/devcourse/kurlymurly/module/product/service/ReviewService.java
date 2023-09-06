package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.product.domain.review.Review;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewRepository;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewLikeRepository;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewLike;
import com.devcourse.kurlymurly.web.dto.product.review.CreateReview;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.devcourse.kurlymurly.global.exception.ErrorCode.NOT_FOUND_REVIEW;
import static com.devcourse.kurlymurly.global.exception.ErrorCode.NOT_FOUND_REVIEW_LIKE;
import static com.devcourse.kurlymurly.module.product.domain.review.Review.Status.*;

@Service
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    public ReviewService(ReviewRepository reviewRepository, ReviewLikeRepository reviewLikeRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewLikeRepository = reviewLikeRepository;
    }

    public List<ReviewResponse.Reviewed> getAllReviewsOfUser(Long userId) {
        return reviewRepository.findAllByUserIdAndStatusIsNotOrderByIdDesc(userId, DELETED).stream()
                .map(this::toReviewedResponse)
                .toList();
    }

    private ReviewResponse.Reviewed toReviewedResponse(Review review) {
        return new ReviewResponse.Reviewed(
                review.getProductId(),
                review.getProductName(),
                review.getContent(),
                review.isSecret(),
                review.getCreateAt(),
                review.getUpdatedAt()
        );
    }

    @Transactional
    public void registerReview(Long userId, CreateReview.Request request) {
        Review review = new Review(userId, request.productId(), request.productName(), request.content(), request.isSecret());
        reviewRepository.save(review);
    }

    public Review findReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new KurlyBaseException(NOT_FOUND_REVIEW));
    }

    @Transactional
    public void updateReviewContent(Long id, String content, boolean isSecret) {
        Review review = findReviewById(id);
        review.updateReview(content, isSecret);
    }

    // TODO: 관리자 영역
    @Transactional
    public void updateToBanned(Long id) {
        Review review = findReviewById(id);
        review.toBanned();
    }

    @Transactional
    public void updateToBest(Long id) {
        Review review = findReviewById(id);
        review.toBest();
    }

    @Transactional
    public void deleteReview(Long id) {
        Review review = findReviewById(id);
        review.softDeleted();
    }

    /**
     * ReviewLike Service
     */
    @Transactional
    public void activeReviewLike(Long userId, Long reviewId) {
        ReviewLike reviewLike = reviewLikeRepository.findByUserIdAndReviewId(userId, reviewId)
                .orElseGet(() -> createReviewLikes(userId, reviewId));

        Review review = findReviewById(reviewLike.getReviewId());

        reviewLike.activeLike();
        review.liked();
    }

    private ReviewLike createReviewLikes(Long userId, Long reviewId) {
        ReviewLike reviewLike = new ReviewLike(userId, reviewId);
        reviewLikeRepository.save(reviewLike);

        Review review = findReviewById(reviewId);
        review.liked();

        return reviewLike;
    }

    @Transactional
    public void cancelReviewLike(Long id) {
        ReviewLike reviewLike = findReviewLikesById(id);

        Review review = findReviewById(reviewLike.getReviewId());

        reviewLike.cancelLike();
        review.disliked();
    }

    private ReviewLike findReviewLikesById(Long id) {
        return reviewLikeRepository.findById(id)
                .orElseThrow(() -> new KurlyBaseException(NOT_FOUND_REVIEW_LIKE));
    }
}