package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.order.domain.Order;
import com.devcourse.kurlymurly.module.order.service.OrderService;
import com.devcourse.kurlymurly.module.product.domain.Product;
import com.devcourse.kurlymurly.module.product.domain.category.Category;
import com.devcourse.kurlymurly.module.product.domain.favorite.Favorite;
import com.devcourse.kurlymurly.module.product.domain.favorite.FavoriteRepository;
import com.devcourse.kurlymurly.module.product.domain.support.ProductSupport;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.web.dto.ListPagingResponse;
import com.devcourse.kurlymurly.web.dto.product.CreateProduct;
import com.devcourse.kurlymurly.web.dto.product.favorite.GetFavorite;
import com.devcourse.kurlymurly.web.dto.product.review.CreateReview;
import com.devcourse.kurlymurly.web.dto.product.support.SupportProduct;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.devcourse.kurlymurly.global.exception.ErrorCode.NEVER_FAVORITE;

@Service
@Transactional(readOnly = true)
public class ProductFacade {
    private final ProductQuery productQuery;
    private final ProductCommand productCommand;
    private final ReviewCommand reviewCommand;
    private final CategoryQuery categoryQuery;
    private final FavoriteRepository favoriteRepository;
    private final OrderService orderService;

    public ProductFacade(
            ProductQuery productQuery,
            ProductCommand productCommand,
            ReviewCommand reviewCommand,
            CategoryQuery categoryQuery,
            FavoriteRepository favoriteRepository,
            OrderService orderService
    ) {
        this.productQuery = productQuery;
        this.productCommand = productCommand;
        this.reviewCommand = reviewCommand;
        this.categoryQuery = categoryQuery;
        this.favoriteRepository = favoriteRepository;
        this.orderService = orderService;
    }

    public ListPagingResponse<GetFavorite.Response> getUserFavorites(Long userId) {
        List<GetFavorite.Response> responses = productQuery.getAllFavoritesByUserId(userId);
        return new ListPagingResponse<>(responses);
    }

    // todo : user api
    public Slice<SupportProduct.Response> getAllMySupports(Long userId, Long lastId) {
        return productQuery.getTenSupportsOfUserPageFromLastId(userId, lastId);
    }

    @Transactional
    public CreateProduct.Response createProduct(CreateProduct.Request request) {
        Category category = categoryQuery.findByIdOrThrow(request.categoryId());
        productCommand.create(request);
        return toResponse(category, request);
    }

    public void validateOrderable(Long id) {
        Product product = productQuery.findProductByIdOrThrow(id);
        product.validateOrderable();
    }

    @Transactional
    public void createProductSupport(Long userId, Long productId, SupportProduct.Request request) {
        Product product = productQuery.findProductByIdOrThrow(productId);
        product.validateSupportable();

        productCommand.createSupport(userId, productId, product.getName(), request);
    }

    @Transactional
    public void updateProductSupport(Long userId, Long supportId, SupportProduct.Request request) {
        ProductSupport support = productQuery.findSupportByIdOrThrow(supportId);
        support.validateAuthor(userId);
        support.update(request.title(), request.content(), request.isSecret());
    }

    @Transactional
    public void soldOutProduct(Long id) {
        Product product = productQuery.findProductByIdOrThrow(id);
        product.soldOut();
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productQuery.findProductByIdOrThrow(id);
        product.softDelete();
    }

    @Transactional
    public void registerReview(User user, CreateReview.Request request) {
        Product product = productQuery.findProductByIdOrThrow(request.productId());
        product.validateSupportable();

        Order order = orderService.findByIdOrThrow(request.orderId());
        order.markReviewedOrder(product.getId());

        reviewCommand.create(user, product, request.content(), request.isSecret());
    }

    @Transactional
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
