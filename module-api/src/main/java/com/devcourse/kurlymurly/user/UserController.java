package com.devcourse.kurlymurly.user;

import com.devcourse.kurlymurly.global.exception.ErrorCode;
import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.module.user.domain.payment.Payment;
import com.devcourse.kurlymurly.module.user.service.UserService;
import com.devcourse.kurlymurly.web.common.KurlyResponse;
import com.devcourse.kurlymurly.web.dto.payment.RegisterPayment;
import com.devcourse.kurlymurly.web.dto.payment.UpdatePayPassword;
import com.devcourse.kurlymurly.web.dto.product.CreateCart;
import com.devcourse.kurlymurly.web.dto.product.RemoveCart;
import com.devcourse.kurlymurly.web.dto.product.UpdateCart;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewResponse;
import com.devcourse.kurlymurly.web.dto.user.CheckEmail;
import com.devcourse.kurlymurly.web.dto.user.CheckId;
import com.devcourse.kurlymurly.web.dto.user.Join;
import com.devcourse.kurlymurly.web.dto.user.Login;
import com.devcourse.kurlymurly.web.dto.user.UpdateUser;
import com.devcourse.kurlymurly.web.dto.user.shipping.AddAddress;
import com.devcourse.kurlymurly.web.dto.user.shipping.GetAddress;
import com.devcourse.kurlymurly.web.dto.user.shipping.UpdateAddress;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

import static org.springframework.http.HttpStatus.OK;

