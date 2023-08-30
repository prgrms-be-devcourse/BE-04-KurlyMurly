package com.devcourse.kurlymurly.module.user.service;

import com.devcourse.kurlymurly.module.product.service.ProductFacade;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.module.user.domain.UserInfo;
import com.devcourse.kurlymurly.module.user.domain.UserRepository;
import com.devcourse.kurlymurly.module.user.domain.cart.Cart;
import com.devcourse.kurlymurly.module.user.domain.cart.CartRepository;
import com.devcourse.kurlymurly.web.dto.user.JoinUser;
import com.devcourse.kurlymurly.web.exception.ExistUserInfoException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private static final String EXIST_SAME_ID = "사용 불가능한 아이디 입니다.";
    private static final String EXIST_SAME_EMAIL = "사용 불가능한 이메일 입니다.";
    private static final String NOT_SAME_PASSWORD = "동일한 비밀번호를 입력";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductFacade productFacade;
    private final CartRepository cartRepository;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            ProductFacade productFacade,
            CartRepository cartRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.productFacade = productFacade;
        this.cartRepository = cartRepository;
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

        userRepository.save(newUser);
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
