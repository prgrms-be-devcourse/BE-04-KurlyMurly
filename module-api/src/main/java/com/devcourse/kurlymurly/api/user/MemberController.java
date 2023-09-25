package com.devcourse.kurlymurly.api.user;

import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.module.user.service.MemberService;
import com.devcourse.kurlymurly.web.common.KurlyResponse;
import com.devcourse.kurlymurly.web.dto.user.UpdateUser;
import com.devcourse.kurlymurly.web.dto.user.cart.CreateCart;
import com.devcourse.kurlymurly.web.dto.user.cart.RemoveCart;
import com.devcourse.kurlymurly.web.dto.user.cart.UpdateCart;
import com.devcourse.kurlymurly.web.dto.user.payment.RegisterPayment;
import com.devcourse.kurlymurly.web.dto.user.payment.UpdatePayPassword;
import com.devcourse.kurlymurly.web.dto.user.shipping.AddAddress;
import com.devcourse.kurlymurly.web.dto.user.shipping.GetAddress;
import com.devcourse.kurlymurly.web.dto.user.shipping.UpdateAddress;
import com.devcourse.kurlymurly.web.product.ReviewResponse;
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

@Tag(name = "member", description = "유저 API")
@RestController
@RequestMapping("/users")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Tag(name = "member")
    @GetMapping("/reviews")
    @ResponseStatus(OK)
    public KurlyResponse<List<ReviewResponse.Reviewable>> getReviewableOrdersOnMyPage(@AuthenticationPrincipal User user) {
        List<ReviewResponse.Reviewable> responses = memberService.getAllReviewableOrdersByUserId(user.getId());
        return KurlyResponse.ok(responses);
    }

    @Tag(name = "member")
    @Operation(description = "[토큰 필요] 개인정보 수정 API", responses = {
            @ApiResponse(responseCode = "200", description = "개인정보를 성공적으로 수정한 경우"),
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
        memberService.findUpdateUser(user.getId(), request);
        return KurlyResponse.noData();
    }

    @Tag(name = "member")
    @Operation(description = "[토큰 필요] 도로명 주소 등록 API", responses = {
            @ApiResponse(responseCode = "200", description = "주소를 성공적으로 추가한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @PostMapping("/shipping")
    @ResponseStatus(OK)
    public KurlyResponse<Void> addAddress(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid AddAddress.Request request
    ) {
        memberService.addAddress(user.getId(), request.roadAddress(), false);
        return KurlyResponse.noData();
    }

    @Tag(name = "member")
    @Operation(description = "[토큰 필요] 주소 목록 조회 API", responses = {
            @ApiResponse(responseCode = "200", description = "유저가 등록한 주소들을 조회"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @GetMapping("/shipping/list")
    @ResponseStatus(OK)
    public KurlyResponse<List<GetAddress.Response>> getAddress(@AuthenticationPrincipal User user) {
        List<GetAddress.Response> addressList = memberService.getAddress(user.getId());
        return KurlyResponse.ok(addressList);
    }

    @Tag(name = "member")
    @Operation(description = "[토큰 필요] 주소 수정 API", responses = {
            @ApiResponse(responseCode = "200", description = "주소를 성공적으로 수정한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "수정할 주소가 조회되지 않을 경우")
    })
    @PutMapping("/shipping")
    @ResponseStatus(OK)
    public KurlyResponse<Void> updateAddress(@AuthenticationPrincipal User user, @RequestBody @Valid UpdateAddress.Request request) {
        memberService.updateAddress(user.getId(), request.addressId(), request.description(), request.receiver(), request.contact());
        return KurlyResponse.noData();
    }

    @Tag(name = "member")
    @Operation(description = "[토큰 필요] 배송 요청사항 설정 API", responses = {
            @ApiResponse(responseCode = "200", description = "요청사항을 성공적으로 수정한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "요청사항을 추가할 주소가 조회되지 않을 경우")
    })
    @PostMapping("/shipping/info")
    @ResponseStatus(OK)
    public KurlyResponse<Void> updateAddressInfo(@AuthenticationPrincipal User user, @RequestBody @Valid UpdateAddress.InfoRequest request) {
        memberService.updateAddressInfo(user.getId(), request.addressId(), request.receiver(), request.contact(), request.receiveArea().name(), request.entrancePassword(), request.messageAlertTime().name());
        return KurlyResponse.noData();
    }

    @Tag(name = "member")
    @Operation(description = "[토큰 필요] 주소 삭제 API", responses = {
            @ApiResponse(responseCode = "200", description = "해당 주소를 삭제한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "삭제할 주소가 조회되지 않을 경우")
    })
    @DeleteMapping("/shipping/{addressId}")
    @ResponseStatus(OK)
    public KurlyResponse<Void> deleteAddress(@AuthenticationPrincipal User user, @PathVariable Long addressId) {
        memberService.deleteAddress(user.getId(), addressId);
        return KurlyResponse.noData();
    }

    @Tag(name = "member")
    @Operation(description = "[토큰 필요] 신용카드 등록 API", responses = {
            @ApiResponse(responseCode = "200", description = "신용카드를 성공적으로 등록한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @PostMapping("/register-credit")
    @ResponseStatus(OK)
    public KurlyResponse<Void> addCredit(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid RegisterPayment.creditRequest request
    ) {
        memberService.addCredit(user.getId(), request);
        return KurlyResponse.noData();
    }

    @Tag(name = "member")
    @Operation(description = "[토큰 필요] 간편결제 등록 API", responses = {
            @ApiResponse(responseCode = "200", description = "간편 결제수단을 성공적으로 등록한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @PostMapping("/register-easy")
    @ResponseStatus(OK)
    public KurlyResponse<Void> addEasyPay(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid RegisterPayment.easyPayRequest request
    ) {
        memberService.addEasyPay(user.getId(), request);
        return KurlyResponse.noData();
    }

    @Tag(name = "member")
    @Operation(description = "[토큰 필요] 결제수단 목록 조회 API", responses = {
            @ApiResponse(responseCode = "200", description = "결제수단 정보 목록을 성공적으로 조회한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @GetMapping("/payment/list")
    @ResponseStatus(OK)
    public KurlyResponse<List<String>> getPayment(@AuthenticationPrincipal User user) {
        List<String> creditList = memberService.getPayments(user.getId());
        return KurlyResponse.ok(creditList);
    }

    @Tag(name = "member")
    @Operation(description = "[토큰 필요] 결제 수단 삭제 API", responses = {
            @ApiResponse(responseCode = "200", description = "결제수단을 성공적으로 삭제한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "삭제할 결제수단 정보가 조회되지 않은 경우")
    })
    @DeleteMapping("/payment/{paymentId}")
    @ResponseStatus(OK)
    public KurlyResponse<Void> deletePayment(@AuthenticationPrincipal User user, @PathVariable Long paymentId) {
        memberService.deletePayment(user.getId(), paymentId);
        return KurlyResponse.noData();
    }

    @Tag(name = "member")
    @Operation(description = "[토큰 필요] 결제 비밀번호 설정 API", responses = {
            @ApiResponse(responseCode = "200", description = "결제수단을 성공적으로 삭제한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "회원이 조회되지 않는 경우")
    })
    @PostMapping("/pay-password")
    @ResponseStatus(OK)
    public KurlyResponse<Void> updatePaymentPassword(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid UpdatePayPassword.Request request
    ) {
        memberService.updatePaymentPassword(user.getId(), request.payPassword());
        return KurlyResponse.noData();
    }

    @Tag(name = "member")
    @Operation(summary = "[토큰] 장바구니 상품 추가", description = "[토큰 필요] 장바구니 상품 추가", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 장바구니에 상품을 추가한 경우"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
    })
    @PostMapping("/carts")
    @ResponseStatus(OK)
    public KurlyResponse<Void> addCart(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid CreateCart.Request request
    ) {
        memberService.addCart(user.getId(), request.productId(), request.quantity());
        return KurlyResponse.noData();
    }

    @Tag(name = "member")
    @Operation(summary = "[토큰] 장바구니 특정 상품 삭제", description = "[토큰 필요] 장바구니 특정 상품 삭제", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 장바구니 특정 상품을 삭제한 경우"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
    })
    @DeleteMapping("/carts/{cartId}")
    @ResponseStatus(OK)
    public KurlyResponse<Void> removeProduct(
            @AuthenticationPrincipal User user,
            @PathVariable Long cartId
    ) {
        memberService.removeCartItem(cartId);
        return KurlyResponse.noData();
    }

    @Tag(name = "member")
    @Operation(summary = "[토큰] 장바구니 선택 상품 삭제", description = "[토큰 필요] 장바구니 선택 상품 삭제", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 장바구니 상품을 선택 삭제한 경우"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
    })
    @DeleteMapping("/carts")
    @ResponseStatus(OK)
    public KurlyResponse<Void> removeCartItemList(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid RemoveCart.Request removeProductList
    ) {
        memberService.removeCartItemList(removeProductList.cartIds());
        return KurlyResponse.noData();
    }

    @Tag(name = "member")
    @Operation(summary = "[토큰] 장바구니 상품 수량 수정", description = "[토큰 필요] 장바구니 상품 수량 수정", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 장바구니 상품 수량을 수정한 경우"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
    })
    @PutMapping("/carts")
    @ResponseStatus(OK)
    public KurlyResponse<Void> changeItemQuantity(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid UpdateCart.Request updateCart
    ) {
        memberService.changeItemQuantity(updateCart.cartId(), updateCart.isIncrease());
        return KurlyResponse.noData();
    }
}
