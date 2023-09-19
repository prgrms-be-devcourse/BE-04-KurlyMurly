package com.devcourse.kurlymurly.product.application;

import com.devcourse.kurlymurly.module.product.domain.ProductDomain;
import com.devcourse.kurlymurly.module.product.domain.SupportDomain;
import com.devcourse.kurlymurly.module.product.domain.favorite.Favorite;
import com.devcourse.kurlymurly.module.product.domain.review.Review;
import com.devcourse.kurlymurly.module.product.domain.support.ProductSupport;
import com.devcourse.kurlymurly.web.dto.product.CreateProduct;
import com.devcourse.kurlymurly.web.dto.product.favorite.GetFavorite;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewResponse;
import com.devcourse.kurlymurly.web.dto.product.support.SupportProduct;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDomain toProductDomain(CreateProduct.Request request) {
        return new ProductDomain(
                request.name(),
                request.description(),
                request.price(),
                request.delivery(),
                request.isKurlyOnly(),
                request.seller(),
                request.storageType(),
                request.saleUnit(),
                request.weight(),
                request.origin(),
                request.allergyInfo(),
                request.expirationInformation()
        );
    }

    public SupportDomain toSupportDomain(SupportProduct.Request request) {
        return new SupportDomain(request.title(), request.content(), request.isSecret());
    }

    public GetFavorite.Response toFavoriteResponse(Favorite favorite) {
        return new GetFavorite.Response(
                favorite.getId(),
                favorite.getProductName(),
                favorite.getProductPrice()
        );
    }

    public SupportProduct.Response toSupportResponse(ProductSupport productSupport) {
        return new SupportProduct.Response(
                productSupport.getId(),
                productSupport.getProductId(),
                productSupport.getProductName(),
                productSupport.getTitle(),
                productSupport.getContent(),
                productSupport.isSecret(),
                productSupport.getStatus(),
                productSupport.getCreateAt()
        );
    }

    public CreateProduct.Response toCreateProductResponse(CreateProduct.Request request) {
        return new CreateProduct.Response(
                request.name(),
                request.price(),
                request.delivery().name(),
                request.storageType().name(),
                request.saleUnit()
        );
    }

    public ReviewResponse.ReviewOfProduct toReviewOfProductResponse(Review review) {
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

    public ReviewResponse.Reviewed toReviewedResponse(Review review) {
        return new ReviewResponse.Reviewed(
                review.getProductId(),
                review.getProductName(),
                review.getContent(),
                review.isSecret(),
                review.getCreateAt(),
                review.getUpdatedAt()
        );
    }
}
