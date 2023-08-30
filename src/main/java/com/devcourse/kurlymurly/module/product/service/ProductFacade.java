package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.module.product.domain.Product;
import com.devcourse.kurlymurly.module.product.domain.category.Category;
import com.devcourse.kurlymurly.module.product.domain.support.ProductSupport;
import com.devcourse.kurlymurly.web.dto.CreateProduct;
import com.devcourse.kurlymurly.web.dto.SupportProduct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductFacade {
    private final ProductCreate productCreate;
    private final ProductRetrieve productRetrieve;
    private final CategoryRetrieve categoryRetrieve;
    private final ProductSupportCreate productSupportCreate;
    private final ProductSupportRetrieve productSupportRetrieve;

    public ProductFacade(
            ProductCreate productCreate,
            ProductRetrieve productRetrieve,
            CategoryRetrieve categoryRetrieve,
            ProductSupportCreate productSupportCreate,
            ProductSupportRetrieve productSupportRetrieve
    ) {
        this.productCreate = productCreate;
        this.productRetrieve = productRetrieve;
        this.categoryRetrieve = categoryRetrieve;
        this.productSupportCreate = productSupportCreate;
        this.productSupportRetrieve = productSupportRetrieve;
    }

    @Transactional
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

        productSupportCreate.create(userId, productId, request);
    }

    @Transactional
    public void updateProductSupport(Long userId, Long supportId, SupportProduct.Request request) {
        ProductSupport support = productSupportRetrieve.findByIdOrThrow(supportId);
        support.validateAuthor(userId);
        support.update(request.title(), request.content(), request.isSecret());
    }

    @Transactional
    public void delete(Long id) {
        Product product = productRetrieve.findByIdOrThrow(id);
        product.softDelete();
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
