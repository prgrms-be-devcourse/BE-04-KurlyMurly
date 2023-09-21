package com.devcourse.kurlymurly.module.order.service;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.order.domain.support.OrderSupport;
import com.devcourse.kurlymurly.module.order.domain.support.OrderSupportRepository;
import com.devcourse.kurlymurly.web.dto.order.support.CreateOrderSupport;
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
    public CreateOrderSupport.Response takeOrderSupport(Long userId, CreateOrderSupport.Request request) {
        OrderSupport orderSupport = new OrderSupport(
                userId,
                request.orderId(),
                request.orderNumber(),
                request.type(),
                request.title(),
                request.content()
        );

        orderSupportRepository.save(orderSupport);

        return new CreateOrderSupport.Response(
                request.type(),
                request.title(),
                request.content(),
                orderSupport.getStatus(),
                orderSupport.getCreateAt()
        );
    }


    public Page<OrderSupport> findOrderSupport(Pageable pageable) {
        return orderSupportRepository.findAll(pageable);
    }

    public OrderSupport findByIdOrThrow(Long id) {
        return orderSupportRepository.findById(id)
                .orElseThrow(() -> new KurlyBaseException(NOT_FOUND_ORDER_SUPPORT));
    }

    public List<OrderSupport> findByOrderNumber(String orderNumber) {
        return orderSupportRepository.findByOrderNumber(orderNumber);
    }

    public List<CreateOrderSupport.Response> findAllByUserId(Long userId) {
        return orderSupportRepository.findAllByUserId(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    private CreateOrderSupport.Response toResponse(OrderSupport orderSupport) {
        return new CreateOrderSupport.Response(
                orderSupport.getType(),
                orderSupport.getTitle(),
                orderSupport.getContent(),
                orderSupport.getStatus(),
                orderSupport.getCreateAt()
        );
    }

    @Transactional
    public void updateOrderSupport(Long userId, Long id, String title, String content) {
        OrderSupport orderSupport = findByIdOrThrow(id);
        orderSupport.validateAuthor(userId);
        orderSupport.updateOrderSupport(title, content);
    }

    @Transactional
    public void updateSupportToPrepare(Long id) {
        OrderSupport orderSupport = findByIdOrThrow(id);
        orderSupport.toPreparedSupport();
    }

    @Transactional
    public void deleteOrderSupport(Long userId, Long id) {
        OrderSupport orderSupport = findByIdOrThrow(id);
        orderSupport.validateAuthor(userId);
        orderSupport.deleteSupport();
    }

    @Transactional
    public void answered(Long id, String answerContent) {
        OrderSupport orderSupport = findByIdOrThrow(id);
        orderSupport.toAnsweredSupport(answerContent);
    }
}
