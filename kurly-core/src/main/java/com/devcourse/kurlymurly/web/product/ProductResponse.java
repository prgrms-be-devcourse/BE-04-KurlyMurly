package com.devcourse.kurlymurly.web.product;

import static com.devcourse.kurlymurly.web.product.ProductResponse.Create;
import static com.devcourse.kurlymurly.web.product.ProductResponse.GetSimple;

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
            String delivery,
            String productName,
            String description,
            int price,
            Long reviewCount,
            boolean isKurlyOnly,
            String status
    ) implements ProductResponse {
    }
}
