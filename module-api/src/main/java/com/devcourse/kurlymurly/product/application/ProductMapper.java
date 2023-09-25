package com.devcourse.kurlymurly.product.application;

import com.devcourse.kurlymurly.module.product.domain.ProductDomain;
import com.devcourse.kurlymurly.module.product.domain.SupportDomain;
import com.devcourse.kurlymurly.module.product.domain.favorite.Favorite;
import com.devcourse.kurlymurly.module.product.domain.review.Review;
import com.devcourse.kurlymurly.module.product.domain.support.ProductSupport;
import com.devcourse.kurlymurly.web.dto.product.ProductRequest;
import com.devcourse.kurlymurly.web.dto.product.ProductResponse;
import com.devcourse.kurlymurly.web.dto.product.favorite.FavoriteResponse;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewResponse;
import com.devcourse.kurlymurly.web.dto.product.support.SupportRequest;
import com.devcourse.kurlymurly.web.dto.product.support.SupportResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDomain toProductDomain(ProductRequest.Create request) {
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

    public SupportDomain toSupportDomain(SupportRequest request) {
        return new SupportDomain(request.title(), request.content(), request.isSecret());
    }

    public FavoriteResponse.Get toFavoriteResponse(Favorite favorite) {
        return new FavoriteResponse.Get(
                favorite.getId(),
                favorite.getProductName(),
                favorite.getProductPrice()
        );
    }

    public SupportResponse.Create toSupportResponse(ProductSupport productSupport) {
        return new SupportResponse.Create(
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

    public ProductResponse.Create toCreateProductResponse(ProductRequest.Create request) {
        return new ProductResponse.Create(
                request.name(),
                request.price(),
                request.delivery().name(),
                request.storageType().name(),
                request.saleUnit()
        );
    }

    public ReviewResponse.Reviewed toReviewedResponse(Review review) {
        return new ReviewResponse.Reviewed(
                review.getId(),
                review.getProductId(),
                review.getProductName(),
                review.getContent(),
                review.isSecret(),
                review.getCreateAt(),
                review.getUpdatedAt()
        );
    }
}
