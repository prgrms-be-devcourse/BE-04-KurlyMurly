package com.devcourse.kurlymurly.module.orderSupport.service;

import com.devcourse.kurlymurly.module.order.domain.OrderSupport;
import com.devcourse.kurlymurly.module.order.domain.OrderSupportJpaRepository;
import com.devcourse.kurlymurly.module.order.service.OrderSupportService;
import com.devcourse.kurlymurly.web.dto.order.OrderCreate;
import com.devcourse.kurlymurly.web.dto.order.OrderSupportCreate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderSupportServiceTest {
    @InjectMocks
    private OrderSupportService orderSupportService;

    @Mock
    private OrderSupportJpaRepository orderSupportRepository;

    private static OrderSupportCreate.Request request;

    private OrderSupport createOrderSupportEntity(OrderSupportCreate.Request request) {
        return new OrderSupport(request.userId(), request.orderId(), request.category(), request.title(), request.content());
    }

    @BeforeEach
    void setUp() {
        request = new OrderSupportCreate.Request(1L, 1L, OrderSupport.Category.ORDER, "support_title", "support_content");
    }

    @Test
    @DisplayName("주문 내역에 대한 문의를 접수한다")
    void takeOrderSupport_test() {
        // given
        OrderSupport orderSupport = createOrderSupportEntity(request);

        // mocking
        given(orderSupportRepository.save(any())).willReturn(orderSupport);

        // when
        OrderSupport entity = orderSupportService.takeOrderSupport(request.userId(), request.orderId(), request.category(),
                request.title(), request.content());

        // then
        assertThat(orderSupport).usingRecursiveComparison().isEqualTo(entity);
    }

    @Test
    @DisplayName("주문 id에 해당하는 문의를 조회한다")
    void findByOrderId_test() {
        // given
        OrderSupport orderSupport = createOrderSupportEntity(request);

        // mocking
        given(orderSupportRepository.save(any())).willReturn(orderSupport);
        given(orderSupportRepository.findByOrderId(any())).willReturn(Optional.of(orderSupport));

        // when
        orderSupportService.takeOrderSupport(request.userId(), request.orderId(), request.category(),
                request.title(), request.content());
        OrderSupport entity = orderSupportService.findByOrderId(request.orderId());

        // then
        assertThat(orderSupport).usingRecursiveComparison().isEqualTo(entity);
    }

    @Test
    @DisplayName("유저 id에 해당하는 문의를 조회한다")
    void findAllByUserId_test() {
        // given
        OrderSupport orderSupport = createOrderSupportEntity(request);

        // mocking
        given(orderSupportRepository.save(any())).willReturn(orderSupport);
        given(orderSupportRepository.findAllByUserId(any())).willReturn(Optional.of(List.of(orderSupport)));

        // when
        orderSupportService.takeOrderSupport(request.userId(), request.orderId(), request.category(),
                request.title(), request.content());
        List<OrderSupport> entity = orderSupportService.findAllByUserId(request.userId());

        // then
        assertThat(orderSupport).usingRecursiveComparison().isEqualTo(entity.get(0));
    }

    @Test
    @DisplayName("문의를 준비중 상태로 변경한다")
    void updateSupportToPrepare_test() {
        // given
        OrderSupport orderSupport = createOrderSupportEntity(request);

        // mocking
        given(orderSupportRepository.save(any())).willReturn(orderSupport);
        given(orderSupportRepository.findById(any())).willReturn(Optional.of(orderSupport));

        // when
        OrderSupport entity = orderSupportService.takeOrderSupport(request.userId(), request.orderId(), request.category(),
                request.title(), request.content());
        orderSupportService.updateSupportToPrepare(entity.getId());

        // then
        Assertions.assertEquals("PREPARE", entity.getStatus().toString());
    }

    @Test
    @DisplayName("문의를 작성중 상태로 변경한다")
    void updateSupportToStart_test() {
        // given
        OrderSupport orderSupport = createOrderSupportEntity(request);

        // mocking
        given(orderSupportRepository.save(any())).willReturn(orderSupport);
        given(orderSupportRepository.findById(any())).willReturn(Optional.of(orderSupport));

        // when
        OrderSupport entity = orderSupportService.takeOrderSupport(request.userId(), request.orderId(), request.category(),
                request.title(), request.content());
        orderSupportService.updateSupportToStart(entity.getId());

        // then
        Assertions.assertEquals("START", entity.getStatus().toString());
    }

    @Test
    @DisplayName("문의를 답변완료 상태로 변경한다")
    void updateSupportToDone_test() {
        // given
        OrderSupport orderSupport = createOrderSupportEntity(request);

        // mocking
        given(orderSupportRepository.save(any())).willReturn(orderSupport);
        given(orderSupportRepository.findById(any())).willReturn(Optional.of(orderSupport));

        // when
        OrderSupport entity = orderSupportService.takeOrderSupport(request.userId(), request.orderId(), request.category(),
                request.title(), request.content());
        orderSupportService.updateSupportToDone(entity.getId());

        // then
        Assertions.assertEquals("DONE", entity.getStatus().toString());
    }

    @Test
    @DisplayName("문의를 삭제된 상태로 변경한다")
    void deleteOrderSupport_test() {
        // given
        OrderSupport orderSupport = createOrderSupportEntity(request);

        // mocking
        given(orderSupportRepository.save(any())).willReturn(orderSupport);
        given(orderSupportRepository.findById(any())).willReturn(Optional.of(orderSupport));

        // when
        OrderSupport entity = orderSupportService.takeOrderSupport(request.userId(), request.orderId(), request.category(),
                request.title(), request.content());
        orderSupportService.deleteOrderSupport(entity.getId());

        // then
        Assertions.assertEquals("DELETED", entity.getStatus().toString());
    }

    @Test
    @DisplayName("문의 내용을 변경한다")
    void updateOrderSupport_test() {
        // given
        OrderSupport orderSupport = createOrderSupportEntity(request);
        String title = "updatedTitle";
        String content = "updatedContent";
        OrderSupportCreate.UpdateRequest updateRequest = new OrderSupportCreate.UpdateRequest(title, content);

        // mocking
        given(orderSupportRepository.save(any())).willReturn(orderSupport);
        given(orderSupportRepository.findById(any())).willReturn(Optional.of(orderSupport));

        // when
        OrderSupport entity = orderSupportService.takeOrderSupport(request.userId(), request.orderId(), request.category(),
                request.title(), request.content());
        orderSupportService.updateOrderSupport(entity.getId(), updateRequest.title(), updateRequest.content());

        // then
        Assertions.assertEquals("updatedTitle", entity.getTitle());
        Assertions.assertEquals("updatedContent", entity.getContent());
    }
}
