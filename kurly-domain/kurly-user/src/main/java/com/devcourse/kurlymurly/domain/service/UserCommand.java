package com.devcourse.kurlymurly.domain.service;

import com.devcourse.kurlymurly.domain.user.User;
import com.devcourse.kurlymurly.domain.user.cart.Cart;
import com.devcourse.kurlymurly.domain.user.cart.CartRepository;
import com.devcourse.kurlymurly.domain.user.payment.CreditInfo;
import com.devcourse.kurlymurly.domain.user.payment.Payment;
import com.devcourse.kurlymurly.domain.user.payment.PaymentRepository;
import com.devcourse.kurlymurly.domain.user.shipping.Shipping;
import com.devcourse.kurlymurly.domain.user.shipping.ShippingRepository;
import com.devcourse.kurlymurly.web.user.RegisterPayment;
import com.devcourse.kurlymurly.web.user.UpdateUser;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Transactional
public class UserCommand {
    private final PaymentRepository paymentRepository;
    private final ShippingRepository shippingRepository;
    private final CartRepository cartRepository;

    public UserCommand(
            PaymentRepository paymentRepository,
            ShippingRepository shippingRepository,
            CartRepository cartRepository
    ) {
        this.paymentRepository = paymentRepository;
        this.shippingRepository = shippingRepository;
        this.cartRepository = cartRepository;
    }

    public void updateUserInfo(UpdateUser.Request request, String editPassword, User user) {
        user.update(request.name(), editPassword, request.email(), request.sex(), request.birth(), request.phoneNumber());
    }

    public void addAddress(Long userId, String address, boolean isDefault) {
        Shipping shipping = new Shipping(userId, address, isDefault);

        shippingRepository.save(shipping);
    }

    public void updateAddress(Shipping shipping, String description, String receiver, String contact) {
        shipping.update(description, receiver, contact);
    }

    public void updateAddressInfo(Shipping shipping, String receiver, String contact, String receiveArea,
                                  String entrancePassword, String alertTime) {
        shipping.updateInfo(receiver, contact, receiveArea, entrancePassword, alertTime);
    }

    public void deleteAddress(Shipping shipping) {
        shippingRepository.delete(shipping);
    }

    public void addCredit(Long userId, RegisterPayment.CreditRequest request) {
        CreditInfo creditInfo = new CreditInfo(request.expiredDate(), request.password());
        Payment credit = new Payment(userId, request.payInfo(), creditInfo);

        paymentRepository.save(credit);
    }

    public void addEasyPay(Long userId, RegisterPayment.EasyPayRequest request) {
        Payment easyPay = new Payment(userId, request.payInfo());

        paymentRepository.save(easyPay);
    }

    public void deletePayment(Payment payment) {
        payment.deletePayment();
    }

    public void updatePaymentPassword(User user, String encodedPassword) {
        user.updatePayPassword(encodedPassword);
    }

    public void addCart(Long id, Long productId, int quantity) {
        Cart cart = new Cart(id, productId, quantity);
        cartRepository.save(cart);
    }

    public void removeCartItem(Cart cart) {
        cartRepository.delete(cart);
    }

    public void removeCartItemList(List<Cart> cart) {
        cartRepository.deleteAllInBatch(cart);
    }

    public void updateQuantity(Cart cart, boolean isIncrease) {
        cart.updateQuantity(isIncrease);
    }
}
