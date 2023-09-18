package com.devcourse.kurlymurly.order;

import com.devcourse.kurlymurly.module.order.service.OrderService;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.web.common.KurlyResponse;
import com.devcourse.kurlymurly.web.dto.order.CreateOrder;
import com.devcourse.kurlymurly.web.dto.order.GetOrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

@Tag(name = "order", description = "주문 API")
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Tag(name = "order")
    @Operation(description = "[토큰 필요] 유저의 주문을 생성한다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 주문을 생성한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    })
    @PostMapping
    @ResponseStatus(OK)
    public KurlyResponse<CreateOrder.Response> createOrder(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid CreateOrder.Request request
    ) {
        CreateOrder.Response response = orderService.createOrder(user.getId(), request);
        return KurlyResponse.ok(response);
    }

    @Tag(name = "order")
    @Operation(description = "주문 id로 주문 상세정보를 조회한다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 주문을 조회한 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 주문일 경우")
    })
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public KurlyResponse<GetOrderResponse.DetailInfo> getOrderDetailInfoById(@PathVariable Long id) {
        GetOrderResponse.DetailInfo detailInfo = orderService.findOrderAndToDetailOrderInfo(id);
        return KurlyResponse.ok(detailInfo);
    }

    @Tag(name = "order")
    @Operation(description = "[토큰 필요] 해당 유저의 주문 내역을 간단한 정보로 조회한다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 주문을 조회한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
    })
    @GetMapping
    @ResponseStatus(OK)
    public KurlyResponse<List<GetOrderResponse.SimpleInfo>> getOrderListOfUserByUserId(@AuthenticationPrincipal User user) {
        List<GetOrderResponse.SimpleInfo> simpleOrderInfos = orderService.findOrderListSimpleFormByUserId(user.getId());
        return KurlyResponse.ok(simpleOrderInfos);
    }

    @Tag(name = "order")
    @Operation(description = "해당 주문의 주인인 유저가 주문을 취소한다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 주문을 취소한 경우"),
            @ApiResponse(responseCode = "400", description = "주문 id를 명시하지 않은 경우"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 주문일 경우")
    })
    @PatchMapping("/{id}")
    @ResponseStatus(OK)
    public KurlyResponse<Void> cancelOrder(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        orderService.toCancelByUser(id, user.getId());
        return KurlyResponse.noData();
    }
}
