package com.devcourse.kurlymurly.web.dto.product;

import com.devcourse.kurlymurly.module.product.domain.Product;

import static com.devcourse.kurlymurly.web.dto.product.ProductResponse.*;

public sealed interface ProductResponse permits Create, GetSimple {
    record Create(
            String productName,
            int price,
            String delivery,
            String StorageType,
            String saleUnit
    ) implements ProductResponse {
    }

    record GetSimple(
            String imageUrl,
            Product.Delivery delivery,
            String productName,
            String description,
            int price,
            Long reviewCount,
            boolean isKurlyOnly,
            Product.Status status
    ) implements ProductResponse {
    }
}
