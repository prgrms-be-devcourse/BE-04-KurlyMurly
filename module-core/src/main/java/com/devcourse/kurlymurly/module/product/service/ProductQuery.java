package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.web.product.FavoriteResponse;
import com.devcourse.kurlymurly.web.product.ProductResponse;
import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.product.domain.Product;
import com.devcourse.kurlymurly.module.product.domain.ProductRepository;
import com.devcourse.kurlymurly.module.product.domain.favorite.FavoriteRepository;
import com.devcourse.kurlymurly.module.product.domain.support.ProductSupport;
import com.devcourse.kurlymurly.module.product.domain.support.ProductSupportRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<ProductResponse.GetSimple> getProductsPageOfCategory(Long categoryId, Pageable pageable) {
        return productRepository.loadProductsByCategory(categoryId, pageable);
    }

    public List<FavoriteResponse.Get> getAllFavoritesByUserId(Long userId) {
        return favoriteRepository.findAllByUserId(userId);
    }

    public Slice<ProductSupport> getTenSupportsOfUserPageFromLastId(Long userId, Long lastId) {
        return productSupportRepository.findTenByUserIdFromStartId(userId, lastId + DEFAULT_PAGE_SIZE);
    }

    public Product findProductByIdOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> KurlyBaseException.withId(PRODUCT_NOT_FOUND, id));
    }

    public ProductSupport findSupportByIdOrThrow(Long id) {
        return productSupportRepository.findById(id)
                .orElseThrow(() -> KurlyBaseException.withId(NEVER_WRITE_PRODUCT_SUPPORT, id));
    }

    // todo: user facade에서 사용
    public void validateOrderable(Long id) {
        Product product = findProductByIdOrThrow(id);
        product.validateOrderable();
    }
}
