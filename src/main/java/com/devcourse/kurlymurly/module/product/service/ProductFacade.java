package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.module.product.domain.Product;
import com.devcourse.kurlymurly.module.product.domain.category.Category;
import com.devcourse.kurlymurly.module.product.domain.category.service.CategoryRetrieve;
import com.devcourse.kurlymurly.web.dto.CreateProduct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductFacade {
    private final ProductCreate productCreate;
    private final ProductRetrieve productRetrieve;
    private final CategoryRetrieve categoryRetrieve;

    public ProductFacade(
            ProductCreate productCreate,
            ProductRetrieve productRetrieve,
            CategoryRetrieve categoryRetrieve
    ) {
        this.productCreate = productCreate;
        this.productRetrieve = productRetrieve;
        this.categoryRetrieve = categoryRetrieve;
    }

    public CreateProduct.Response createProduct(CreateProduct.Request request) {
        Category category = categoryRetrieve.findByIdOrThrow(request.categoryId());
        productCreate.create(request);
        return toResponse(category, request);
    }

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
