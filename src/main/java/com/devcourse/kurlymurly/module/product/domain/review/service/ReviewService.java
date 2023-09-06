package com.devcourse.kurlymurly.module.product.domain.review.service;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.product.domain.Product;
import com.devcourse.kurlymurly.module.product.domain.review.Review;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewRepository;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewLikeJpaRepository;
import com.devcourse.kurlymurly.module.product.domain.review.ReviewLike;
import com.devcourse.kurlymurly.module.product.service.ProductRetrieve;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.module.user.domain.UserRepository;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewCreate;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.devcourse.kurlymurly.global.exception.ErrorCode.NOT_EXISTS_USER;
import static com.devcourse.kurlymurly.global.exception.ErrorCode.NOT_FOUND_REVIEW;
import static com.devcourse.kurlymurly.global.exception.ErrorCode.NOT_FOUND_REVIEW_LIKE;

@Service
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewLikeJpaRepository reviewLikeRepository;
    private final ProductRetrieve productRetrieve;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, ReviewLikeJpaRepository reviewLikeRepository,
                         ProductRetrieve productRetrieve, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewLikeRepository = reviewLikeRepository;
        this.productRetrieve = productRetrieve;
        this.userRepository = userRepository;
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

    public List<ReviewResponse.ReviewOfProduct> getReviewsOfProduct(Long productId) {
        return reviewRepository.findAllByProductIdOrderByCreateAt(productId).stream()
                .map(this::toReviewOfProductResponse)
                .toList();
    }

    private ReviewResponse.ReviewOfProduct toReviewOfProductResponse(Review review) {
        Product product = productRetrieve.findByIdOrThrow(review.getProductId());

        User user = userRepository.findById(review.getUserId())
                .orElseThrow(() -> new KurlyBaseException(NOT_EXISTS_USER));

        return new ReviewResponse.ReviewOfProduct
                (
                        product.getId(),
                        product.getName(),
                        getMaskedUserName(user.getName()),
                        user.getTier().name(),
                        review.getContent(),
                        review.getLikes(),
                        review.getCreateAt(),
                        review.isSecreted()
                );
    }

    private String getMaskedUserName(String name) {
        return name.replaceAll("(?<=.{1})", "*");
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
        review.increaseLikes();
    }

    private ReviewLike createReviewLikes(Long userId, Long reviewId) {
        ReviewLike reviewLike = new ReviewLike(userId, reviewId);
        reviewLikeRepository.save(reviewLike);

        Review review = findReviewById(reviewId);
        review.increaseLikes();

        return reviewLike;
    }

    @Transactional
    public void cancelReviewLike(Long id) {
        ReviewLike reviewLike = findReviewLikesById(id);

        Review review = findReviewById(reviewLike.getReviewId());

        reviewLike.cancelLike();
        review.decreaseLikes();
    }

    private ReviewLike findReviewLikesById(Long id) {
        return reviewLikeRepository.findById(id)
                .orElseThrow(() -> new KurlyBaseException(NOT_FOUND_REVIEW_LIKE));
    }
}
