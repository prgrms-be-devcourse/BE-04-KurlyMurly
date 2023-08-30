package com.devcourse.kurlymurly.web.controller.user;

import com.devcourse.kurlymurly.module.user.service.UserService;
import com.devcourse.kurlymurly.web.dto.user.CheckEmail;
import com.devcourse.kurlymurly.web.dto.user.CheckId;
import com.devcourse.kurlymurly.web.dto.user.JoinUser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    @ExceptionHandler(NoSuchElementException.class)
    public ApiResponse<String> notFoundHandler(NoSuchElementException e) {
        return ApiResponse.fail(HttpStatus.NOT_FOUND,e.getMessage());
    }

    public UserController(UserService userService) {
        this.userService = userService;
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
}
