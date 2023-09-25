package com.devcourse.kurlymurly.web.dto.product.favorite;

import static com.devcourse.kurlymurly.web.dto.product.favorite.FavoriteResponse.Get;

public sealed interface FavoriteResponse permits Get {
    record Get(
            Long productId,
            String imageUrl,
            String productName,
            int price
    ) implements FavoriteResponse {
    }
}
