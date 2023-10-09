package com.devcourse.kurlymurly.web.product;

import static com.devcourse.kurlymurly.web.product.ProductResponse.GetSimple;

public sealed interface ProductResponse permits GetSimple {
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
