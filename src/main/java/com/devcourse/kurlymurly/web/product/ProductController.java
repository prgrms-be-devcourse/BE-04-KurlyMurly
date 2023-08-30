package com.devcourse.kurlymurly.web.product;

import com.devcourse.kurlymurly.module.product.service.ProductFacade;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.web.dto.CreateProduct;
import com.devcourse.kurlymurly.web.dto.SupportProduct;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<CreateProduct.Response> createProduct(
            @RequestBody CreateProduct.Request request
    ) {
        CreateProduct.Response response = productFacade.createProduct(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/support")
    public ResponseEntity<Void> createProductSupport(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody SupportProduct.Request request
    ) {
        productFacade.createProductSupport(user.getId(), id, request);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/supports/{supportId}")
    public ResponseEntity<Void> updateProductSupport(
            @AuthenticationPrincipal User user,
            @PathVariable("supportId") Long supportId,
            @RequestBody SupportProduct.Request request
    ) {
        productFacade.updateProductSupport(user.getId(), supportId, request);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productFacade.delete(id);
        return ResponseEntity.ok(null);
    }
}
