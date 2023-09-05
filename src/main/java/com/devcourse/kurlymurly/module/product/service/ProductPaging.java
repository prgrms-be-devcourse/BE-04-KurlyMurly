package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.module.product.domain.favorite.Favorite;
import com.devcourse.kurlymurly.module.product.domain.favorite.FavoriteRepository;
import com.devcourse.kurlymurly.module.product.domain.support.ProductSupport;
import com.devcourse.kurlymurly.module.product.domain.support.ProductSupportRepository;
import com.devcourse.kurlymurly.web.dto.product.favorite.GetFavorite;
import com.devcourse.kurlymurly.web.dto.product.support.SupportProduct;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
public class ProductPaging {
    private static final int DEFAULT_PAGE_SIZE = 10;

    private final FavoriteRepository favoriteRepository;
    private final ProductSupportRepository productSupportRepository;

    public ProductPaging(
            FavoriteRepository favoriteRepository,
            ProductSupportRepository productSupportRepository
    ) {
        this.favoriteRepository = favoriteRepository;
        this.productSupportRepository = productSupportRepository;
    }

    public List<GetFavorite.Response> getAllFavoritesByUserId(Long userId) {
        return favoriteRepository.findAllByUserId(userId).stream()
                .map(this::toFavoriteResponse)
                .toList();
    }


    public Slice<SupportProduct.Response> getTenSupportsOfUserPageFromLastId(Long userId, Long lastId) {
        return productSupportRepository.findTenByUserIdFromStartId(userId, lastId + DEFAULT_PAGE_SIZE)
                .map(this::toSupportResponse);
    }

    private GetFavorite.Response toFavoriteResponse(Favorite favorite) {
        return new GetFavorite.Response(
                favorite.getId(),
                favorite.getProductName(),
                favorite.getProductPrice()
        );
    }

    private SupportProduct.Response toSupportResponse(ProductSupport productSupport) {
        return new SupportProduct.Response(
                productSupport.getId(),
                productSupport.getProductId(),
                productSupport.getProductName(),
                productSupport.getTitle(),
                productSupport.getContent(),
                productSupport.isSecret(),
                productSupport.getStatus(),
                productSupport.getCreateAt()
        );
    }
}