@Tag(name = "user", description = "유저 API")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Tag(name = "user")
    @GetMapping("/reviews")
    @ResponseStatus(OK)
    public KurlyResponse<List<ReviewResponse.Reviewable>> getReviewableOrdersOnMyPage(@AuthenticationPrincipal User user) {
        List<ReviewResponse.Reviewable> responses = userService.getAllReviewableOrdersByUserId(user.getId());
        return KurlyResponse.ok(responses);
    }

    @Tag(name = "user")
    @Operation(description = "로그인 API", responses = {
            @ApiResponse(responseCode = "200", description = "로그인에 성공한 경우"),
            @ApiResponse(responseCode = "422", description = "잘못된 로그인 정보를 입력한 경우"),
    })
    @PostMapping("/login")
    @ResponseStatus(OK)
    public KurlyResponse<Login.Response> login(@RequestBody @Valid Login.Request request) {
        Login.Response response = userService.login(request.loginId(), request.password());

        return KurlyResponse.ok(response);
    }

    @Tag(name = "user")
    @Operation(description = "회원 가입 API", responses = {
            @ApiResponse(responseCode = "200", description = "회원 가입에 성공한 경우"),
            @ApiResponse(responseCode = "400", description = "변경할 비밀번호와 확인 비밀번호가 일치하지 않는 경우"),
            @ApiResponse(responseCode = "409", description = "이미 가입된 아이디를 입력한 경우"),
            @ApiResponse(responseCode = "409", description = "이미 가입된 이메일을 입력한 경우")
    })
    @PostMapping
    @ResponseStatus(OK)
    public KurlyResponse<Void> join(@RequestBody @Valid Join.Request request) {
        userService.join(request);
        return KurlyResponse.noData();
    }

    @Tag(name = "user")
    @Operation(description = "[토큰 필요] 개인정보 수정 API", responses = {
            @ApiResponse(responseCode = "200", description = "개인정보를 성공적으로 수정한 경우"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "400", description = "재확인 비밀번호가 일치하지 않는 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "해당 유저가 조회되지 않는 경우"),
            @ApiResponse(responseCode = "404", description = "현재 비밀번호가 일치하지 않는 경우")
    })
    @PutMapping("/info")
    @ResponseStatus(OK)
    public KurlyResponse<Void> update(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid UpdateUser.Request request
    ) {
        boolean isPasswordNotEqual = request.password().equals(request.checkPassword());

        if (isPasswordNotEqual) {
            throw new KurlyBaseException(ErrorCode.NOT_SAME_PASSWORD);
        }

        userService.findUpdateUser(user.getId(), request);

        return KurlyResponse.noData();
    }

    @Tag(name = "user")
    @Operation(description = "id 중복 체크 API", responses = {
            @ApiResponse(responseCode = "200", description = "해당 id의 중복검사를 진행"),
    })
    @PostMapping("/check-id")
    @ResponseStatus(OK)
    public KurlyResponse<Void> checkId(@RequestBody @Valid CheckId.Request request) {
        boolean result = userService.checkId(request.loginId());
        return KurlyResponse.ok(result);
    }

    @Tag(name = "user")
    @Operation(description = "이메일 중복 체크 API", responses = {
            @ApiResponse(responseCode = "200", description = "해당 이메일에 대한 중복검사를 진행")
    })
    @PostMapping("/check-email")
    @ResponseStatus(OK)
    public KurlyResponse<Void> checkEmail(@RequestBody @Valid CheckEmail.Request request) {
        boolean result = userService.checkEmail(request.email());
        return KurlyResponse.ok(result);
    }

    @Tag(name = "user")
    @Operation(description = "[토큰 필요] 도로명 주소 등록 API", responses = {
            @ApiResponse(responseCode = "200", description = "주소를 성공적으로 추가한 경우"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @PostMapping("/shipping")
    @ResponseStatus(OK)
    public KurlyResponse<Void> addAddress(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid AddAddress.Request request
    ) {
        userService.addAddress(user.getId(), request.roadAddress(), false);
        return KurlyResponse.noData();
    }

    @Tag(name = "user")
    @Operation(description = "[토큰 필요] 주소 목록 조회 API", responses = {
            @ApiResponse(responseCode = "200", description = "유저가 등록한 주소들을 조회"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @GetMapping("/shipping/list")
    @ResponseStatus(OK)
    public KurlyResponse<List<GetAddress.Response>> getAddress(@AuthenticationPrincipal User user) {
        List<GetAddress.Response> addressList = userService.getAddress(user.getId());
        return KurlyResponse.ok(addressList);
    }

    @Tag(name = "user")
    @Operation(description = "[토큰 필요] 주소 수정 API", responses = {
            @ApiResponse(responseCode = "200", description = "주소를 성공적으로 수정한 경우"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "수정할 주소가 조회되지 않을 경우")
    })
    @PutMapping("/shipping")
    @ResponseStatus(OK)
    public KurlyResponse<Void> updateAddress(@AuthenticationPrincipal User user, @RequestBody UpdateAddress.Request request) {
        userService.updateAddress(user.getId(), request.addressId(), request.description(), request.receiver(), request.contact());
        return KurlyResponse.noData();
    }

    @Tag(name = "user")
    @Operation(description = "[토큰 필요] 배송 요청사항 설정 API", responses = {
            @ApiResponse(responseCode = "200", description = "요청사항을 성공적으로 수정한 경우"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "요청사항을 추가할 주소가 조회되지 않을 경우")
    })
    @PostMapping("/shipping/info")
    @ResponseStatus(OK)
    public KurlyResponse<Void> updateAddressInfo(@AuthenticationPrincipal User user, @RequestBody @Valid UpdateAddress.InfoRequest request) {
        userService.updateAddressInfo(user.getId(), request.addressId(), request.receiver(), request.contact(), request.receiveArea().name(), request.entrancePassword(), request.messageAlertTime().name());
        return KurlyResponse.noData();
    }

    @Tag(name = "user")
    @Operation(description = "[토큰 필요] 주소 삭제 API", responses = {
            @ApiResponse(responseCode = "200", description = "해당 주소를 삭제한 경우"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "삭제할 주소가 조회되지 않을 경우")
    })
    @DeleteMapping("/shipping/{addressId}")
    @ResponseStatus(OK)
    public KurlyResponse<Void> deleteAddress(@AuthenticationPrincipal User user, @PathVariable Long addressId) {
        userService.deleteAddress(user.getId(), addressId);
        return KurlyResponse.noData();
    }

    @Tag(name = "user")
    @Operation(description = "[토큰 필요] 신용카드 등록 API", responses = {
            @ApiResponse(responseCode = "200", description = "신용카드를 성공적으로 등록한 경우"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @PostMapping("/register-credit")
    @ResponseStatus(OK)
    public KurlyResponse<Void> addCredit(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid RegisterPayment.creditRequest request
    ) {
        userService.addCredit(user.getId(), request);
        return KurlyResponse.noData();
    }

    @Tag(name = "user")
    @Operation(description = "[토큰 필요] 간편결제 등록 API", responses = {
            @ApiResponse(responseCode = "200", description = "간편 결제수단을 성공적으로 등록한 경우"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @PostMapping("/register-easy")
    @ResponseStatus(OK)
    public KurlyResponse<Void> addEasyPay(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid RegisterPayment.easyPayRequest request
    ) {
        userService.addEasyPay(user.getId(), request);
        return KurlyResponse.noData();
    }

    @Tag(name = "user")
    @Operation(description = "[토큰 필요] 결제수단 목록 조회 API", responses = {
            @ApiResponse(responseCode = "200", description = "결제수단 정보 목록을 성공적으로 조회한 경우"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "결제수단 정보들이 조회되지 않은 경우")
    })
    @GetMapping("/payment/list")
    @ResponseStatus(OK)
    public KurlyResponse<List<Payment>> getPayment(@AuthenticationPrincipal User user) {
        List<Payment> creditList = userService.getPayments(user.getId());
        return KurlyResponse.ok(creditList);
    }

    @Tag(name = "user")
    @Operation(description = "[토큰 필요] 결제 수단 삭제 API", responses = {
            @ApiResponse(responseCode = "200", description = "결제수단을 성공적으로 삭제한 경우"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "삭제할 결제수단 정보가 조회되지 않은 경우")
    })
    @DeleteMapping("/payment/{paymentId}")
    @ResponseStatus(OK)
    public KurlyResponse<Void> deletePayment(@AuthenticationPrincipal User user, @PathVariable Long paymentId) {
        userService.deletePayment(user.getId(), paymentId);

        return KurlyResponse.noData();
    }

    @Tag(name = "user")
    @Operation(description = "[토큰 필요] 결제 비밀번호 설정 API", responses = {
            @ApiResponse(responseCode = "200", description = "결제수단을 성공적으로 삭제한 경우"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "회원이 조회되지 않는 경우")
    })
    @PostMapping("/pay-password")
    @ResponseStatus(OK)
    public KurlyResponse<Void> updatePaymentPassword(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid UpdatePayPassword.Request request
    ) {
        userService.updatePaymentPassword(user.getId(), request.payPassword());
        return KurlyResponse.noData();
    }

    @Tag(name = "user")
    @PostMapping("/carts")
    @ResponseStatus(OK)
    public KurlyResponse<Void> addCart(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid CreateCart.Request request
    ) {
        userService.addCart(user.getId(), request.productId(), request.quantity());
        return KurlyResponse.noData();
    }

    @Tag(name = "user")
    @DeleteMapping("/carts/{cartId}")
    @ResponseStatus(OK)
    public KurlyResponse<Void> removeProduct(
            @AuthenticationPrincipal User user,
            @PathVariable Long cartId
    ) {
        userService.removeCartItem(cartId);
        return KurlyResponse.noData();
    }

    @Tag(name = "user")
    @DeleteMapping("/carts")
    @ResponseStatus(OK)
    public KurlyResponse<Void> removeCartItemList(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid RemoveCart.Request removeProductList
    ) {
        userService.removeCartItemList(removeProductList.cartIds());
        return KurlyResponse.noData();
    }

    @Tag(name = "user")
    @PutMapping("/carts")
    @ResponseStatus(OK)
    public KurlyResponse<Void> changeItemQuantity(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid UpdateCart.Request updateCart
    ) {
        userService.changeItemQuantity(updateCart.cartId(), updateCart.isIncrease());
        return KurlyResponse.noData();
    }
}
