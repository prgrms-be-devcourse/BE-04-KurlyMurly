package com.devcourse.kurlymurly.domain.service;

import com.devcourse.kurlymurly.core.exception.KurlyBaseException;
import com.devcourse.kurlymurly.domain.user.User;
import com.devcourse.kurlymurly.domain.user.UserRepository;
import com.devcourse.kurlymurly.domain.user.cart.Cart;
import com.devcourse.kurlymurly.domain.user.cart.CartRepository;
import com.devcourse.kurlymurly.domain.user.payment.Payment;
import com.devcourse.kurlymurly.domain.user.payment.PaymentRepository;
import com.devcourse.kurlymurly.domain.user.shipping.Shipping;
import com.devcourse.kurlymurly.domain.user.shipping.ShippingRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.devcourse.kurlymurly.core.exception.ErrorCode.CART_NOT_FOUND;
import static com.devcourse.kurlymurly.core.exception.ErrorCode.NOT_EXISTS_USER;
import static com.devcourse.kurlymurly.core.exception.ErrorCode.NOT_FOUND_PAYMENT;
import static com.devcourse.kurlymurly.core.exception.ErrorCode.SHIPPING_NOT_FOUND;

@Component
@Transactional(readOnly = true)
public class MemberQuery {
    private final UserRepository userRepository;
    private final ShippingRepository shippingRepository;
    private final PaymentRepository paymentRepository;
    private final CartRepository cartRepository;

    public MemberQuery(
            UserRepository userRepository,
            ShippingRepository shippingRepository,
            PaymentRepository paymentRepository,
            CartRepository cartRepository
    ) {
        this.userRepository = userRepository;
        this.shippingRepository = shippingRepository;
        this.paymentRepository = paymentRepository;
        this.cartRepository = cartRepository;
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> KurlyBaseException.withId(NOT_EXISTS_USER, userId));
    }

    public List<Shipping> getAllAddress(Long userId) {
        return shippingRepository.findAllByUserId(userId);
    }

    public Shipping findAddress(Long userId, Long shippingId) {
        return shippingRepository.findByIdAndUserId(shippingId, userId)
                .orElseThrow(() -> KurlyBaseException.withId(SHIPPING_NOT_FOUND, shippingId));
    }

    public List<String> getAllPayments(Long userId) {
        return paymentRepository.findAllByUserId(userId);
    }

    public Payment getPayment(Long userId, Long paymentId) {
        return paymentRepository.findByUserIdAndId(userId, paymentId)
                .orElseThrow(() -> KurlyBaseException.withId(NOT_FOUND_PAYMENT, paymentId));
    }

    @Transactional
    public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new KurlyBaseException(CART_NOT_FOUND));
    }

    @Transactional
    public List<Cart> getAllCarts(List<Long> cartIds) {
        return cartRepository.findAllById(cartIds);
    }
}
