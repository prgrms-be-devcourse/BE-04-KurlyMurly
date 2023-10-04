package com.devcourse.kurlymurly.web.product;

import static com.devcourse.kurlymurly.web.product.FavoriteResponse.Get;

public sealed interface FavoriteResponse permits Get {
    record Get(
            Long productId,
            String imageUrl,
            String productName,
            int price
    ) implements FavoriteResponse {
    }
}
