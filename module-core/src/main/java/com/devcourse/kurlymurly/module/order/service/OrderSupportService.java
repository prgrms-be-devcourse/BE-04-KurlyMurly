package com.devcourse.kurlymurly.module.order.service;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.order.domain.support.OrderSupport;
import com.devcourse.kurlymurly.module.order.domain.support.OrderSupportRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.devcourse.kurlymurly.global.exception.ErrorCode.NOT_FOUND_ORDER_SUPPORT;

@Service
@Transactional(readOnly = true)
public class OrderSupportService {
    private final OrderSupportRepository orderSupportRepository;

    public OrderSupportService(OrderSupportRepository orderSupportRepository) {
        this.orderSupportRepository = orderSupportRepository;
    }

    @Transactional
    public OrderSupport takeOrderSupport(Long userId, Long orderId, String orderNumber, OrderSupport.Type type,
                                         String title, String content) {
        OrderSupport orderSupport = new OrderSupport(userId, orderId, orderNumber, type, title, content);

        return orderSupportRepository.save(orderSupport);
    }

    public Page<OrderSupport> findOrderSupport(Pageable pageable) {
        return orderSupportRepository.findAll(pageable);
    }

    public OrderSupport findById(Long id) {
        return orderSupportRepository.findById(id)
                .orElseThrow(() -> new KurlyBaseException(NOT_FOUND_ORDER_SUPPORT));
    }

    public List<OrderSupport> findByOrderNumber(String orderNumber) {
        return orderSupportRepository.findByOrderNumber(orderNumber);
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

    @Transactional
    public void updateSupportToPrepare(Long id) {
        OrderSupport orderSupport = findById(id);
        orderSupport.toPreparedSupport();
    }

    @Transactional
    public void updateSupportToAnswered(Long id) {
        OrderSupport orderSupport = findById(id);
        orderSupport.toAnsweredSupport();
    }

    @Transactional
    public void deleteOrderSupport(Long id) {
        OrderSupport orderSupport = findById(id);
        orderSupport.deleteSupport();
    }
}
