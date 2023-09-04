package com.devcourse.kurlymurly.module.user.service;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.global.jwt.JwtTokenProvider;
import com.devcourse.kurlymurly.module.product.service.ProductFacade;
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
import com.devcourse.kurlymurly.web.dto.user.JoinUser;
import com.devcourse.kurlymurly.web.dto.user.LoginUser;
import com.devcourse.kurlymurly.web.dto.user.UpdateUser;
import com.devcourse.kurlymurly.web.exception.ExistUserInfoException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.devcourse.kurlymurly.global.exception.ErrorCode.NOT_CORRECT_PASSWORD;
import static com.devcourse.kurlymurly.global.exception.ErrorCode.NOT_EXISTS_USER;
import static com.devcourse.kurlymurly.global.exception.ErrorCode.NOT_FOUND_PAYMENT;

@Service
@Transactional(readOnly = true)
public class UserService {
    private static final String EXIST_SAME_ID = "사용 불가능한 아이디 입니다.";
    private static final String EXIST_SAME_EMAIL = "사용 불가능한 이메일 입니다.";
    private static final String NOT_SAME_PASSWORD = "동일한 비밀번호를 입력";
    private static final String FAIL_USER_LOGIN = "아이디, 비밀번호를 확인해주세요";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductFacade productFacade;
    private final CartRepository cartRepository;
    private final ShippingRepository shippingRepository;
    private final PaymentRepository paymentRepository;
    private final JwtTokenProvider tokenProvider;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            ProductFacade productFacade,
            CartRepository cartRepository,
            ShippingRepository shippingRepository,
            PaymentRepository paymentRepository,
            JwtTokenProvider tokenProvider
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.productFacade = productFacade;
        this.cartRepository = cartRepository;
        this.shippingRepository = shippingRepository;
        this.paymentRepository = paymentRepository;
        this.tokenProvider = tokenProvider;
    }

    public LoginUser.Response logIn(LoginUser.Request request) {
        String inputPassword = passwordEncoder.encode(request.password());

        User user = userRepository.findByLoginId(request.loginId())
                .filter(u -> passwordEncoder.matches(u.getPassword(), inputPassword))
                .orElseThrow(() -> new IllegalArgumentException(FAIL_USER_LOGIN));

        String token = tokenProvider.createToken(user.getId(), user.getRole().name());

        return new LoginUser.Response(user.getRole().name(), token);
    }

    @Transactional
    public void join(JoinUser.Request request) {
        User newUser = convertToUser(request);

        checkPassword(request.password(), request.checkPassword());

        if (checkId(request.loginId())) {
            throw new ExistUserInfoException(EXIST_SAME_ID);
        }

        if (checkEmail(request.email())) {
            throw new ExistUserInfoException(EXIST_SAME_EMAIL);
        }

        Long savedId = userRepository.save(newUser).getId();

        addAddress(savedId, request.roadAddress(), true);
    }

    public void findUpdateUser(Long userId, UpdateUser.Request request) {
        String inputPassword = passwordEncoder.encode(request.password());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new KurlyBaseException(NOT_EXISTS_USER));

        boolean notCorrectPassword = !passwordEncoder.matches(user.getPassword(), inputPassword);

        if (notCorrectPassword) {
            throw new KurlyBaseException(NOT_CORRECT_PASSWORD);
        }

        updateUserInfo(request, user);
    }

    private void updateUserInfo(UpdateUser.Request request, User user) {
        String editPassword = passwordEncoder.encode(request.password());
        System.out.println(editPassword);
        user.update(request.name(), editPassword, request.email(), request.sex(), request.bitrh(), request.phoneNumber());
    }

    @Transactional
    public void addAddress(Long userId, String address, boolean isDefault) {
        Shipping shipping = new Shipping(userId, address, isDefault);

        shippingRepository.save(shipping);
    }

    @Transactional
    public void addCredit(Long userId, RegisterPayment.creditRequest request) {
        CreditInfo creditInfo = new CreditInfo(request.expiredDate(), request.password());
        Payment credit = new Payment(userId, request.payInfo(), creditInfo);

        paymentRepository.save(credit);
    }

    public List<Payment> getPayments(Long userId) {
        List<Payment> paymentList = paymentRepository.findAllById(Collections.singleton(userId));

        if (paymentList.isEmpty()) {
            throw new KurlyBaseException(NOT_FOUND_PAYMENT);
        }

        return paymentList;
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

    public void deletePayment(Long userId,Long paymentId) {
        Payment payment = paymentRepository.findByUserIdAndId(userId,paymentId)
                .orElseThrow(() -> new KurlyBaseException(NOT_FOUND_PAYMENT));

        payment.deletePayment();
    }

    @Transactional
    public void addCart(Long id, Long productId, int quantity) {
        productFacade.validateOrderable(productId);
        Cart cart = new Cart(id, productId, quantity);
        cartRepository.save(cart);
    }

    private void checkPassword(String password, String checkPassword) {
        boolean isPasswordNotSame = !password.equals(checkPassword);

        if (isPasswordNotSame) {
            throw new IllegalArgumentException(NOT_SAME_PASSWORD);
        }
    }

    public Boolean checkId(String id) {
        return userRepository.existsByLoginId(id);
    }

    public Boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private User convertToUser(JoinUser.Request request) {
        UserInfo userInfo = new UserInfo(request.birth(), request.recommender(), request.sex());

        return new User(request.name(), request.loginId(), passwordEncoder.encode(request.password()), request.email(), userInfo, request.phoneNumber());
    }
}
