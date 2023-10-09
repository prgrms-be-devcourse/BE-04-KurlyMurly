package com.devcourse.kurlymurly.application.product;

import com.devcourse.kurlymurly.domain.product.ProductDomain;
import com.devcourse.kurlymurly.domain.product.SupportDomain;
import com.devcourse.kurlymurly.domain.product.review.Review;
import com.devcourse.kurlymurly.web.product.ProductRequest;
import com.devcourse.kurlymurly.web.product.ReviewResponse;
import com.devcourse.kurlymurly.web.product.SupportRequest;
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
