package com.devcourse.kurlymurly.domain.service;

import com.devcourse.kurlymurly.core.exception.KurlyBaseException;
import com.devcourse.kurlymurly.domain.product.review.Review;
import com.devcourse.kurlymurly.domain.product.review.ReviewLike;
import com.devcourse.kurlymurly.domain.product.review.ReviewLikeRepository;
import com.devcourse.kurlymurly.domain.product.review.ReviewRepository;
import com.devcourse.kurlymurly.web.product.ReviewResponse;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.devcourse.kurlymurly.core.exception.ErrorCode.NOT_FOUND_REVIEW;
import static com.devcourse.kurlymurly.core.exception.ErrorCode.NOT_FOUND_REVIEW_LIKE;

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
        return reviewRepository.getAllReviewsByUserId(userId);
    }

    public Slice<ReviewResponse.OfProduct> getReviewsOfProduct(Long productId, Long start) {
        return reviewRepository.getTenReviewsOfProductFromStart(productId, start);
    }

    public Review findReviewByIdOrThrow(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> KurlyBaseException.withId(NOT_FOUND_REVIEW, id));
    }

    public ReviewLike findLikesByIdOrThrow(Long likeId) {
        return reviewLikeRepository.findById(likeId)
                .orElseThrow(() -> KurlyBaseException.withId(NOT_FOUND_REVIEW_LIKE, likeId));
    }
}
