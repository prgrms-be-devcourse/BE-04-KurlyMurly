package com.devcourse.kurlymurly.domain.service;

import com.devcourse.kurlymurly.common.exception.KurlyBaseException;
import com.devcourse.kurlymurly.domain.order.support.OrderSupport;
import com.devcourse.kurlymurly.domain.order.support.OrderSupportRepository;
import com.devcourse.kurlymurly.web.order.CreateOrderSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.devcourse.kurlymurly.common.exception.ErrorCode.NOT_FOUND_ORDER_SUPPORT;

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
                orderSupport.getStatus().name(),
                orderSupport.getCreateAt()
        );
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
                orderSupport.getType().name(),
                orderSupport.getTitle(),
                orderSupport.getContent(),
                orderSupport.getStatus().name(),
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
