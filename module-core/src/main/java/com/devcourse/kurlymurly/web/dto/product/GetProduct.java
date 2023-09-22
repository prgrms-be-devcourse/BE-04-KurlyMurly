package com.devcourse.kurlymurly.web.dto.product;

import com.devcourse.kurlymurly.module.product.domain.Product;

import static com.devcourse.kurlymurly.web.dto.product.GetProduct.SimpleResponse;

public sealed interface GetProduct permits SimpleResponse {
    record SimpleResponse(
            String imageUrl,
            Product.Delivery delivery,
            String productName,
            String description,
            int price,
            Long reviewCount,
            boolean isKurlyOnly,
            Product.Status status
    ) implements GetProduct{
    }
}
