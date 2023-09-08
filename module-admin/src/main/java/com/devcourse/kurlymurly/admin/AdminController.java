package com.devcourse.kurlymurly.admin;

import com.devcourse.kurlymurly.module.order.service.OrderService;
import com.devcourse.kurlymurly.module.order.service.OrderSupportService;
import com.devcourse.kurlymurly.module.product.service.ProductFacade;
import com.devcourse.kurlymurly.module.product.service.ReviewService;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.web.common.KurlyResponse;
import com.devcourse.kurlymurly.web.dto.product.CreateProduct;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "admin", description = "관리자 API")
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final ProductFacade productFacade;
    private final ReviewService reviewService;
    private final OrderService orderService;
    private final OrderSupportService orderSupportService;

    public AdminController(ProductFacade productFacade, ReviewService reviewService, OrderService orderService, OrderSupportService orderSupportService) {
        this.productFacade = productFacade;
        this.reviewService = reviewService;
        this.orderService = orderService;
        this.orderSupportService = orderSupportService;
    }

    @Tag(name = "admin")
    @Operation(description = "[관리자 토큰 필요] 새로운 상품을 등록한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공적으로 상품을 등록했습니다."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 토큰이거나 토큰을 보내지 않은 경우")
    })
    @PostMapping("/products")
    @ResponseStatus(OK)
    public KurlyResponse<CreateProduct.Response> createProduct(
            @AuthenticationPrincipal User admin,
            @RequestBody @Valid CreateProduct.Request request
    ) {
        CreateProduct.Response response = productFacade.createProduct(request);
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
            @AuthenticationPrincipal User admin,
            @PathVariable Long productId
    ) {
        productFacade.soldOutProduct(productId);
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
            @AuthenticationPrincipal User admin,
            @PathVariable Long productId
    ) {
        productFacade.delete(productId);
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
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> updateToBanned(
            @AuthenticationPrincipal User admin,
            @PathVariable Long reviewId
    ) {
        reviewService.updateToBanned(reviewId);
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
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> updateToBest(
            @AuthenticationPrincipal User admin,
            @PathVariable Long reviewId
    ) {
        reviewService.updateToBest(reviewId);
        return KurlyResponse.noData();
    }

    @Tag(name = "admin")
    @Operation(description = "[관리자 토큰 필요] Processing 주문으로 변환 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "order 상태를 PRODCESSING로 변경한 경우"),
            @ApiResponse(responseCode = "400", description = "주문 id가 명시되지 않은 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @PatchMapping("/{id}/processing")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> updateOrderToProcessing(
            @AuthenticationPrincipal User admin,
            @PathVariable Long id
    ) {
        orderService.toProcessing(id);
        return KurlyResponse.noData();
    }

    @Tag(name = "admin")
    @Operation(description = "[관리자 토큰 필요] Delivering 주문으로 변환 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "order 상태를 DELIVERING로 변경한 경우"),
            @ApiResponse(responseCode = "400", description = "주문 id가 명시되지 않은 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @PatchMapping("/{id}/delivering")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> updateOrderToDelivering(
            @AuthenticationPrincipal User admin,
            @PathVariable Long id
    ) {
        orderService.toDelivering(id);
        return KurlyResponse.noData();
    }

    @Tag(name = "admin")
    @Operation(description = "[관리자 토큰 필요] Delivered 주문으로 변환 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "order 상태를 DELIVERED로 변경한 경우"),
            @ApiResponse(responseCode = "400", description = "주문 id가 명시되지 않은 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @PatchMapping("/{id}/done")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> updateOrderToDeliveryDone(
            @AuthenticationPrincipal User admin,
            @PathVariable Long id
    ) {
        orderService.toDelivered(id);
        return KurlyResponse.noData();
    }

    @Tag(name = "admin")
    @Operation(description = "[관리자 토큰 필요] Canceled 주문으로 변환 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "order 상태를 CANCLED로 변경한 경우"),
            @ApiResponse(responseCode = "400", description = "주문 id가 명시되지 않은 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @PatchMapping("/{id}/done")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> updateOrderToCanceled(
            @AuthenticationPrincipal User admin,
            @PathVariable Long id
    ) {
        orderService.toCancel(id);
        return KurlyResponse.noData();
    }

    @Tag(name = "admin")
    @Operation(description = "[관리자 토큰 필요] 1:1 문의 상태를 답변 준비중으로 변환 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "orderSupport 상태를 PREPARE로 변경한 경우"),
            @ApiResponse(responseCode = "400", description = "1:1 문의 id가 명시되지 않은 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @PatchMapping("/{id}/prepare")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> changeSupportToPrepare(
            @AuthenticationPrincipal User admin,
            @PathVariable Long id
    ) {
        orderSupportService.updateSupportToPrepare(id);
        return KurlyResponse.noData();
    }

    @Tag(name = "admin")
    @Operation(description = "[관리자 토큰 필요] 1:1 문의 상태를 답변 완료로 변환 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "orderSupport 상태를 ANSWERED로 변경한 경우"),
            @ApiResponse(responseCode = "400", description = "1:1 문의 id가 명시되지 않은 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @PatchMapping("/{id}/answer")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> changeSupportToAnswered(
            @AuthenticationPrincipal User admin,
            @PathVariable Long id
    ) {
        orderSupportService.updateSupportToAnswered(id);
        return KurlyResponse.noData();
    }
}
