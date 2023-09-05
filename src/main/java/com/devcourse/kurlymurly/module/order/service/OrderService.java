package com.devcourse.kurlymurly.module.order.service;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.order.domain.Order;
import com.devcourse.kurlymurly.module.order.domain.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.devcourse.kurlymurly.global.exception.ErrorCode.NOT_FOUND_ORDER;


@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order createOrder(Long userId, Long shippingId, int totalPrice, String payment) {
        Order entity = new Order(userId, shippingId, totalPrice, payment);

        return orderRepository.save(entity);
    }

    public Page<Order> findOrderAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new KurlyBaseException(NOT_FOUND_ORDER));
    }

    public List<Order> findAllByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    // 관리자 영역 (ADMIN 권한 필요)
    @Transactional
    public Order updateOrderToProcessing(Long id) {
        Order order = findById(id);
        order.processingOrder();

        return order;
    }

    @Transactional
    public Order updateOrderToDelivering(Long id) {
        Order order = findById(id);
        order.deliveringOrder();

        return order;
    }

    @Transactional
    public Order updateOrderToDeliveryDone(Long id) {
        Order order = findById(id);
        order.deliveryDoneOrder();

        return order;
    }

    @Transactional
    public void cancelOrder(Long id) {
        Order order = findById(id);
        order.cancelOrder();
    }
}
