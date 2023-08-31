package com.devcourse.kurlymurly.web.dto.order.controller;

import com.devcourse.kurlymurly.module.order.domain.Order;
import com.devcourse.kurlymurly.module.order.service.OrderService;
import com.devcourse.kurlymurly.web.dto.order.OrderCreate;
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
    public Order createOrder(@RequestBody OrderCreate.Request request) { // TODO
        return orderService.createOrder(
                request.userId(),
                request.shippingId(),
                request.totalPrice(),
                request.payment()
        );
    }

    @GetMapping("/{id}")
    public Order findById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @GetMapping("/{userId}") // userId는 노출 x  , TODO
    public List<Order> findAllByUserId(@PathVariable Long userId) {
        return orderService.findAllByUserId(userId);
    }

    @PatchMapping("/processing/{id}")
    public Order updateOrderToProcessing(@PathVariable Long id) {
        return orderService.updateOrderToProcessing(id);
    }

    @PatchMapping("/delivering/{id}")
    public Order updateOrderToDelivering(@PathVariable Long id) {
        return orderService.updateOrderToDelivering(id);
    }

    @PatchMapping("/done/{id}")
    public Order updateOrderToDeliveryDone(@PathVariable Long id) {
        return orderService.updateOrderToDeliveryDone(id);
    }

    @PatchMapping("/{id}")
    public HttpStatus cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return HttpStatus.OK;
    }

}
