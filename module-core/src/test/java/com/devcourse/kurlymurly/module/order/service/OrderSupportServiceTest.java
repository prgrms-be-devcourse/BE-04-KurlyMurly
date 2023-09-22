package com.devcourse.kurlymurly.module.order.service;

import com.devcourse.kurlymurly.module.order.domain.support.OrderSupport;
import com.devcourse.kurlymurly.module.order.domain.support.OrderSupportRepository;
import com.devcourse.kurlymurly.web.dto.order.support.AnswerOrderSupport;
import com.devcourse.kurlymurly.web.dto.order.support.CreateOrderSupport;
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
    private OrderSupportRepository orderSupportRepository;

    private static CreateOrderSupport.Request request;

    private final Long userId = 1L;

    private OrderSupport createOrderSupportEntity(CreateOrderSupport.Request request) {
        return new OrderSupport(userId, request.orderId(), request.orderNumber(),
                request.type(), request.title(), request.content());
    }

    private void takeOrderSupportService(OrderSupport orderSupport) {
        given(orderSupportRepository.save(any())).willReturn(orderSupport);
        orderSupportService.takeOrderSupport(userId, request);
    }

    @BeforeEach
    void setUp() {
        request = new CreateOrderSupport.Request(
                1L,
                "1234456789012",
                OrderSupport.Type.ORDER,
                "support_title",
                "support_content"
        );
    }

    @Test
    @DisplayName("주문 내역에 대한 문의를 접수한다")
    void takeOrderSupport_test() {
        // given
        OrderSupport orderSupport = createOrderSupportEntity(request);

        // mocking
        given(orderSupportRepository.save(any())).willReturn(orderSupport);

        // when
        takeOrderSupportService(orderSupport);
    }

    @Test
    @DisplayName("주문 번호에 해당하는 문의를 조회한다")
    void findByOrderId_test() {
        // given
        OrderSupport orderSupport = createOrderSupportEntity(request);

        // mocking
        given(orderSupportRepository.findByOrderNumber(any())).willReturn(List.of(orderSupport));

        // when
        takeOrderSupportService(orderSupport);
        List<OrderSupport> entity = orderSupportService.findByOrderNumber(request.orderNumber());

        // then
        assertThat(orderSupport).usingRecursiveComparison().isEqualTo(entity.get(0));
    }


    @Test
    @DisplayName("유저 id에 해당하는 문의를 조회한다")
    void findAllByUserId_test() {
        // given
        OrderSupport orderSupport = createOrderSupportEntity(request);

        // mocking
        given(orderSupportRepository.findAllByUserId(any())).willReturn(List.of(orderSupport));

        // when
        takeOrderSupportService(orderSupport);
        List<CreateOrderSupport.Response> entity = orderSupportService.findAllByUserId(userId);

        // then
        CreateOrderSupport.Response response = new CreateOrderSupport.Response(orderSupport.getType(), orderSupport.getTitle(), orderSupport.getContent(),
                orderSupport.getStatus(), orderSupport.getCreateAt());

        assertThat(response).usingRecursiveComparison().isEqualTo(entity.get(0));
    }

    @Test
    @DisplayName("문의를 준비중 상태로 변경한다")
    void updateSupportToPrepare_test() {
        // given
        OrderSupport orderSupport = createOrderSupportEntity(request);

        // mocking
        given(orderSupportRepository.findById(any())).willReturn(Optional.of(orderSupport));

        // when
        takeOrderSupportService(orderSupport);
        orderSupportService.updateSupportToPrepare(orderSupport.getId());

        // then
        Assertions.assertEquals("PREPARE", orderSupport.getStatus().name());
    }

    @Test
    @DisplayName("[관리자] 1:1 문의를 답변 처리한다.")
    void updateSupportToDone_test() {
        // given
        OrderSupport orderSupport = createOrderSupportEntity(request);
        AnswerOrderSupport.Request answerRequest = new AnswerOrderSupport.Request(
                1L,
                "문의해 주셔서 감사합니다. 해당 문의에 대한 답변입니다."
        );

        // mocking
        given(orderSupportRepository.findById(any())).willReturn(Optional.of(orderSupport));

        // when
        takeOrderSupportService(orderSupport);
        orderSupportService.answered(1L, answerRequest.content());

        // then
        Assertions.assertEquals("ANSWERED", orderSupport.getStatus().name());
    }

    @Test
    @DisplayName("문의를 삭제된 상태로 변경한다")
    void deleteOrderSupport_test() {
        // given
        OrderSupport orderSupport = createOrderSupportEntity(request);

        // mocking
        given(orderSupportRepository.findById(any())).willReturn(Optional.of(orderSupport));

        // when
        takeOrderSupportService(orderSupport);
        orderSupportService.deleteOrderSupport(1L, orderSupport.getId());

        // then
        Assertions.assertEquals("DELETED", orderSupport.getStatus().name());
    }

    @Test
    @DisplayName("문의 내용을 변경한다")
    void updateOrderSupport_test() {
        // given
        OrderSupport orderSupport = createOrderSupportEntity(request);
        String title = "updatedTitle";
        String content = "updatedContent";
        CreateOrderSupport.UpdateRequest updateRequest = new CreateOrderSupport.UpdateRequest(title, content);

        // mocking
        given(orderSupportRepository.findById(any())).willReturn(Optional.of(orderSupport));

        // when
        takeOrderSupportService(orderSupport);
        orderSupportService.updateOrderSupport(1L, orderSupport.getId(), updateRequest.title(), updateRequest.content());

        // then
        Assertions.assertEquals("updatedTitle", orderSupport.getTitle());
        Assertions.assertEquals("updatedContent", orderSupport.getContent());
    }
}
