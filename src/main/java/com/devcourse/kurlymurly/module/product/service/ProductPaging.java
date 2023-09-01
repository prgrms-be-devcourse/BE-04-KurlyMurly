package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.module.product.domain.favorite.Favorite;
import com.devcourse.kurlymurly.module.product.domain.favorite.FavoriteRepository;
import com.devcourse.kurlymurly.web.dto.product.GetFavorite;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
public class ProductPaging {
    private final FavoriteRepository favoriteRepository;

    public ProductPaging(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public List<GetFavorite.Response> getAllFavoritesByUserId(Long userId) {
        return favoriteRepository.findAllByUserId(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    private GetFavorite.Response toResponse(Favorite favorite) {
        return new GetFavorite.Response(
                favorite.getId(),
                favorite.getProductName(),
                favorite.getProductPrice()
        );
    }
}
