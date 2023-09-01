package com.devcourse.kurlymurly.module.order.service;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.order.domain.OrderSupport;
import com.devcourse.kurlymurly.module.order.domain.OrderSupportJpaRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.devcourse.kurlymurly.global.exception.ErrorCode.NOT_FOUND_ORDER_SUPPORT;

@Service
@Transactional(readOnly = true)
public class OrderSupportService {
    private final OrderSupportJpaRepository orderSupportRepository;

    public OrderSupportService(OrderSupportJpaRepository orderSupportRepository) {
        this.orderSupportRepository = orderSupportRepository;
    }

    @Transactional
    public OrderSupport takeOrderSupport(Long userId, Long orderId, OrderSupport.Type type,
                                         @Valid String title, String content) {
        OrderSupport orderSupport = new OrderSupport(userId, orderId, type, title, content);

        return orderSupportRepository.save(orderSupport);
    }

    public Page<OrderSupport> findOrderSupport(Pageable pageable) {
        return orderSupportRepository.findAll(pageable);
    }

    public OrderSupport findById(Long id) {
        return orderSupportRepository.findById(id)
                .orElseThrow(() -> new KurlyBaseException(NOT_FOUND_ORDER_SUPPORT));
    }

    public List<OrderSupport> findByOrderId(Long orderId) {
        return orderSupportRepository.findByOrderId(orderId);
    }

    public List<OrderSupport> findAllByUserId(Long userId) {
        return orderSupportRepository.findAllByUserId(userId);
    }

    @Transactional
    public OrderSupport updateOrderSupport(Long id, String title, String content) {
        OrderSupport orderSupport = findById(id);
        orderSupport.updateOrderSupport(title, content);

        return orderSupport;
    }

    // 관리자 영역
    @Transactional
    public OrderSupport updateSupportToPrepare(Long id) {
        OrderSupport orderSupport = findById(id);
        orderSupport.toPreparedSupport();

        return orderSupport;
    }

    @Transactional
    public OrderSupport updateSupportToAnswered(Long id) {
        OrderSupport orderSupport = findById(id);
        orderSupport.toAnsweredSupport();

        return orderSupport;
    }

    @Transactional
    public void deleteOrderSupport(Long id) {
        OrderSupport orderSupport = findById(id);
        orderSupport.deleteSupport();
    }
}
