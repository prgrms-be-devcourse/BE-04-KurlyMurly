package com.devcourse.kurlymurly.web.controller.user;

import com.devcourse.kurlymurly.module.user.service.UserService;
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
    public ApiResponse<Long> save(@RequestBody JoinUser.Request request) throws NoSuchAlgorithmException {
        userService.join(request);
        return ApiResponse.ok();
    }

    @GetMapping("/checkId/{id}")
    public ApiResponse<Boolean> checkId(@PathVariable String id) {
        Boolean result = userService.checkId(id);
        return ApiResponse.ok(result);
    }

    @GetMapping("/checkAddress/{email}")
    public ApiResponse<Boolean> checkEmail(@PathVariable String email) {
        Boolean result = userService.checkEmail(email);
        return ApiResponse.ok(result);
    }
}
