package com.devcourse.kurlymurly.web.product;

import com.devcourse.kurlymurly.module.product.service.ProductFacade;
import com.devcourse.kurlymurly.web.dto.CreateProduct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductFacade productFacade;

    public ProductController(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @PostMapping
    public ResponseEntity<CreateProduct.Response> createProduct(CreateProduct.Request request) {
        CreateProduct.Response response = productFacade.createProduct(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productFacade.delete(id);
        return ResponseEntity.ok(null);
    }
}
