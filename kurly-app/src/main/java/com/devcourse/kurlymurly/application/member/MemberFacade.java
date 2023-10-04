package com.devcourse.kurlymurly.application.member;

import com.devcourse.kurlymurly.domain.user.MemberCommand;
import com.devcourse.kurlymurly.domain.user.MemberQuery;
import com.devcourse.kurlymurly.domain.user.User;
import com.devcourse.kurlymurly.domain.user.cart.Cart;
import com.devcourse.kurlymurly.domain.user.payment.Payment;
import com.devcourse.kurlymurly.domain.user.shipping.Shipping;
import com.devcourse.kurlymurly.module.order.service.OrderService;
import com.devcourse.kurlymurly.web.product.ReviewResponse;
import com.devcourse.kurlymurly.web.user.GetAddress;
import com.devcourse.kurlymurly.web.user.RegisterPayment;
import com.devcourse.kurlymurly.web.user.UpdateUser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberFacade {
    private final MemberQuery memberQuery;
    private final MemberCommand memberCommand;
    private final OrderService orderService;
    private final MemberMapper memberMapper;

    public MemberFacade(
            MemberQuery memberQuery,
            MemberCommand memberCommand,
            OrderService orderService,
            MemberMapper memberMapper
    ) {
        this.memberQuery = memberQuery;
        this.memberCommand = memberCommand;
        this.orderService = orderService;
        this.memberMapper = memberMapper;
    }

    public List<ReviewResponse.Reviewable> getAllReviewableOrdersByUserId(Long userId) {
        return orderService.getAllReviewableOrdersByUserId(userId);
    }

    public void updateUserInfo(Long userId, UpdateUser.Request request) {
        User user = memberQuery.getUser(userId);

        memberCommand.updateUserInfo(request, user);
    }

    public void addAddress(Long userId, String address, boolean isDefault) {
        memberCommand.addAddress(userId, address, isDefault);
    }

    public List<GetAddress.Response> loadAllAddress(Long userId) {
        return memberQuery.getAllAddress(userId).stream()
                .map(memberMapper::toGetAddressResponse)
                .toList();
    }

    public void updateAddress(Long userId, Long addressId, String description, String receiver, String contact) {
        Shipping shipping = memberQuery.findAddress(userId, addressId);

        memberCommand.updateAddress(shipping, description, receiver, contact);
    }

    public void updateAddressInfo(Long userId, Long addressId, String receiver, String contact, String receiveArea,
                                  String entrancePassword, String alertTime) {
        Shipping shipping = memberQuery.findAddress(userId, addressId);

        memberCommand.updateAddressInfo(shipping, receiver, contact, receiveArea, entrancePassword, alertTime);
    }

    public void deleteAddress(Long userId, Long addressId) {
        Shipping shipping = memberQuery.findAddress(userId, addressId);

        memberCommand.deleteAddress(shipping);
    }

    public void addCredit(Long userId, RegisterPayment.CreditRequest request) {
        memberCommand.addCredit(userId, request);
    }

    public void addEasyPay(Long userId, RegisterPayment.EasyPayRequest request) {
        memberCommand.addEasyPay(userId, request);
    }

    public List<String> loadAllPayments(Long userId) {
        return memberQuery.getAllPayments(userId);
    }

    public void deletePayment(Long userId, Long paymentId) {
        Payment payment = memberQuery.getPayment(userId, paymentId);

        memberCommand.deletePayment(payment);
    }

    public void updatePaymentPassword(Long userId, String payPassword) {
        User user = memberQuery.getUser(userId);

        memberCommand.updatePaymentPassword(user, payPassword);
    }

    public void addCart(Long id, Long productId, int quantity) {
        memberCommand.addCart(id, productId, quantity);
    }

    public void removeCartItem(Long cartId) {
        Cart cart = memberQuery.getCart(cartId);

        memberCommand.removeCartItem(cart);
    }

    public void removeCartItemList(List<Long> cartIds) {
        List<Cart> carts = memberQuery.getAllCarts(cartIds);

        memberCommand.removeCartItemList(carts);
    }

    public void changeItemQuantity(Long cartId, boolean isIncrease) {
        Cart cart = memberQuery.getCart(cartId);

        cart.updateQuantity(isIncrease);
    }
}