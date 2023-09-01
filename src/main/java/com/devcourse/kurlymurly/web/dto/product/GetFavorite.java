package com.devcourse.kurlymurly.web.dto.product;

import static com.devcourse.kurlymurly.web.dto.product.GetFavorite.*;

public sealed interface GetFavorite permits Response {
    record Response(Long id, String name, int price) implements GetFavorite{
    }
}
