package com.devcourse.kurlymurly.web.user;

import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.module.user.service.UserService;
import com.devcourse.kurlymurly.web.common.ApiResponse;
import com.devcourse.kurlymurly.web.dto.CreateCart;
import com.devcourse.kurlymurly.web.dto.user.CheckEmail;
import com.devcourse.kurlymurly.web.dto.user.CheckId;
import com.devcourse.kurlymurly.web.dto.user.JoinUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ApiResponse<String> notFoundHandler(NoSuchElementException e) {
        return ApiResponse.fail(HttpStatus.NOT_FOUND,e.getMessage());
    }

    @PostMapping
    public ApiResponse<Long> join(@RequestBody JoinUser.Request request) {
        userService.join(request);
        return ApiResponse.ok();
    }

    @PostMapping("/checkId")
    public ApiResponse<Boolean> checkId(@RequestBody CheckId.Request request) {
        Boolean result = userService.checkId(request.loginId());
        return ApiResponse.ok(result);
    }

    @PostMapping("/checkAddress")
    public ApiResponse<Boolean> checkEmail(@RequestBody CheckEmail.Request request) {
        Boolean result = userService.checkEmail(request.email());
        return ApiResponse.ok(result);
    }

    @PostMapping("/carts")
    public ResponseEntity<Void> addCart(
            @AuthenticationPrincipal User user,
            @RequestBody CreateCart.Request request
    ) {
        userService.addCart(user.getId(), request.productId(), request.quantity());
        return ResponseEntity.ok(null);
    }
}
