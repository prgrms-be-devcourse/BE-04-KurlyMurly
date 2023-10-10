package com.devcourse.kurlymurly.application.product;

import com.devcourse.kurlymurly.application.image.ImageUploader;
import com.devcourse.kurlymurly.domain.product.Product;
import com.devcourse.kurlymurly.domain.product.ProductDomain;
import com.devcourse.kurlymurly.domain.product.SupportDomain;
import com.devcourse.kurlymurly.domain.product.review.Review;
import com.devcourse.kurlymurly.domain.service.OrderService;
import com.devcourse.kurlymurly.domain.service.ProductCommand;
import com.devcourse.kurlymurly.domain.service.ProductQuery;
import com.devcourse.kurlymurly.domain.service.ReviewCommand;
import com.devcourse.kurlymurly.domain.service.ReviewQuery;
import com.devcourse.kurlymurly.web.common.KurlyPagingRequest;
import com.devcourse.kurlymurly.web.product.FavoriteResponse;
import com.devcourse.kurlymurly.web.product.ProductRequest;
import com.devcourse.kurlymurly.web.product.ProductResponse;
import com.devcourse.kurlymurly.web.product.ReviewRequest;
import com.devcourse.kurlymurly.web.product.ReviewResponse;
import com.devcourse.kurlymurly.web.product.SupportRequest;
import com.devcourse.kurlymurly.web.product.SupportResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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
    private final ImageUploader imageUploader;

    public ProductFacade(
            ProductQuery productQuery,
            ProductCommand productCommand,
            ReviewQuery reviewQuery,
            ReviewCommand reviewCommand,
            OrderService orderService,
            ProductMapper productMapper,
            ImageUploader imageUploader
    ) {
        this.productQuery = productQuery;
        this.productCommand = productCommand;
        this.reviewQuery = reviewQuery;
        this.reviewCommand = reviewCommand;
        this.orderService = orderService;
        this.productMapper = productMapper;
        this.imageUploader = imageUploader;
    }

    public List<FavoriteResponse.Get> getUserFavorites(Long userId) {
        return productQuery.getAllFavoritesByUserId(userId);
    }

    // todo : user api
    public Slice<SupportResponse.Create> loadAllMyInquiries(Long userId, Long lastId) {
        return productQuery.getTenSupportsOfUserPageFromLastId(userId, lastId);
    }

    public Page<ProductResponse.GetSimple> loadProductPageResponse(
            Long categoryId,
            @Valid KurlyPagingRequest request
    ) {
        return productQuery.getProductsPageOfCategory(categoryId, request.toPageable());
    }

    public Page<ProductResponse.GetSimple> loadNewProductPageResponse(
            @Valid KurlyPagingRequest request
    ) {
        return productQuery.getNewProductPageResponse(request.toPageable());
    }

    public Page<ProductResponse.GetSimple> loadBestProductPageResponse(
            @Valid KurlyPagingRequest request
    ) {
        return productQuery.getBestProductPageResponse(request.toPageable());
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

    public void createProduct(
            MultipartFile image,
            @Valid ProductRequest.Create request
    ) {
        ProductDomain productDomain = productMapper.toProductDomain(request);
        imageUploader.upload(image).thenAccept(imageUrl ->
                productCommand.create(request.categoryId(), imageUrl, productDomain)
        );
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
            Long userId,
            @Valid ReviewRequest.Create request
    ) {
        Product product = productQuery.findProductByIdOrThrow(request.productId());
        product.validateSupportable();

        reviewCommand.create(userId, product.getId(), product.getName(), request.content(), request.isSecret());
        orderService.reviewOrderLine(request.orderId(), request.lineIndex());
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
