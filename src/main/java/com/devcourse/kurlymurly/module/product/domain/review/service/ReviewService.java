package com.devcourse.kurlymurly.module.product.domain.review.service;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.product.domain.review.Review;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewJpaRepository;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewCreate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.devcourse.kurlymurly.global.exception.ErrorCode.NOT_FOUND_REVIEW;

@Service
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewJpaRepository reviewRepository;

    public ReviewService(ReviewJpaRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    private ReviewCreate.Response toResponse(ReviewCreate.Request request) {
        return new ReviewCreate.Response(
                request.userId(),
                request.productId(),
                request.orderId(),
                request.likes(),
                request.content()
        );
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

        return toResponse(request);
    }

    public Page<Review> findReviewAll(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }

    public Review findById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new KurlyBaseException(NOT_FOUND_REVIEW));
    }

    public List<Review> findAllByUserId(Long userId) {
        return reviewRepository.findAllByUserId(userId);
    }

    @Transactional
    public void updateReviewContent(Long id, String content) {
        Review review = findById(id);
        review.updateReviewContent(content);
    }

    // 관리자 영역 TODO
    @Transactional
    public void updateToNormal(Long id) {
        Review review = findById(id);
        review.toNormalReview();
    }

    @Transactional
    public void updateToBanned(Long id) {
        Review review = findById(id);
        review.toBannedReview();
    }

    @Transactional
    public void updateToBest(Long id) {
        Review review = findById(id);
        review.toBestReview();
    }

    @Transactional
    public void deleteReview(Long id) {
        Review review = findById(id);
        review.deleteReview();
    }
}
