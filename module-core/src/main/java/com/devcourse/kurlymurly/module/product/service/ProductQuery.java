package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.product.domain.Product;
import com.devcourse.kurlymurly.module.product.domain.ProductRepository;
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

import static com.devcourse.kurlymurly.global.exception.ErrorCode.NEVER_WRITE_PRODUCT_SUPPORT;
import static com.devcourse.kurlymurly.global.exception.ErrorCode.PRODUCT_NOT_FOUND;

@Component
@Transactional(readOnly = true)
public class ProductQuery {
    private static final int DEFAULT_PAGE_SIZE = 10;

    private final ProductRepository productRepository;
    private final FavoriteRepository favoriteRepository;
    private final ProductSupportRepository productSupportRepository;

    public ProductQuery(ProductRepository productRepository, FavoriteRepository favoriteRepository, ProductSupportRepository productSupportRepository) {
        this.productRepository = productRepository;
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

    public Product findProductByIdOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> KurlyBaseException.withId(PRODUCT_NOT_FOUND, id));
    }

    public ProductSupport findSupportByIdOrThrow(Long id) {
        return productSupportRepository.findById(id)
                .orElseThrow(() -> KurlyBaseException.withId(NEVER_WRITE_PRODUCT_SUPPORT, id));
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
