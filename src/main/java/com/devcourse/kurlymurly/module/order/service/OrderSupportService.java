package com.devcourse.kurlymurly.module.order.service;

import com.devcourse.kurlymurly.Exception.NotFoundOrderException;
import com.devcourse.kurlymurly.module.order.domain.OrderSupport;
import com.devcourse.kurlymurly.module.order.domain.OrderSupportJpaRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderSupportService {
    private final OrderSupportJpaRepository orderSupportRepository;

    public OrderSupportService(OrderSupportJpaRepository orderSupportRepository) {
        this.orderSupportRepository = orderSupportRepository;
    }

    @Transactional
    public OrderSupport takeOrderSupport(Long userId, Long orderId, OrderSupport.Category category,
                                         @Valid String title, String content) {
        OrderSupport entity = new OrderSupport(userId, orderId, category, title, content);

        return orderSupportRepository.save(entity);
    }

    public Page<OrderSupport> findOrderSupport(Pageable pageable) {
        return orderSupportRepository.findAll(pageable);
    }

    public OrderSupport findById(Long id) {
        return orderSupportRepository.findById(id)
                .orElseThrow(NotFoundOrderException::new);
    }

    public OrderSupport findByOrderId(Long orderId) {
        return orderSupportRepository.findByOrderId(orderId)
                .orElseThrow(NotFoundOrderException::new);
    }

    public List<OrderSupport> findAllByUserId(Long userId) {
        return orderSupportRepository.findAllByUserId(userId)
                .orElseThrow(NotFoundOrderException::new);
    }

    @Transactional
    public OrderSupport updateOrderSupport(Long id, String title, String content) {
        OrderSupport entity = findById(id);
        entity.updateOrderSupport(title, content);

        return entity;
    }

    // 관리자 영역
    @Transactional
    public OrderSupport updateSupportToPrepare(Long id) {
        OrderSupport entity = findById(id);
        entity.prepareSupport();

        return entity;
    }

    @Transactional
    public OrderSupport updateSupportToStart(Long id) {
        OrderSupport entity = findById(id);
        entity.startSupport();

        return entity;
    }

    @Transactional
    public OrderSupport updateSupportToDone(Long id) {
        OrderSupport entity = findById(id);
        entity.doneSupport();

        return entity;
    }

    @Transactional
    public void deleteOrderSupport(Long id) {
        OrderSupport entity = findById(id);
        entity.deleteSupport();
    }
}
