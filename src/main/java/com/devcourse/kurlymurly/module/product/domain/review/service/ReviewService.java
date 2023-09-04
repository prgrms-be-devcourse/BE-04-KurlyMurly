package com.devcourse.kurlymurly.module.product.domain.review.service;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.product.domain.review.Review;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewJpaRepository;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewLikeJpaRepository;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewLikes;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewCreate;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewLikeCreate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.devcourse.kurlymurly.global.exception.ErrorCode.NOT_FOUND_REVIEW;
import static com.devcourse.kurlymurly.global.exception.ErrorCode.NOT_FOUND_REVIEW_LIKE;

@Service
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewJpaRepository reviewRepository;
    private final ReviewLikeJpaRepository reviewLikeRepository;

    public ReviewService(ReviewJpaRepository reviewRepository, ReviewLikeJpaRepository reviewLikeRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewLikeRepository = reviewLikeRepository;
    }

    @Transactional
    public ReviewCreate.Response registerReview(ReviewCreate.Request request) {
        Review review = new Review(
                request.userId(),
                request.productId(),
                request.orderId(),
                request.likes(),
                request.content()
        );

        reviewRepository.save(review);

        return toReviewResponse(request);
    }

    private ReviewCreate.Response toReviewResponse(ReviewCreate.Request request) {
        return new ReviewCreate.Response(
                request.userId(),
                request.productId(),
                request.orderId(),
                request.likes(),
                request.content()
        );
    }

    public Page<Review> findReviewAll(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }

    public Review findReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new KurlyBaseException(NOT_FOUND_REVIEW));
    }

    public List<Review> findAllByUserId(Long userId) {
        return reviewRepository.findAllByUserId(userId);
    }

    @Transactional
    public void updateReviewContent(Long id, String content, boolean isSecreted) {
        Review review = findReviewById(id);
        review.updateReview(content, isSecreted);
    }

    // TODO: 관리자 영역
    @Transactional
    public void updateToNormal(Long id) {
        Review review = findReviewById(id);
        review.toNormal();
    }
    @Transactional
    public void updateToBanned(Long id) {
        Review review = findReviewById(id);
        review.toBanned();
    }

    @Transactional
    public void updateToSecret(Long id) {
        Review review = findReviewById(id);
        review.toSecret();
    }

    @Transactional
    public void updateToBest(Long id) {
        Review review = findReviewById(id);
        review.toBest();
    }

    @Transactional
    public void deleteReview(Long id) {
        Review review = findReviewById(id);
        review.deleted();
    }

    /**
     * ReviewLike Service
     */
    public ReviewLikes findReviewLikesById(Long id) {
        return reviewLikeRepository.findById(id)
                .orElseThrow(() -> new KurlyBaseException(NOT_FOUND_REVIEW_LIKE));
    }

    @Transactional
    public ReviewLikeCreate.Response createReviewLikes(Long likeUserId, Long reviewId) {
        ReviewLikes reviewLikes = new ReviewLikes(likeUserId, reviewId);
        reviewLikeRepository.save(reviewLikes);

        Review review = findReviewById(reviewId);
        review.increaseLikes();

        return toReviewLikeResponse(reviewLikes);
    }

    private ReviewLikeCreate.Response toReviewLikeResponse(ReviewLikes reviewLikes) {
        return new ReviewLikeCreate.Response(
                reviewLikes.getLikeUserId(),
                reviewLikes.getReviewId()
        );
    }

    @Transactional
    public void activeReviewLike(Long id) {
        ReviewLikes reviewLikes = findReviewLikesById(id);

        Review review = findReviewById(reviewLikes.getReviewId());

        reviewLikes.activeLike();
        review.increaseLikes();
    }

    @Transactional
    public void cancelReviewLike(Long id) {
        ReviewLikes reviewLikes = findReviewLikesById(id);

        Review review = findReviewById(reviewLikes.getReviewId());

        reviewLikes.cancelLike();
        review.decreaseLikes();
    }
}
