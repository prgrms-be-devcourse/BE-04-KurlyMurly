package com.devcourse.kurlymurly.module.auth;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.global.jwt.JwtTokenProvider;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.module.user.domain.UserInfo;
import com.devcourse.kurlymurly.module.user.domain.shipping.Shipping;
import com.devcourse.kurlymurly.module.user.domain.shipping.ShippingRepository;
import com.devcourse.kurlymurly.web.dto.user.Join;
import com.devcourse.kurlymurly.web.dto.user.Login;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.devcourse.kurlymurly.global.exception.ErrorCode.EXIST_SAME_EMAIL;
import static com.devcourse.kurlymurly.global.exception.ErrorCode.EXIST_SAME_ID;

@Service
@Transactional(readOnly = true)
public class AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final ShippingRepository shippingRepository;

    public AuthService(
            AuthRepository authRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider tokenProvider,
            AuthenticationManagerBuilder authenticationManagerBuilder,
            ShippingRepository shippingRepository
    ) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.shippingRepository = shippingRepository;
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

        if (checkId(request.loginId())) {
            throw new KurlyBaseException(EXIST_SAME_ID);
        }

        if (checkEmail(request.email())) {
            throw new KurlyBaseException(EXIST_SAME_EMAIL);
        }

        Long savedId = authRepository.save(newUser).getId();

        addAddress(savedId, request.roadAddress(), true);
    }

    @Transactional
    public void addAddress(Long userId, String address, boolean isDefault) {
        Shipping shipping = new Shipping(userId, address, isDefault);

        shippingRepository.save(shipping);
    }

    private User toUser(Join.Request request) {
        UserInfo userInfo = new UserInfo(request.birth(), request.recommender(), request.sex());

        return new User(request.name(), request.loginId(), passwordEncoder.encode(request.password()), request.email(), userInfo, request.phoneNumber());
    }

    public Boolean checkId(String id) {
        return authRepository.existsByLoginId(id);
    }

    public Boolean checkEmail(String email) {
        return authRepository.existsByEmail(email);
    }
}
