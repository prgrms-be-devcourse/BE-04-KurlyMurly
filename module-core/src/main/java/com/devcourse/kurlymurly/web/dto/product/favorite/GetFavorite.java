package com.devcourse.kurlymurly.web.dto.product.favorite;

import static com.devcourse.kurlymurly.web.dto.product.favorite.GetFavorite.Response;

public sealed interface GetFavorite permits Response {
    record Response(Long id, String name, int price) implements GetFavorite{
    }
}
