package com.devcourse.kurlymurly.web.user;

import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.module.user.domain.service.UserService;
import com.devcourse.kurlymurly.web.dto.CreateCart;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
