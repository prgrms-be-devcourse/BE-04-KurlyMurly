package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.product.domain.Product;
import com.devcourse.kurlymurly.module.product.domain.category.Category;
import com.devcourse.kurlymurly.module.product.domain.favorite.Favorite;
import com.devcourse.kurlymurly.module.product.domain.favorite.FavoriteRepository;
import com.devcourse.kurlymurly.module.product.domain.support.ProductSupport;
import com.devcourse.kurlymurly.web.dto.product.CreateProduct;
import com.devcourse.kurlymurly.web.dto.product.GetFavorite;
import com.devcourse.kurlymurly.web.dto.ListPagingResponse;
import com.devcourse.kurlymurly.web.dto.product.SupportProduct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.devcourse.kurlymurly.global.exception.ErrorCode.NEVER_FAVORITE;

@Service
@Transactional(readOnly = true)
public class ProductFacade {
    private final ProductCreate productCreate;
    private final ProductRetrieve productRetrieve;
    private final CategoryRetrieve categoryRetrieve;
    private final ProductSupportCreate productSupportCreate;
    private final ProductSupportRetrieve productSupportRetrieve;
    private final FavoriteRepository favoriteRepository;
    private final ProductPaging productPaging;

    public ProductFacade(
            ProductCreate productCreate,
            ProductRetrieve productRetrieve,
            CategoryRetrieve categoryRetrieve,
            ProductSupportCreate productSupportCreate,
            ProductSupportRetrieve productSupportRetrieve,
            FavoriteRepository favoriteRepository,
            ProductPaging productPaging
    ) {
        this.productCreate = productCreate;
        this.productRetrieve = productRetrieve;
        this.categoryRetrieve = categoryRetrieve;
        this.productSupportCreate = productSupportCreate;
        this.productSupportRetrieve = productSupportRetrieve;
        this.favoriteRepository = favoriteRepository;
        this.productPaging = productPaging;
    }

    public ListPagingResponse<GetFavorite.Response> getUserFavorites(Long userId) {
        List<GetFavorite.Response> responses = productPaging.getAllFavoritesByUserId(userId);
        return new ListPagingResponse<>(responses);
    }

    @Transactional // todo: 관리자 API
    public CreateProduct.Response createProduct(CreateProduct.Request request) {
        Category category = categoryRetrieve.findByIdOrThrow(request.categoryId());
        productCreate.create(request);
        return toResponse(category, request);
    }

    public void validateOrderable(Long id) {
        Product product = productRetrieve.findByIdOrThrow(id);
        product.validateOrderable();
    }

    @Transactional
    public void createProductSupport(Long userId, Long productId, SupportProduct.Request request) {
        Product product = productRetrieve.findByIdOrThrow(productId);
        product.validateSupportable();

        productSupportCreate.create(userId, productId, product.getName(), request);
    }

    @Transactional
    public void updateProductSupport(Long userId, Long supportId, SupportProduct.Request request) {
        ProductSupport support = productSupportRetrieve.findByIdOrThrow(supportId);
        support.validateAuthor(userId);
        support.update(request.title(), request.content(), request.isSecret());
    }

    @Transactional // todo: 관리자 API
    public void soldOutProduct(Long id) {
        Product product = productRetrieve.findByIdOrThrow(id);
        product.soldOut();
    }

    @Transactional // todo: 관리자 API
    public void delete(Long id) {
        Product product = productRetrieve.findByIdOrThrow(id);
        product.softDelete();
    }

    @Transactional
    public void favoriteProduct(Long userId, Long productId) {
        Favorite favorite = favoriteRepository.findByUserIdAndProductId(userId, productId)
                .orElseGet(() -> createNewFavorite(userId, productId));

        favorite.activate();
    }

    private Favorite createNewFavorite(Long userId, Long productId) {
        Product product = productRetrieve.findByIdOrThrow(productId);
        Favorite favorite = new Favorite(userId, product);
        return favoriteRepository.save(favorite);
    }

    @Transactional
    public void cancelFavorite(Long userId, Long productId) {
        Favorite favorite = favoriteRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new KurlyBaseException(NEVER_FAVORITE));
        favorite.softDelete();
    }

    private CreateProduct.Response toResponse(Category category, CreateProduct.Request request) {
        return new CreateProduct.Response(
                category.getName(),
                category.getSubCategory(),
                request.name(),
                request.price(),
                request.delivery().name(),
                request.storageType().name(),
                request.saleUnit()
        );
    }
}
