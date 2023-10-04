package com.devcourse.kurlymurly.api.admin;

import com.devcourse.kurlymurly.application.product.ProductFacade;
import com.devcourse.kurlymurly.domain.service.ReviewCommand;
import com.devcourse.kurlymurly.module.order.service.OrderService;
import com.devcourse.kurlymurly.module.order.service.OrderSupportService;
import com.devcourse.kurlymurly.auth.AuthUser;
import com.devcourse.kurlymurly.web.common.KurlyResponse;
import com.devcourse.kurlymurly.web.order.AnswerOrderSupport;
import com.devcourse.kurlymurly.web.product.ProductRequest;
import com.devcourse.kurlymurly.web.product.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.OK;

@Tag(name = "admin", description = "관리자 API")
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final ProductFacade productFacade;
    private final ReviewCommand reviewCommand;
    private final OrderService orderService;
    private final OrderSupportService orderSupportService;

    public AdminController(
            ProductFacade productFacade,
            ReviewCommand reviewCommand,
            OrderService orderService,
            OrderSupportService orderSupportService
    ) {
        this.productFacade = productFacade;
        this.reviewCommand = reviewCommand;
        this.orderService = orderService;
        this.orderSupportService = orderSupportService;
    }

    @Tag(name = "admin")
    @Operation(description = "[관리자 토큰 필요] 새로운 상품을 등록한다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 상품을 등록했습니다."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 토큰이거나 토큰을 보내지 않은 경우")
    })
    @PostMapping("/products")
    @ResponseStatus(OK)
    public KurlyResponse<ProductResponse.Create> createProduct(
            @AuthenticationPrincipal AuthUser admin,
            @RequestPart MultipartFile image,
            @RequestBody ProductRequest.Create request
    ) {
        ProductResponse.Create response = productFacade.createProduct(image, request);
        return KurlyResponse.ok(response);
    }

    @Tag(name = "admin")
    @Operation(description = "[관리자 토큰 필요] 상품을 품절로 처리하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공적으로 상품을 품절시켰습니다."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 토큰이거나 토큰을 보내지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 상품입니다.")
    })
    @PutMapping("/products/{productId}/sold-out")
    @ResponseStatus(OK)
    public KurlyResponse<Void> soldOutProduct(
            @AuthenticationPrincipal AuthUser admin,
            @PathVariable Long productId
    ) {
//        productFacade.soldOutProduct(productId);
        return KurlyResponse.noData();
    }

    @Tag(name = "admin")
    @Operation(description = "[관리자 토큰 필요] 상품을 삭제한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공적으로 상품을 삭제했습니다."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 토큰이거나 토큰을 보내지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 상품입니다.")
    })
    @DeleteMapping("/products/{productId}")
    @ResponseStatus(OK)
    public KurlyResponse<Void> deleteProduct(
            @AuthenticationPrincipal AuthUser admin,
            @PathVariable Long productId
    ) {
//        productFacade.deleteProduct(productId);
        return KurlyResponse.noData();
    }

    @Tag(name = "admin")
    @Operation(description = "[관리자 토큰 필요] BANNED 리뷰로 변환 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "review 상태를 BANNED로 변경한 경우"),
            @ApiResponse(responseCode = "400", description = "리뷰 id가 명시되지 않은 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @PatchMapping("/reviews/{reviewId}/ban")
    @ResponseStatus(OK)
    public KurlyResponse<Void> updateToBanned(
            @AuthenticationPrincipal AuthUser admin,
            @PathVariable Long reviewId
    ) {
        reviewCommand.banned(reviewId);
        return KurlyResponse.noData();
    }

    @Tag(name = "admin")
    @Operation(description = "[관리자 토큰 필요] BEST 리뷰로 변환 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "review 상태를 BEST로 변경한 경우"),
            @ApiResponse(responseCode = "400", description = "리뷰 id가 명시되지 않은 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @PatchMapping("/reviews/{reviewId}/best")
    @ResponseStatus(OK)
    public KurlyResponse<Void> updateToBest(
            @AuthenticationPrincipal AuthUser admin,
            @PathVariable Long reviewId
    ) {
        reviewCommand.toBestReview(reviewId);
        return KurlyResponse.noData();
    }

    @Tag(name = "admin")
    @Operation(description = "[관리자 토큰 필요] 주문을 다음 단계 상태로 변환 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "order 상태를 PRODCESSING로 변경한 경우"),
            @ApiResponse(responseCode = "400", description = "주문 id가 명시되지 않은 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @PatchMapping("/orders/{orderId}/processing")
    @ResponseStatus(OK)
    public KurlyResponse<Void> changeToProcessing(
            @AuthenticationPrincipal AuthUser admin,
            @PathVariable Long orderId
    ) {
        orderService.toNextState(orderId);
        return KurlyResponse.noData();
    }

    @Tag(name = "admin")
    @Operation(description = "[관리자 토큰 필요] 주문을 취소하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "order 상태를 CANCLED로 변경한 경우"),
            @ApiResponse(responseCode = "400", description = "주문 id가 명시되지 않은 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @PostMapping("/orders/{orderId}/cancel")
    @ResponseStatus(OK)
    public KurlyResponse<Void> changeToDone(
            @AuthenticationPrincipal AuthUser admin,
            @PathVariable Long orderId
    ) {
        orderService.toCancel(orderId);
        return KurlyResponse.noData();
    }

    @Tag(name = "admin")
    @Operation(description = "[관리자 토큰 필요] 1:1 문의 답변 처리 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공적으로 1:1 문의에 답변 해준 경우"),
            @ApiResponse(responseCode = "400", description = "1:1 문의 id가 명시되지 않은 경우"),
            @ApiResponse(responseCode = "401", description = "권한이 없는 토큰이거나 토큰을 보내지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 1:1 문의인 경우"),
    })
    @PostMapping("/orderSupports/answer")
    @ResponseStatus(OK)
    public KurlyResponse<Void> changeToAnswered(
            @AuthenticationPrincipal AuthUser admin,
            @RequestBody @Valid AnswerOrderSupport.Request answerRequest
    ) {
        orderSupportService.answered(answerRequest.orderSupportId(), answerRequest.content());
        return KurlyResponse.noData();
    }
}
