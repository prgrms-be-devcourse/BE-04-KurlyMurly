package com.devcourse.kurlymurly.web.user;

import com.devcourse.kurlymurly.global.jwt.JwtTokenProvider;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.module.user.service.UserService;
import com.devcourse.kurlymurly.web.common.KurlyResponse;
import com.devcourse.kurlymurly.web.dto.product.CreateCart;
import com.devcourse.kurlymurly.web.dto.user.CheckEmail;
import com.devcourse.kurlymurly.web.dto.user.CheckId;
import com.devcourse.kurlymurly.web.dto.user.JoinUser;
import com.devcourse.kurlymurly.web.dto.user.LoginUser;
import com.devcourse.kurlymurly.web.dto.user.shipping.AddAddress;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    @ResponseStatus(OK)
    public KurlyResponse<LoginUser.Response> login(@RequestBody LoginUser.Request request) {
        LoginUser.Response response = userService.logIn(request);
        return KurlyResponse.ok(response);
    }

    @PostMapping
    @ResponseStatus(OK)
    public KurlyResponse<Void> join(@RequestBody JoinUser.Request request) {
        userService.join(request);
        return KurlyResponse.noData();
    }

    @PostMapping("/login-id")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> checkId(@RequestBody CheckId.Request request) {
        boolean result = userService.checkId(request.loginId());
        return KurlyResponse.ok(result);
    }

    @PostMapping("/check-email")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> checkEmail(@RequestBody CheckEmail.Request request) {
        boolean result = userService.checkEmail(request.email());
        return KurlyResponse.ok(result);
    }

    @PostMapping("/address")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> addAddress(@RequestHeader(value = "Authorization") String token, @RequestBody AddAddress.Request request) {
        String decodedToken = jwtTokenProvider.validateToken(token);
        Long userId = extractToken(decodedToken);

        userService.addAddress(userId, request.roadAddress(), false);
        return KurlyResponse.noData();
    }

    private Long extractToken(String decodedToken) {
        String[] splitToken = decodedToken.split(":");
        return Long.parseLong(splitToken[0]);
    }

    @PostMapping("/carts")
    @ResponseStatus(OK)
    public KurlyResponse<Void> addCart(
            @AuthenticationPrincipal User user,
            @RequestBody CreateCart.Request request
    ) {
        userService.addCart(user.getId(), request.productId(), request.quantity());
        return KurlyResponse.noData();
    }
}
