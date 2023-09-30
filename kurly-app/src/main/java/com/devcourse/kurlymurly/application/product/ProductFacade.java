package com.devcourse.kurlymurly.application.product;

import com.devcourse.kurlymurly.domain.user.User;
import com.devcourse.kurlymurly.web.product.FavoriteResponse;
import com.devcourse.kurlymurly.web.product.ProductRequest;
import com.devcourse.kurlymurly.web.product.ProductResponse;
import com.devcourse.kurlymurly.web.product.ReviewRequest;
import com.devcourse.kurlymurly.web.product.ReviewResponse;
import com.devcourse.kurlymurly.web.product.SupportRequest;
import com.devcourse.kurlymurly.web.product.SupportResponse;
import com.devcourse.kurlymurly.image.service.ImageService;
import com.devcourse.kurlymurly.module.order.service.OrderService;
import com.devcourse.kurlymurly.module.product.domain.Product;
import com.devcourse.kurlymurly.module.product.domain.ProductDomain;
import com.devcourse.kurlymurly.module.product.domain.SupportDomain;
import com.devcourse.kurlymurly.module.product.domain.review.Review;
import com.devcourse.kurlymurly.module.product.service.ProductCommand;
import com.devcourse.kurlymurly.module.product.service.ProductQuery;
import com.devcourse.kurlymurly.module.product.service.ReviewCommand;
import com.devcourse.kurlymurly.module.product.service.ReviewQuery;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class ProductFacade {
    private final ProductQuery productQuery;
    private final ProductCommand productCommand;
    private final ReviewQuery reviewQuery;
    private final ReviewCommand reviewCommand;
    private final OrderService orderService;
    private final ProductMapper productMapper;
    private final ImageService imageService;

    public ProductFacade(
            ProductQuery productQuery,
            ProductCommand productCommand,
            ReviewQuery reviewQuery,
            ReviewCommand reviewCommand,
            OrderService orderService,
            ProductMapper productMapper,
            ImageService imageService
    ) {
        this.productQuery = productQuery;
        this.productCommand = productCommand;
        this.reviewQuery = reviewQuery;
        this.reviewCommand = reviewCommand;
        this.orderService = orderService;
        this.productMapper = productMapper;
        this.imageService = imageService;
    }

    public List<FavoriteResponse.Get> getUserFavorites(Long userId) {
        return productQuery.getAllFavoritesByUserId(userId);
    }

    // todo : user api
    public Slice<SupportResponse.Create> getAllMySupports(Long userId, Long lastId) {
        return productQuery.getTenSupportsOfUserPageFromLastId(userId, lastId);
    }

    public Page<ProductResponse.GetSimple> loadProductPageResponse(
            Long categoryId,
            @Valid Pageable pageable
    ) {
        return productQuery.getProductsPageOfCategory(categoryId, pageable);
    }

    public Page<ProductResponse.GetSimple> loadNewProductPageResponse(
            @Valid Pageable pageable
    ) {
        return productQuery.getNewProductPageResponse(pageable);
    }

    public Slice<ReviewResponse.OfProduct> loadReviewsOfProduct(
            Long productId,
            @Valid ReviewRequest.OfProduct request
    ) {
        return reviewQuery.getReviewsOfProduct(productId, request.start());
    }

    public List<ReviewResponse.Reviewed> loadReviewsOfUser(Long userId) {
        return reviewQuery.getAllReviewsOfUser(userId);
    }

    public ProductResponse.Create createProduct(
            MultipartFile image,
            @Valid ProductRequest.Create request
    ) {
        ProductDomain productDomain = productMapper.toProductDomain(request);
        String imageUrl = imageService.upload(image);

        productCommand.create(request.categoryId(), imageUrl, productDomain);
        return productMapper.toCreateResponse(request);
    }

    public void createProductSupport(
            Long userId,
            Long productId,
            @Valid SupportRequest.Create request
    ) {
        SupportDomain supportDomain = productMapper.toSupportDomain(request);
        productCommand.createSupport(userId, productId, supportDomain);
    }

    public void updateProductSupport(
            Long userId,
            Long supportId,
            @Valid SupportRequest.Update request
    ) {
        SupportDomain supportDomain = productMapper.toSupportDomain(request);
        productCommand.updateSupport(userId, supportId, supportDomain);
    }

    public void registerReview(
            User user,
            @Valid ReviewRequest.Create request
    ) {
        Product product = productQuery.findProductByIdOrThrow(request.productId());
        product.validateSupportable();

        reviewCommand.create(user, product, request.content(), request.isSecret());
        orderService.reviewOrderItem(request.orderId(), request.productId());
    }

    public void favoriteProduct(Long userId, Long productId) {
        productCommand.favoriteProduct(userId, productId);
    }

    public void cancelFavorite(Long userId, Long productId) {
        productCommand.cancelFavorite(userId, productId);
    }

    public void updateReview(
            Long userId,
            Long reviewId,
            @Valid ReviewRequest.Update request
    ) {
        reviewCommand.update(userId, reviewId, request.content(), request.isSecret());
    }

    public void deleteReview(Long userId, Long reviewId) {
        reviewCommand.delete(userId, reviewId);
    }

    public void likeReview(Long userId, Long reviewId) {
        reviewCommand.likeReview(userId, reviewId);
    }

    public void cancelReviewLike(Long userId, Long reviewId) {
        reviewCommand.cancelLike(userId, reviewId);
    }

    public ReviewResponse.Reviewed loadSpecificReviewById(Long id) {
        Review review = reviewQuery.findReviewByIdOrThrow(id);
        return productMapper.toReviewedResponse(review);
    }
}
