package com.devcourse.kurlymurly.auth;

import com.devcourse.kurlymurly.auth.jwt.JwtProvider;
import com.devcourse.kurlymurly.core.exception.KurlyBaseException;
import com.devcourse.kurlymurly.domain.user.User;
import com.devcourse.kurlymurly.domain.user.UserInfo;
import com.devcourse.kurlymurly.domain.user.UserRepository;
import com.devcourse.kurlymurly.domain.user.shipping.Shipping;
import com.devcourse.kurlymurly.domain.user.shipping.ShippingRepository;
import com.devcourse.kurlymurly.web.user.Join;
import com.devcourse.kurlymurly.web.user.Login;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.devcourse.kurlymurly.core.exception.ErrorCode.EXIST_SAME_EMAIL;
import static com.devcourse.kurlymurly.core.exception.ErrorCode.EXIST_SAME_ID;

@Service
@Transactional(readOnly = true)
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final ShippingRepository shippingRepository;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtProvider jwtProvider,
            AuthenticationManagerBuilder authenticationManagerBuilder,
            ShippingRepository shippingRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.shippingRepository = shippingRepository;
    }

    public Login.Response login(String loginId, String password) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginId, password);
        Authentication authorized = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String token = jwtProvider.createToken(authorized);

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

        Long savedId = userRepository.save(newUser).getId();

        Shipping shipping = new Shipping(savedId, request.roadAddress(), true);
        shippingRepository.save(shipping);
    }

    private User toUser(Join.Request request) {
        UserInfo userInfo = new UserInfo(request.birth(), request.recommender(), request.sex());

        return new User(
                request.name(),
                request.loginId(),
                passwordEncoder.encode(request.password()),
                request.email(),
                userInfo,
                request.phoneNumber()
        );
    }

    public Boolean checkId(String id) {
        return userRepository.existsByLoginId(id);
    }

    public Boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
