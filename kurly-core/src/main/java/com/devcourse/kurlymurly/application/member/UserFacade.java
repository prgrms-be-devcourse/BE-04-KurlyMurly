package com.devcourse.kurlymurly.application.member;

import com.devcourse.kurlymurly.domain.service.UserCommand;
import com.devcourse.kurlymurly.domain.service.UserQuery;
import com.devcourse.kurlymurly.domain.service.ProductQuery;
import com.devcourse.kurlymurly.domain.user.User;
import com.devcourse.kurlymurly.domain.user.cart.Cart;
import com.devcourse.kurlymurly.domain.user.payment.Payment;
import com.devcourse.kurlymurly.domain.user.shipping.Shipping;
import com.devcourse.kurlymurly.module.order.service.OrderService;
import com.devcourse.kurlymurly.web.product.ReviewResponse;
import com.devcourse.kurlymurly.web.user.GetAddress;
import com.devcourse.kurlymurly.web.user.RegisterPayment;
import com.devcourse.kurlymurly.web.user.UpdateUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserFacade {
    private final UserQuery userQuery;
    private final UserCommand userCommand;
    private final OrderService orderService;
    private final UserMapper userMapper;
    private final ProductQuery productQuery;
    private final PasswordEncoder passwordEncoder;

    public UserFacade(
            UserQuery userQuery,
            UserCommand userCommand,
            OrderService orderService,
            UserMapper userMapper,
            ProductQuery productQuery,
            PasswordEncoder passwordEncoder
    ) {
        this.userQuery = userQuery;
        this.userCommand = userCommand;
        this.orderService = orderService;
        this.userMapper = userMapper;
        this.productQuery = productQuery;
        this.passwordEncoder = passwordEncoder;
    }

    public List<ReviewResponse.Reviewable> getAllReviewableOrdersByUserId(Long userId) {
        return orderService.getAllReviewableOrdersByUserId(userId);
    }

    public void updateUserInfo(Long userId, UpdateUser.Request request) {
        User user = userQuery.getUser(userId);
        String editPassword = passwordEncoder.encode(request.password());

        userCommand.updateUserInfo(request, editPassword, user);
    }

    public void addAddress(Long userId, String address, boolean isDefault) {
        userCommand.addAddress(userId, address, isDefault);
    }

    public List<GetAddress.Response> loadAllAddress(Long userId) {
        return userQuery.getAllAddress(userId).stream()
                .map(userMapper::toGetAddressResponse)
                .toList();
    }

    public void updateAddress(Long userId, Long addressId, String description, String receiver, String contact) {
        Shipping shipping = userQuery.findAddress(userId, addressId);

        userCommand.updateAddress(shipping, description, receiver, contact);
    }

    public void updateAddressInfo(Long userId, Long addressId, String receiver, String contact, String receiveArea,
                                  String entrancePassword, String alertTime) {
        Shipping shipping = userQuery.findAddress(userId, addressId);

        userCommand.updateAddressInfo(shipping, receiver, contact, receiveArea, entrancePassword, alertTime);
    }

    public void deleteAddress(Long userId, Long addressId) {
        Shipping shipping = userQuery.findAddress(userId, addressId);

        userCommand.deleteAddress(shipping);
    }

    public void addCredit(Long userId, RegisterPayment.CreditRequest request) {
        userCommand.addCredit(userId, request);
    }

    public void addEasyPay(Long userId, RegisterPayment.EasyPayRequest request) {
        userCommand.addEasyPay(userId, request);
    }

    public List<String> loadAllPayments(Long userId) {
        return userQuery.getAllPayments(userId);
    }

    public void deletePayment(Long userId, Long paymentId) {
        Payment payment = userQuery.getPayment(userId, paymentId);

        userCommand.deletePayment(payment);
    }

    public void updatePaymentPassword(Long userId, String payPassword) {
        User user = userQuery.getUser(userId);
        String encodedPassword = passwordEncoder.encode(payPassword);
        userCommand.updatePaymentPassword(user, encodedPassword);
    }

    public void addCart(Long id, Long productId, int quantity) {
        productQuery.validateOrderable(productId);
        userCommand.addCart(id, productId, quantity);
    }

    public void removeCartItem(Long cartId) {
        Cart cart = userQuery.getCart(cartId);

        userCommand.removeCartItem(cart);
    }

    public void removeCartItemList(List<Long> cartIds) {
        List<Cart> carts = userQuery.getAllCarts(cartIds);

        userCommand.removeCartItemList(carts);
    }

    public void changeItemQuantity(Long cartId, boolean isIncrease) {
        Cart cart = userQuery.getCart(cartId);

        cart.updateQuantity(isIncrease);
    }
}
