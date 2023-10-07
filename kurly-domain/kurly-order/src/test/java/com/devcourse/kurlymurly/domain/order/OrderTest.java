package com.devcourse.kurlymurly.domain.order;

import com.devcourse.kurlymurly.common.exception.KurlyBaseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.devcourse.kurlymurly.domain.order.Order.Status;
import static com.devcourse.kurlymurly.domain.order.OrderFixture.HEJOW_ORDER;
import static com.devcourse.kurlymurly.domain.order.OrderFixture.HEJOW_ORDER2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class OrderTest {
    @Test
    @DisplayName("주문 상품의 인덱스로 후기를 남기면 타겟 상품만 후기가 작성되어야 한다.")
    void reviewOrderLine_Success() {
        // given
        int targetLineIndex = 1;
        Order order = HEJOW_ORDER.toEntity();

        // when
        order.reviewOrderLine(targetLineIndex);

        // then
        List<OrderLine> orderLines = order.getOrderLines();
        OrderLine reviewed = orderLines.get(targetLineIndex);
        OrderLine notReviewed = orderLines.get(orderLines.size() - targetLineIndex - 1);

        assertThat(reviewed.isNotReviewed()).isFalse();
        assertThat(notReviewed.isNotReviewed()).isTrue();
    }

    @Test
    @DisplayName("주문 상품의 개수보다 큰 인덱스 값을 입력하면 예외를 던져야 한다.")
    void reviewOrderLine_Fail_ByOutOfIndex() {
        // given
        int outRangeIndex = 9999;
        Order order = HEJOW_ORDER2.toEntity();

        // when, then
        assertThatExceptionOfType(KurlyBaseException.class)
                .isThrownBy(() -> order.reviewOrderLine(outRangeIndex));
    }

    @Nested
    @DisplayName("주문 상태 패턴 테스트")
    class OrderStateTest {
        @ParameterizedTest
        @MethodSource("provideStateOrder")
        @DisplayName("주문을 다음 상태로 변경시키면 알맞는 상태로 변경되어야 한다.")
        void processingOrder_ToDelivering(Status from, Status to) {
            // given
            Order order = HEJOW_ORDER.toSpecificStateEntity(from);

            // when
            order.nextState();

            // then
            assertThat(order.getStatus()).isEqualTo(to);
        }

        private static Stream<Arguments> provideStateOrder() {
            return Stream.of(
                    Arguments.of(Status.ORDERED, Status.PROCESSING),
                    Arguments.of(Status.PROCESSING, Status.DELIVERING),
                    Arguments.of(Status.DELIVERING, Status.DELIVERED)
            );
        }

        @ParameterizedTest
        @EnumSource(value = Status.class, names = {"ORDERED", "PROCESSING"})
        @DisplayName("주문 완료되거나 출고 중인 주문은 취소할 수 있어야 한다.")
        void cancelOrder_Success_ByValidState(Status status) {
            // given
            Order order = HEJOW_ORDER.toSpecificStateEntity(status);

            // when
            order.cancel();

            // then
            assertThat(order.getStatus()).isEqualTo(Status.CANCELED);
        }

        @ParameterizedTest
        @EnumSource(value = Status.class, names = {"DELIVERING", "DELIVERED", "CANCELED"})
        @DisplayName("배송 중이거나 완료, 그리고 취소된 상품은 취소하려고 하면 예외를 던진다.")
        void cancelOrder_Fail_ByInvalidState(Status status) {
            // given
            Order order = HEJOW_ORDER.toSpecificStateEntity(status);

            // when, then
            assertThatExceptionOfType(KurlyBaseException.class)
                    .isThrownBy(order::cancel);
        }
    }
}
