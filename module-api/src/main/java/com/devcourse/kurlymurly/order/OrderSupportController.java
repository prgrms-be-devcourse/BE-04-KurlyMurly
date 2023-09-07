package com.devcourse.kurlymurly.order;

import com.devcourse.kurlymurly.module.order.domain.support.OrderSupport;
import com.devcourse.kurlymurly.module.order.service.OrderSupportService;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.web.dto.order.OrderSupportCreate;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orderSupports")
public class OrderSupportController {
    private final OrderSupportService orderSupportService;

    public OrderSupportController(OrderSupportService orderSupportService) {
        this.orderSupportService = orderSupportService;
    }

    @PostMapping
    public OrderSupport takeOrderSupport(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid OrderSupportCreate.Request request
    ) {
        return orderSupportService.takeOrderSupport(
                user.getId(),
                request.orderId(),
                request.orderNumber(),
                request.type(),
                request.title(),
                request.content()
        );
    }

    @GetMapping("/{userId}")
    public List<OrderSupport> findAllByUserId(@PathVariable Long userId) {
        return orderSupportService.findAllByUserId(userId);
    }

    @PatchMapping("/{id}")
    public OrderSupport updateOrderSupport(@PathVariable Long id,
                                           @RequestBody @Valid OrderSupportCreate.UpdateRequest request) {
        return orderSupportService.updateOrderSupport(id, request.title(), request.content());
    }

    @PatchMapping("/{id}/prepare")
    public OrderSupport changeSupportToPrepare(@PathVariable Long id) {
        return orderSupportService.updateSupportToPrepare(id);
    }

    @PatchMapping("/{id}/answer")
    public OrderSupport changeSupportToDone(@PathVariable Long id) {
        return orderSupportService.updateSupportToAnswered(id);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderSupport(@PathVariable Long id) {
        orderSupportService.deleteOrderSupport(id);
    }
}
