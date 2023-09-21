package com.devcourse.kurlymurly.module.user.service;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.global.jwt.JwtTokenProvider;
import com.devcourse.kurlymurly.module.order.service.OrderService;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.module.user.domain.UserInfo;
import com.devcourse.kurlymurly.module.user.domain.UserRepository;
import com.devcourse.kurlymurly.module.user.domain.cart.Cart;
import com.devcourse.kurlymurly.module.user.domain.cart.CartRepository;
import com.devcourse.kurlymurly.module.user.domain.payment.CreditInfo;
import com.devcourse.kurlymurly.module.user.domain.payment.Payment;
import com.devcourse.kurlymurly.module.user.domain.payment.PaymentRepository;
import com.devcourse.kurlymurly.module.user.domain.shipping.Shipping;
import com.devcourse.kurlymurly.module.user.domain.shipping.ShippingRepository;
import com.devcourse.kurlymurly.web.dto.payment.RegisterPayment;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewResponse;
import com.devcourse.kurlymurly.web.dto.user.Join;
import com.devcourse.kurlymurly.web.dto.user.Login;
import com.devcourse.kurlymurly.web.dto.user.UpdateUser;
import com.devcourse.kurlymurly.web.dto.user.shipping.GetAddress;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.devcourse.kurlymurly.global.exception.ErrorCode.CART_NOT_FOUND;
import static com.devcourse.kurlymurly.global.exception.ErrorCode.EXIST_SAME_EMAIL;
import static com.devcourse.kurlymurly.global.exception.ErrorCode.EXIST_SAME_ID;
import static com.devcourse.kurlymurly.global.exception.ErrorCode.NOT_CORRECT_PASSWORD;
import static com.devcourse.kurlymurly.global.exception.ErrorCode.NOT_EXISTS_USER;
import static com.devcourse.kurlymurly.global.exception.ErrorCode.NOT_FOUND_PAYMENT;
import static com.devcourse.kurlymurly.global.exception.ErrorCode.NOT_SAME_PASSWORD;
import static com.devcourse.kurlymurly.global.exception.ErrorCode.SHIPPING_NOT_FOUND;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final ShippingRepository shippingRepository;
    private final PaymentRepository paymentRepository;
    private final JwtTokenProvider tokenProvider;
    private final OrderService orderService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            CartRepository cartRepository,
            ShippingRepository shippingRepository,
            PaymentRepository paymentRepository,
            JwtTokenProvider tokenProvider,
            OrderService orderService,
            AuthenticationManagerBuilder authenticationManagerBuilder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cartRepository = cartRepository;
        this.shippingRepository = shippingRepository;
        this.paymentRepository = paymentRepository;
        this.tokenProvider = tokenProvider;
        this.orderService = orderService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    public List<ReviewResponse.Reviewable> getAllReviewableOrdersByUserId(Long userId) {
        return orderService.getAllReviewableOrdersByUserId(userId);
    }

    public Login.Response login(String loginId, String password) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginId, password);
        Authentication authorized = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String token = tokenProvider.createToken(authorized);

        return new Login.Response(token, 1800000L);
    }

    @Transactional
    public void join(Join.Request request) {
        User newUser = toUser(request);

        checkPassword(request.password(), request.checkPassword());

        if (checkId(request.loginId())) {
            throw new KurlyBaseException(EXIST_SAME_ID);
        }

        if (checkEmail(request.email())) {
            throw new KurlyBaseException(EXIST_SAME_EMAIL);
        }

        Long savedId = userRepository.save(newUser).getId();

        addAddress(savedId, request.roadAddress(), true);
    }

    private User toUser(Join.Request request) {
        UserInfo userInfo = new UserInfo(request.birth(), request.recommender(), request.sex());

        return new User(request.name(), request.loginId(), passwordEncoder.encode(request.password()), request.email(), userInfo, request.phoneNumber());
    }

    @Transactional
    public void findUpdateUser(Long userId, UpdateUser.Request request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new KurlyBaseException(NOT_EXISTS_USER));

        boolean notCorrectPassword = user.validatePassword(request.password(),passwordEncoder);

        if (notCorrectPassword) {
            throw new KurlyBaseException(NOT_CORRECT_PASSWORD);
        }

        updateUserInfo(request, user);
    }

    private void updateUserInfo(UpdateUser.Request request, User user) {
        String editPassword = passwordEncoder.encode(request.password());
        System.out.println(editPassword);
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
                .orElseThrow(() -> new KurlyBaseException(SHIPPING_NOT_FOUND));
    }

    @Transactional
    public void addCredit(Long userId, RegisterPayment.creditRequest request) {
        CreditInfo creditInfo = new CreditInfo(request.expiredDate(), request.password());
        Payment credit = new Payment(userId, request.payInfo(), creditInfo);

        paymentRepository.save(credit);
    }

    @Transactional
    public void addEasyPay(Long userId, RegisterPayment.easyPayRequest request) {
        Payment easyPay = new Payment(userId, request.payInfo());

        paymentRepository.save(easyPay);
    }

    public List<Payment> getPayments(Long userId) {
        List<Payment> paymentList = paymentRepository.findAllById(Collections.singleton(userId));

        if (paymentList.isEmpty()) {
            throw new KurlyBaseException(NOT_FOUND_PAYMENT);
        }

        return paymentList;
    }

    @Transactional
    public void deletePayment(Long userId, Long paymentId) {
        Payment payment = paymentRepository.findByUserIdAndId(userId, paymentId)
                .orElseThrow(() -> new KurlyBaseException(NOT_FOUND_PAYMENT));

        payment.deletePayment();
    }

    @Transactional
    public void updatePaymentPassword(Long userId, String payPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new KurlyBaseException(NOT_EXISTS_USER));

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

    private void checkPassword(String password, String checkPassword) {
        boolean isPasswordNotSame = !password.equals(checkPassword);

        if (isPasswordNotSame) {
            throw new KurlyBaseException(NOT_SAME_PASSWORD);
        }
    }

    public Boolean checkId(String id) {
        return userRepository.existsByLoginId(id);
    }

    public Boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
