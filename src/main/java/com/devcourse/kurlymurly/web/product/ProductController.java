package com.devcourse.kurlymurly.web.product;

import com.devcourse.kurlymurly.module.product.service.ProductFacade;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.web.common.KurlyResponse;
import com.devcourse.kurlymurly.web.dto.CreateProduct;
import com.devcourse.kurlymurly.web.dto.SupportProduct;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductFacade productFacade;

    public ProductController(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @PostMapping
    @ResponseStatus(OK)
    public KurlyResponse<CreateProduct.Response> createProduct(
            @RequestBody CreateProduct.Request request
    ) {
        CreateProduct.Response response = productFacade.createProduct(request);
        return KurlyResponse.ok(response);
    }

    @PostMapping("/{id}/support")
    @ResponseStatus(OK)
    public KurlyResponse<Void> createProductSupport(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody SupportProduct.Request request
    ) {
        productFacade.createProductSupport(user.getId(), id, request);
        return KurlyResponse.noData();
    }

    @PostMapping("/{id}/favorite")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> favoriteProduct(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        productFacade.favoriteProduct(user.getId(), id);
        return KurlyResponse.noData();
    }

    @PutMapping("/supports/{supportId}")
    @ResponseStatus(OK)
    public KurlyResponse<Void> updateProductSupport(
            @AuthenticationPrincipal User user,
            @PathVariable("supportId") Long supportId,
            @RequestBody SupportProduct.Request request
    ) {
        productFacade.updateProductSupport(user.getId(), supportId, request);
        return KurlyResponse.noData();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public KurlyResponse<Void> deleteProduct(@PathVariable Long id) {
        productFacade.delete(id);
        return KurlyResponse.noData();
    }
}
