package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.product.domain.review.Review;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewLike;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewLikeRepository;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewRepository;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewResponse;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.devcourse.kurlymurly.global.exception.ErrorCode.NOT_FOUND_REVIEW;
import static com.devcourse.kurlymurly.global.exception.ErrorCode.NOT_FOUND_REVIEW_LIKE;
import static com.devcourse.kurlymurly.module.product.domain.review.Review.Status.DELETED;

@Component
@Transactional(readOnly = true)
public class ReviewQuery {
    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    public ReviewQuery(ReviewRepository reviewRepository, ReviewLikeRepository reviewLikeRepository) {
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

    public Slice<ReviewResponse.ReviewOfProduct> getReviewsOfProduct(Long productId, Long start) {
        return reviewRepository.getTenReviewsOfProductFromStart(productId, start)
                .map(this::toReviewOfProductResponse);
    }

    private ReviewResponse.ReviewOfProduct toReviewOfProductResponse(Review review) {
        return new ReviewResponse.ReviewOfProduct(
                review.getProductId(),
                review.getProductName(),
                review.getMaskedUserName(),
                review.getUserTier(),
                review.getContent(),
                review.getLikes(),
                review.getCreateAt(),
                review.isSecret()
        );
    }

    public Review findReviewByIdOrThrow(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new KurlyBaseException(NOT_FOUND_REVIEW));
    }

    public ReviewLike findLikesByIdOrThrow(Long likeId) {
        return reviewLikeRepository.findById(likeId)
                .orElseThrow(() -> KurlyBaseException.withId(NOT_FOUND_REVIEW_LIKE, likeId));
    }
}
