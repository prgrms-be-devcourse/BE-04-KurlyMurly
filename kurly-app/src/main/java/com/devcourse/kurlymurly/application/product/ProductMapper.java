package com.devcourse.kurlymurly.application.product;

import com.devcourse.kurlymurly.web.product.ProductRequest;
import com.devcourse.kurlymurly.web.product.ProductResponse;
import com.devcourse.kurlymurly.web.product.ReviewResponse;
import com.devcourse.kurlymurly.web.product.SupportRequest;
import com.devcourse.kurlymurly.web.product.SupportResponse;
import com.devcourse.kurlymurly.module.product.domain.ProductDomain;
import com.devcourse.kurlymurly.module.product.domain.SupportDomain;
import com.devcourse.kurlymurly.module.product.domain.review.Review;
import com.devcourse.kurlymurly.module.product.domain.support.ProductSupport;
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

    public ProductResponse.Create toCreateProductResponse(ProductRequest.Create request) {
        return new ProductResponse.Create(
                request.name(),
                request.price(),
                request.delivery(),
                request.storageType(),
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
