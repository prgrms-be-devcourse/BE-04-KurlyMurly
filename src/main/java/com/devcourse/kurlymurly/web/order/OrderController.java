package com.devcourse.kurlymurly.web.order;

import com.devcourse.kurlymurly.module.order.domain.Order;
import com.devcourse.kurlymurly.module.order.service.OrderService;
import com.devcourse.kurlymurly.web.common.PageParam;
import com.devcourse.kurlymurly.web.dto.order.OrderCreate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderCreate.Request request) {
        return orderService.createOrder(
                request.userId(),
                request.shippingId(),
                request.totalPrice(),
                request.payment()
        );
    }

    @GetMapping
    public Page<Order> findOrderAll(@RequestBody PageParam param) {
        Pageable pageable = PageRequest.of(param.page(), param.size());

        return orderService.findOrderAll(pageable);
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
