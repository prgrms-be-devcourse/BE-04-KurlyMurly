package com.devcourse.kurlymurly.order;

import com.devcourse.kurlymurly.module.order.domain.Order;
import com.devcourse.kurlymurly.module.order.service.OrderService;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.web.common.KurlyResponse;
import com.devcourse.kurlymurly.web.dto.order.CreateOrder;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(OK)
    public KurlyResponse<CreateOrder.Response> order(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid CreateOrder.Request request
    ) {
        CreateOrder.Response response = orderService.createOrder(user.getId(), request);
        return KurlyResponse.ok(response);
    }

    @GetMapping("/{id}")
    public Order findById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @GetMapping("/{userId}") // userId는 노출 x  , TODO
    public List<Order> findAllByUserId(@PathVariable Long userId) {
        return orderService.findAllByUserId(userId);
    }

    @PatchMapping("/{id}/processing")
    public Order updateOrderToProcessing(@PathVariable Long id) {
        return orderService.updateOrderToProcessing(id);
    }

    @PatchMapping("/{id}/delivering")
    public Order updateOrderToDelivering(@PathVariable Long id) {
        return orderService.updateOrderToDelivering(id);
    }

    @PatchMapping("/{id}/done")
    public Order updateOrderToDeliveryDone(@PathVariable Long id) {
        return orderService.updateOrderToDeliveryDone(id);
    }

    @PatchMapping("/{id}")
    public HttpStatus cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return HttpStatus.OK;
    }

}
