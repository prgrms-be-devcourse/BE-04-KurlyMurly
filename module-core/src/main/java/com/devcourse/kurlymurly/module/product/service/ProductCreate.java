package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.module.product.domain.Product;
import com.devcourse.kurlymurly.module.product.domain.ProductDetail;
import com.devcourse.kurlymurly.module.product.domain.ProductRepository;
import com.devcourse.kurlymurly.web.dto.product.CreateProduct;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ProductCreate {
    private final ProductRepository productRepository;

    public ProductCreate(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void create(CreateProduct.Request request) {
        Product product = toEntity(request);
        productRepository.save(product);
    }

    private Product toEntity(CreateProduct.Request request) {
        ProductDetail detail = toDetail(request);
        return new Product(request.categoryId(),
                request.name(),
                request.description(),
                request.price(),
                request.delivery(),
                detail,
                request.isKurlyOnly());
    }

    private ProductDetail toDetail(CreateProduct.Request request) {
        return new ProductDetail(request.seller(),
                request.storageType(),
                request.saleUnit(),
                request.weight(),
                request.origin(),
                request.allergyInfo(),
                request.expirationInformation());
    }
}
