package com.devcourse.kurlymurly.web.user;

import com.devcourse.kurlymurly.global.exception.ErrorCode;
import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.module.user.domain.payment.Payment;
import com.devcourse.kurlymurly.module.user.service.UserService;
import com.devcourse.kurlymurly.web.common.KurlyResponse;
import com.devcourse.kurlymurly.web.dto.payment.RegisterPayment;
import com.devcourse.kurlymurly.web.dto.product.CreateCart;
import com.devcourse.kurlymurly.web.dto.product.RemoveCart;
import com.devcourse.kurlymurly.web.dto.user.CheckEmail;
import com.devcourse.kurlymurly.web.dto.user.CheckId;
import com.devcourse.kurlymurly.web.dto.user.JoinUser;
import com.devcourse.kurlymurly.web.dto.user.LoginUser;
import com.devcourse.kurlymurly.web.dto.user.UpdateUser;
import com.devcourse.kurlymurly.web.dto.user.shipping.AddAddress;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    @ResponseStatus(OK)
    public KurlyResponse<String> login(@RequestBody LoginUser.Request request) {
        String response = userService.login(request.loginId(), request.password());
        return KurlyResponse.ok(response);
    }

    @PostMapping
    @ResponseStatus(OK)
    public KurlyResponse<Void> join(@RequestBody JoinUser.Request request) {
        userService.join(request);
        return KurlyResponse.noData();
    }

    @PutMapping
    @ResponseStatus(OK)
    public KurlyResponse<Void> update(@AuthenticationPrincipal User user, @RequestBody UpdateUser.Request request) {
        boolean isPasswordNotEqual = request.password().equals(request.checkPassword());

        if (isPasswordNotEqual) {
            throw new KurlyBaseException(ErrorCode.NOT_EQUAL_PASSWORD);
        }

        userService.findUpdateUser(user.getId(), request);

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
    public KurlyResponse<Void> addAddress(@AuthenticationPrincipal User user, @RequestBody AddAddress.Request request) {
        userService.addAddress(user.getId(), request.roadAddress(), false);
        return KurlyResponse.noData();
    }

    @PostMapping("/register-credit")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> addCredit(@AuthenticationPrincipal User user, @RequestBody RegisterPayment.creditRequest request) {
        userService.addCredit(user.getId(), request);
        return KurlyResponse.noData();
    }

    @PostMapping("/register-easy")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> addEasyPay(@AuthenticationPrincipal User user, @RequestBody RegisterPayment.easyPayRequest request) {
        userService.addEasyPay(user.getId(), request);
        return KurlyResponse.noData();
    }

    @GetMapping("/credits")
    @ResponseStatus(OK)
    public KurlyResponse<List<Payment>> getPayment(@AuthenticationPrincipal User user) {
        List<Payment> creditList = userService.getPayments(user.getId());
        return KurlyResponse.ok(creditList);
    }

    @PutMapping("/delete-credit/{paymentId}")
    @ResponseStatus(OK)
    public KurlyResponse<Void> deletePayment(@AuthenticationPrincipal User user, @PathVariable Long paymentId) {
        userService.deletePayment(user.getId(),paymentId);

        return KurlyResponse.noData();
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

    @DeleteMapping("/carts/{productId}")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> removeProduct(
            @AuthenticationPrincipal User user,
            @PathVariable Long productId
    ) {
        userService.removeCartItem(productId);
        return KurlyResponse.noData();
    }

    @DeleteMapping("/carts")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> removeProduct(
            @AuthenticationPrincipal User user,
            @RequestBody RemoveCart.Request removeProductList
    ) {
        userService.removeCartItemList(removeProductList.cartIds());
        return KurlyResponse.noData();
    }
}
