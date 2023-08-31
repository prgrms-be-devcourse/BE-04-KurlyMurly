package com.devcourse.kurlymurly.module.order.service;

import com.devcourse.kurlymurly.Exception.NotFoundOrderException;
import com.devcourse.kurlymurly.module.order.domain.Order;
import com.devcourse.kurlymurly.module.order.domain.OrderJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderJpaRepository orderRepository;

    public OrderService(OrderJpaRepository orderRepository) {
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
                .orElseThrow(NotFoundOrderException::new);
    }

    public List<Order> findAllByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId)
                .orElseThrow(NotFoundOrderException::new);
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
