package com.devcourse.kurlymurly.api.order;

import com.devcourse.kurlymurly.module.order.service.OrderSupportService;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.web.common.KurlyResponse;
import com.devcourse.kurlymurly.web.order.CreateOrderSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Tag(name = "orderSupport", description = "1:1 문의 API")
@RestController
@RequestMapping("/inquiries")
public class OrderSupportController {
    private final OrderSupportService orderSupportService;

    public OrderSupportController(OrderSupportService orderSupportService) {
        this.orderSupportService = orderSupportService;
    }

    @Tag(name = "orderSupport")
    @Operation(summary = "[토큰] 1:1 문의 생성", description = "[토큰 필요] 1:1 문의를 생성한다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 1:1 문의를 생성한 경우"),
            @ApiResponse(responseCode = "400", description = "1:1 문의를 작성하기 위한 사용자 응답이 적절하지 않은 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @PostMapping
    @ResponseStatus(OK)
    public KurlyResponse<CreateOrderSupport.Response> takeOrderSupport(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid CreateOrderSupport.Request request
    ) {
        CreateOrderSupport.Response OrderSupportResponse = orderSupportService.takeOrderSupport(user.getId(), request);
        return KurlyResponse.ok(OrderSupportResponse);
    }

    @Tag(name = "orderSupport")
    @Operation(summary = "[토큰] 사용자 1:1 문의 내역 조회", description = "[토큰 필요] 해당 유저의 1:1 문의 내역을 조회한다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 1:1 문의 내역을 조회한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @GetMapping
    @ResponseStatus(OK)
    public KurlyResponse<List<CreateOrderSupport.Response>> findAllByUserId(@AuthenticationPrincipal User user) {
        List<CreateOrderSupport.Response> orderSupportResponses = orderSupportService.findAllByUserId(user.getId());
        return KurlyResponse.ok(orderSupportResponses);
    }

    @Tag(name = "orderSupport")
    @Operation(summary = "[토큰] 1:1 문의 수정", description = "[토큰 필요] 작성한 1:1 문의를 수정한다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 1:1 문의를 수정한 경우"),
            @ApiResponse(responseCode = "400", description = "1:1문의 id를 명시하지 않은 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 1:1 문의일 경우"),
            @ApiResponse(responseCode = "409", description = "문의 작성자가 아닐 경우")
    })
    @PatchMapping("/{id}")
    @ResponseStatus(OK)
    public KurlyResponse<Void> updateOrderSupport(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody @Valid CreateOrderSupport.UpdateRequest request
    ) {
        orderSupportService.updateOrderSupport(user.getId(), id, request.title(), request.content());
        return KurlyResponse.noData();
    }

    @Tag(name = "orderSupport")
    @Operation(summary = "[토큰] 1:1 문의 삭제", description = "[토큰 필요] 작성한 1:1 문의를 삭제한다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 1:1 문의를 삭제한 경우"),
            @ApiResponse(responseCode = "400", description = "1:1문의 id를 명시하지 않은 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 1:1 문의일 경우"),
            @ApiResponse(responseCode = "409", description = "문의 작성자가 아닐 경우")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public KurlyResponse<Void> deleteOrderSupport(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        orderSupportService.deleteOrderSupport(user.getId(), id);
        return KurlyResponse.noData();
    }
}
