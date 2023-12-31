package com.devcourse.kurlymurly.api.user;

import com.devcourse.kurlymurly.application.product.ProductFacade;
import com.devcourse.kurlymurly.application.user.UserFacade;
import com.devcourse.kurlymurly.auth.AuthUser;
import com.devcourse.kurlymurly.web.common.KurlyResponse;
import com.devcourse.kurlymurly.web.product.SupportResponse;
import com.devcourse.kurlymurly.web.user.UpdateUser;
import com.devcourse.kurlymurly.web.user.CreateCart;
import com.devcourse.kurlymurly.web.user.RemoveCart;
import com.devcourse.kurlymurly.web.user.UpdateCart;
import com.devcourse.kurlymurly.web.user.RegisterPayment;
import com.devcourse.kurlymurly.web.user.UpdatePayPassword;
import com.devcourse.kurlymurly.web.user.AddAddress;
import com.devcourse.kurlymurly.web.user.GetAddress;
import com.devcourse.kurlymurly.web.user.UpdateAddress;
import com.devcourse.kurlymurly.web.product.ReviewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Tag(name = "user", description = "유저 API")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserFacade userFacade;
    private final ProductFacade productFacade;

    public UserController(
            UserFacade userFacade,
            ProductFacade productFacade
    ) {
        this.userFacade = userFacade;
        this.productFacade = productFacade;
    }

    @Tag(name = "user")
    @Operation(summary = "[토큰] 유저 리뷰 조회", description = "[토큰 필요] 사용자가 작성한 리뷰 조회 API", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 상품의 후기를 가져온 상태"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않아서 발생하는 에러")
    })
    @GetMapping("/reviews")
    @ResponseStatus(OK)
    public KurlyResponse<List<ReviewResponse.Reviewed>> loadAllReviewsOnMyPage(
            @AuthenticationPrincipal AuthUser user
    ) {
        List<ReviewResponse.Reviewed> response = productFacade.loadReviewsOfUser(user.getId());
        return KurlyResponse.ok(response);
    }

    @Tag(name = "user")
    @Operation(summary = "[토큰] 유저가 작성한 상품 문의를 10개씩 가져온다", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 상품 문의들을 가져온다."),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않아서 발생하는 에러")
    })
    @GetMapping("/inquiries")
    @ResponseStatus(OK)
    public KurlyResponse<Slice<SupportResponse.Create>> loadTenInquiries(
            @AuthenticationPrincipal AuthUser user,
            @RequestParam Long last
    ) {
        Slice<SupportResponse.Create> responses = userFacade.loadAllMyInquiries(user.getId(), last);
        return KurlyResponse.ok(responses);
    }

    @Tag(name = "user")
    @Operation(summary = "[토큰] 작성 가능 리뷰 조회", description = "[토큰 필요] 작성 가능 리뷰 조회 API", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 작성 가능한 리뷰 목록을 가져온 상태"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않아서 발생하는 에러")
    })
    @GetMapping("/reviews/available")
    @ResponseStatus(OK)
    public KurlyResponse<List<ReviewResponse.Reviewable>> getReviewableOrdersOnMyPage(
            @AuthenticationPrincipal AuthUser user
    ) {
        List<ReviewResponse.Reviewable> responses = userFacade.getAllReviewableOrdersByUserId(user.getId());
        return KurlyResponse.ok(responses);
    }

    @Tag(name = "user")
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
            @AuthenticationPrincipal AuthUser user,
            @RequestBody @Valid UpdateUser.Request request
    ) {
        userFacade.updateUserInfo(user.getId(), request);
        return KurlyResponse.noData();
    }

    @Tag(name = "user")
    @Operation(description = "[토큰 필요] 도로명 주소 등록 API", responses = {
            @ApiResponse(responseCode = "200", description = "주소를 성공적으로 추가한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @PostMapping("/shipping")
    @ResponseStatus(OK)
    public KurlyResponse<Void> addAddress(
            @AuthenticationPrincipal AuthUser user,
            @RequestBody @Valid AddAddress.Request request
    ) {
        userFacade.addAddress(user.getId(), request.roadAddress(), false);
        return KurlyResponse.noData();
    }

    @Tag(name = "user")
    @Operation(description = "[토큰 필요] 주소 목록 조회 API", responses = {
            @ApiResponse(responseCode = "200", description = "유저가 등록한 주소들을 조회"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @GetMapping("/shipping/list")
    @ResponseStatus(OK)
    public KurlyResponse<List<GetAddress.Response>> getAddress(
            @AuthenticationPrincipal AuthUser user
    ) {
        List<GetAddress.Response> addressList = userFacade.loadAllAddress(user.getId());
        return KurlyResponse.ok(addressList);
    }

    @Tag(name = "user")
    @Operation(description = "[토큰 필요] 주소 수정 API", responses = {
            @ApiResponse(responseCode = "200", description = "주소를 성공적으로 수정한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "수정할 주소가 조회되지 않을 경우")
    })
    @PutMapping("/shipping")
    @ResponseStatus(OK)
    public KurlyResponse<Void> updateAddress(
            @AuthenticationPrincipal AuthUser user,
            @RequestBody @Valid UpdateAddress.Request request
    ) {
        userFacade.updateAddress(user.getId(), request.addressId(), request.description(), request.receiver(), request.contact());
        return KurlyResponse.noData();
    }

    @Tag(name = "user")
    @Operation(description = "[토큰 필요] 배송 요청사항 설정 API", responses = {
            @ApiResponse(responseCode = "200", description = "요청사항을 성공적으로 수정한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "요청사항을 추가할 주소가 조회되지 않을 경우")
    })
    @PostMapping("/shipping/info")
    @ResponseStatus(OK)
    public KurlyResponse<Void> updateAddressInfo(
            @AuthenticationPrincipal AuthUser user,
            @RequestBody @Valid UpdateAddress.InfoRequest request
    ) {
        userFacade.updateAddressInfo(user.getId(), request.addressId(), request.receiver(), request.contact(), request.receiveArea(), request.entrancePassword(), request.messageAlertTime());
        return KurlyResponse.noData();
    }

    @Tag(name = "user")
    @Operation(description = "[토큰 필요] 주소 삭제 API", responses = {
            @ApiResponse(responseCode = "200", description = "해당 주소를 삭제한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "삭제할 주소가 조회되지 않을 경우")
    })
    @DeleteMapping("/shipping/{addressId}")
    @ResponseStatus(OK)
    public KurlyResponse<Void> deleteAddress(
            @AuthenticationPrincipal AuthUser user,
            @PathVariable Long addressId
    ) {
        userFacade.deleteAddress(user.getId(), addressId);
        return KurlyResponse.noData();
    }

    @Tag(name = "user")
    @Operation(description = "[토큰 필요] 신용카드 등록 API", responses = {
            @ApiResponse(responseCode = "200", description = "신용카드를 성공적으로 등록한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @PostMapping("/register-credit")
    @ResponseStatus(OK)
    public KurlyResponse<Void> addCredit(
            @AuthenticationPrincipal AuthUser user,
            @RequestBody @Valid RegisterPayment.CreditRequest request
    ) {
        userFacade.addCredit(user.getId(), request);
        return KurlyResponse.noData();
    }

    @Tag(name = "user")
    @Operation(description = "[토큰 필요] 간편결제 등록 API", responses = {
            @ApiResponse(responseCode = "200", description = "간편 결제수단을 성공적으로 등록한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @PostMapping("/register-easy")
    @ResponseStatus(OK)
    public KurlyResponse<Void> addEasyPay(
            @AuthenticationPrincipal AuthUser user,
            @RequestBody @Valid RegisterPayment.EasyPayRequest request
    ) {
        userFacade.addEasyPay(user.getId(), request);
        return KurlyResponse.noData();
    }

    @Tag(name = "user")
    @Operation(description = "[토큰 필요] 결제수단 목록 조회 API", responses = {
            @ApiResponse(responseCode = "200", description = "결제수단 정보 목록을 성공적으로 조회한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @GetMapping("/payment/list")
    @ResponseStatus(OK)
    public KurlyResponse<List<String>> getPayment(@AuthenticationPrincipal AuthUser user) {
        List<String> creditList = userFacade.loadAllPayments(user.getId());
        return KurlyResponse.ok(creditList);
    }

    @Tag(name = "user")
    @Operation(description = "[토큰 필요] 결제 수단 삭제 API", responses = {
            @ApiResponse(responseCode = "200", description = "결제수단을 성공적으로 삭제한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "삭제할 결제수단 정보가 조회되지 않은 경우")
    })
    @DeleteMapping("/payment/{paymentId}")
    @ResponseStatus(OK)
    public KurlyResponse<Void> deletePayment(
            @AuthenticationPrincipal AuthUser user,
            @PathVariable Long paymentId
    ) {
        userFacade.deletePayment(user.getId(), paymentId);
        return KurlyResponse.noData();
    }

    @Tag(name = "user")
    @Operation(description = "[토큰 필요] 결제 비밀번호 설정 API", responses = {
            @ApiResponse(responseCode = "200", description = "결제수단을 성공적으로 삭제한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "회원이 조회되지 않는 경우")
    })
    @PostMapping("/pay-password")
    @ResponseStatus(OK)
    public KurlyResponse<Void> updatePaymentPassword(
            @AuthenticationPrincipal AuthUser user,
            @RequestBody @Valid UpdatePayPassword.Request request
    ) {
        userFacade.updatePaymentPassword(user.getId(), request.payPassword());
        return KurlyResponse.noData();
    }

    @Tag(name = "user")
    @Operation(summary = "[토큰] 장바구니 상품 추가", description = "[토큰 필요] 장바구니 상품 추가", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 장바구니에 상품을 추가한 경우"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
    })
    @PostMapping("/carts")
    @ResponseStatus(OK)
    public KurlyResponse<Void> addCart(
            @AuthenticationPrincipal AuthUser user,
            @RequestBody @Valid CreateCart.Request request
    ) {
        userFacade.addCart(user.getId(), request.productId(), request.quantity());
        return KurlyResponse.noData();
    }

    @Tag(name = "user")
    @Operation(summary = "[토큰] 장바구니 특정 상품 삭제", description = "[토큰 필요] 장바구니 특정 상품 삭제", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 장바구니 특정 상품을 삭제한 경우"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
    })
    @DeleteMapping("/carts/{cartId}")
    @ResponseStatus(OK)
    public KurlyResponse<Void> removeProduct(
            @AuthenticationPrincipal AuthUser user,
            @PathVariable Long cartId
    ) {
        userFacade.removeCartItem(cartId);
        return KurlyResponse.noData();
    }

    @Tag(name = "user")
    @Operation(summary = "[토큰] 장바구니 선택 상품 삭제", description = "[토큰 필요] 장바구니 선택 상품 삭제", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 장바구니 상품을 선택 삭제한 경우"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
    })
    @DeleteMapping("/carts")
    @ResponseStatus(OK)
    public KurlyResponse<Void> removeCartItemList(
            @AuthenticationPrincipal AuthUser user,
            @RequestBody @Valid RemoveCart.Request removeProductList
    ) {
        userFacade.removeCartItemList(removeProductList.cartIds());
        return KurlyResponse.noData();
    }

    @Tag(name = "user")
    @Operation(summary = "[토큰] 장바구니 상품 수량 수정", description = "[토큰 필요] 장바구니 상품 수량 수정", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 장바구니 상품 수량을 수정한 경우"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
    })
    @PutMapping("/carts")
    @ResponseStatus(OK)
    public KurlyResponse<Void> changeItemQuantity(
            @AuthenticationPrincipal AuthUser user,
            @RequestBody @Valid UpdateCart.Request updateCart
    ) {
        userFacade.changeItemQuantity(updateCart.cartId(), updateCart.isIncrease());
        return KurlyResponse.noData();
    }
}
