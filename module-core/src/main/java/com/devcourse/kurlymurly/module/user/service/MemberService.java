package com.devcourse.kurlymurly.module.user.service;

import com.devcourse.kurlymurly.core.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.order.service.OrderService;
import com.devcourse.kurlymurly.module.user.domain.UserRepository;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.module.user.domain.cart.Cart;
import com.devcourse.kurlymurly.module.user.domain.cart.CartRepository;
import com.devcourse.kurlymurly.module.user.domain.payment.CreditInfo;
import com.devcourse.kurlymurly.module.user.domain.payment.Payment;
import com.devcourse.kurlymurly.module.user.domain.payment.PaymentRepository;
import com.devcourse.kurlymurly.module.user.domain.shipping.Shipping;
import com.devcourse.kurlymurly.module.user.domain.shipping.ShippingRepository;
import com.devcourse.kurlymurly.web.user.UpdateUser;
import com.devcourse.kurlymurly.web.user.RegisterPayment;
import com.devcourse.kurlymurly.web.user.GetAddress;
import com.devcourse.kurlymurly.web.product.ReviewResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.devcourse.kurlymurly.core.exception.ErrorCode.CART_NOT_FOUND;
import static com.devcourse.kurlymurly.core.exception.ErrorCode.NOT_EXISTS_USER;
import static com.devcourse.kurlymurly.core.exception.ErrorCode.NOT_FOUND_PAYMENT;
import static com.devcourse.kurlymurly.core.exception.ErrorCode.SHIPPING_NOT_FOUND;

@Service
@Transactional(readOnly = true)
public class MemberService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final ShippingRepository shippingRepository;
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    public MemberService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            CartRepository cartRepository,
            ShippingRepository shippingRepository,
            PaymentRepository paymentRepository,
            OrderService orderService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cartRepository = cartRepository;
        this.shippingRepository = shippingRepository;
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
    }

    public List<ReviewResponse.Reviewable> getAllReviewableOrdersByUserId(Long userId) {
        return orderService.getAllReviewableOrdersByUserId(userId);
    }

    @Transactional
    public void findUpdateUser(Long userId, UpdateUser.Request request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> KurlyBaseException.withId(NOT_EXISTS_USER, userId));

        updateUserInfo(request, user);
    }

    private void updateUserInfo(UpdateUser.Request request, User user) {
        String editPassword = passwordEncoder.encode(request.password());
        user.update(request.name(), editPassword, request.email(), request.sex(), request.birth(), request.phoneNumber());
    }

    @Transactional
    public void addAddress(Long userId, String address, boolean isDefault) {
        Shipping shipping = new Shipping(userId, address, isDefault);

        shippingRepository.save(shipping);
    }

    public List<GetAddress.Response> getAddress(Long userId) {
        return shippingRepository.findAllByUserId(userId).stream()
                .map(this::toGetAddressResponse)
                .toList();
    }

    private GetAddress.Response toGetAddressResponse(Shipping shipping) {
        return new GetAddress.Response(shipping.isDefault(), shipping.getAddress().isExpress()
                , shipping.getAddress().getDescribedAddress(), shipping.getInfo().getReceiver()
                , shipping.getInfo().getContact());
    }

    @Transactional
    public void updateAddress(Long userId, Long addressId, String description, String receiver, String contact) {
        Shipping shipping = findAddress(userId, addressId);

        shipping.update(description, receiver, contact);
    }

    @Transactional
    public void updateAddressInfo(Long userId, Long addressId, String receiver, String contact, String receiveArea,
                                  String entrancePassword, String alertTime) {
        Shipping shipping = findAddress(userId, addressId);

        shipping.updateInfo(receiver, contact, receiveArea, entrancePassword, alertTime);
    }

    @Transactional
    public void deleteAddress(Long userId, Long addressId) {
        Shipping shipping = findAddress(userId, addressId);

        shippingRepository.delete(shipping);
    }

    private Shipping findAddress(Long userId, Long shippingId) {
        return shippingRepository.findByIdAndUserId(shippingId, userId)
                .orElseThrow(() -> KurlyBaseException.withId(SHIPPING_NOT_FOUND, shippingId));
    }

    @Transactional
    public void addCredit(Long userId, RegisterPayment.CreditRequest request) {
        CreditInfo creditInfo = new CreditInfo(request.expiredDate(), request.password());
        Payment credit = new Payment(userId, request.payInfo(), creditInfo);

        paymentRepository.save(credit);
    }

    @Transactional
    public void addEasyPay(Long userId, RegisterPayment.EasyPayRequest request) {
        Payment easyPay = new Payment(userId, request.payInfo());

        paymentRepository.save(easyPay);
    }

    public List<String> getPayments(Long userId) {
        return paymentRepository.findAllByUserId(userId);
    }

    @Transactional
    public void deletePayment(Long userId, Long paymentId) {
        Payment payment = paymentRepository.findByUserIdAndId(userId, paymentId)
                .orElseThrow(() -> KurlyBaseException.withId(NOT_FOUND_PAYMENT, paymentId));

        payment.deletePayment();
    }

    @Transactional
    public void updatePaymentPassword(Long userId, String payPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> KurlyBaseException.withId(NOT_EXISTS_USER, userId));

        String encodedPassword = passwordEncoder.encode(payPassword);
        user.updatePayPassword(encodedPassword);
    }

    @Transactional
    public void addCart(Long id, Long productId, int quantity) {
//        productFacade.validateOrderable(productId);
        Cart cart = new Cart(id, productId, quantity);
        cartRepository.save(cart);
    }

    @Transactional
    public void removeCartItem(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new KurlyBaseException(CART_NOT_FOUND));

        cartRepository.delete(cart);
    }

    @Transactional
    public void removeCartItemList(List<Long> cartIds) {
        List<Cart> carts = cartRepository.findAllById(cartIds);
        cartRepository.deleteAllInBatch(carts);
    }

    @Transactional
    public void changeItemQuantity(Long cartId, boolean isIncrease) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new KurlyBaseException(CART_NOT_FOUND));

        cart.updateQuantity(isIncrease);
    }
}
