package com.devcourse.kurlymurly.web.user;

import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.module.user.service.UserService;
import com.devcourse.kurlymurly.web.common.KurlyResponse;
import com.devcourse.kurlymurly.web.dto.CreateCart;
import com.devcourse.kurlymurly.web.dto.user.CheckEmail;
import com.devcourse.kurlymurly.web.dto.user.CheckId;
import com.devcourse.kurlymurly.web.dto.user.JoinUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(OK)
    public KurlyResponse<Void> join(@RequestBody JoinUser.Request request) {
        userService.join(request);
        return KurlyResponse.noData();
    }

    @PostMapping("/checkId")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> checkId(@RequestBody CheckId.Request request) {
        boolean result = userService.checkId(request.loginId());
        return KurlyResponse.ok(result);
    }

    @PostMapping("/checkAddress")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> checkEmail(@RequestBody CheckEmail.Request request) {
        boolean result = userService.checkEmail(request.email());
        return KurlyResponse.ok(result);
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
