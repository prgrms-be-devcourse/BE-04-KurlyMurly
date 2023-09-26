package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.core.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.product.domain.Product;
import com.devcourse.kurlymurly.module.product.domain.ProductDomain;
import com.devcourse.kurlymurly.module.product.domain.ProductRepository;
import com.devcourse.kurlymurly.module.product.domain.SupportDomain;
import com.devcourse.kurlymurly.module.product.domain.favorite.Favorite;
import com.devcourse.kurlymurly.module.product.domain.favorite.FavoriteRepository;
import com.devcourse.kurlymurly.module.product.domain.support.ProductSupport;
import com.devcourse.kurlymurly.module.product.domain.support.ProductSupportRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.devcourse.kurlymurly.core.exception.ErrorCode.NEVER_FAVORITE;

@Component
@Transactional
public class ProductCommand {
    private final CategoryQuery categoryQuery;
    private final ProductQuery productQuery;
    private final ProductRepository productRepository;
    private final ProductSupportRepository productSupportRepository;
    private final FavoriteRepository favoriteRepository;

    public ProductCommand(
            CategoryQuery categoryQuery,
            ProductQuery productQuery,
            ProductRepository productRepository,
            ProductSupportRepository productSupportRepository,
            FavoriteRepository favoriteRepository
    ) {
        this.categoryQuery = categoryQuery;
        this.productQuery = productQuery;
        this.productRepository = productRepository;
        this.productSupportRepository = productSupportRepository;
        this.favoriteRepository = favoriteRepository;
    }

    public Product create(Long categoryId, String imageUrl, ProductDomain productDomain) {
        categoryQuery.validateIsExist(categoryId);

        Product product = productDomain.toEntity(categoryId, imageUrl);
        return productRepository.save(product);
    }

    public void createSupport(Long userId, Long productId, SupportDomain supportDomain) {
        Product product = productQuery.findProductByIdOrThrow(productId);
        product.validateSupportable();

        ProductSupport support = supportDomain.toSupportEntity(userId, productId, product.getName());
        productSupportRepository.save(support);
    }

    public void updateSupport(Long userId, Long supportId, SupportDomain supportDomain) {
        ProductSupport support = productQuery.findSupportByIdOrThrow(supportId);
        support.validateAuthor(userId);
        support.update(supportDomain.getTitle(), supportDomain.getContent(), supportDomain.isSecret());
    }

    public void soldOutProduct(Long id) {
        Product product = productQuery.findProductByIdOrThrow(id);
        product.soldOut();
    }

    public void deleteProduct(Long id) {
        Product product = productQuery.findProductByIdOrThrow(id);
        product.softDelete();
    }

    public void favoriteProduct(Long userId, Long productId) {
        Favorite favorite = favoriteRepository.findByUserIdAndProductId(userId, productId)
                .orElseGet(() -> createFavorite(userId, productId));

        favorite.activate();
    }

    private Favorite createFavorite(Long userId, Long productId) {
        Product product = productQuery.findProductByIdOrThrow(productId);
        Favorite favorite = new Favorite(userId, product);
        return favoriteRepository.save(favorite);
    }

    public void cancelFavorite(Long userId, Long productId) {
        Favorite favorite = favoriteRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new KurlyBaseException(NEVER_FAVORITE));

        favorite.softDelete();
    }
}
