package com.devcourse.kurlymurly.module.user.domain.service;

import com.devcourse.kurlymurly.module.product.service.ProductFacade;
import com.devcourse.kurlymurly.module.user.domain.cart.Cart;
import com.devcourse.kurlymurly.module.user.domain.cart.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final ProductFacade productFacade;
    private final CartRepository cartRepository;

    public UserService(ProductFacade productFacade, CartRepository cartRepository) {
        this.productFacade = productFacade;
        this.cartRepository = cartRepository;
    }

    @Transactional
    public void addCart(Long id, Long productId, int quantity) {
        productFacade.validateOrderable(productId);
        Cart cart = new Cart(id, productId, quantity);
        cartRepository.save(cart);
    }
}
