package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.module.product.domain.Product;
import com.devcourse.kurlymurly.module.product.domain.ProductDetail;
import com.devcourse.kurlymurly.module.product.domain.ProductRepository;
import com.devcourse.kurlymurly.module.product.domain.support.ProductSupport;
import com.devcourse.kurlymurly.module.product.domain.support.ProductSupportRepository;
import com.devcourse.kurlymurly.web.dto.product.CreateProduct;
import com.devcourse.kurlymurly.web.dto.product.support.SupportProduct;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class ProductCommand {
    private final ProductRepository productRepository;
    private final ProductSupportRepository productSupportRepository;

    public ProductCommand(ProductRepository productRepository, ProductSupportRepository productSupportRepository) {
        this.productRepository = productRepository;
        this.productSupportRepository = productSupportRepository;
    }

    public void create(CreateProduct.Request request) {
        Product product = toEntity(request);
        productRepository.save(product);
    }

    public void createSupport(Long userId, Long productId, String productName, SupportProduct.Request request) {
        ProductSupport support = new ProductSupport(userId, productId, productName, request.title(), request.content(), request.isSecret());
        productSupportRepository.save(support);
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
