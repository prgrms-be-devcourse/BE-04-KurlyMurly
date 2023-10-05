package com.devcourse.kurlymurly.domain.order;

import com.devcourse.kurlymurly.common.exception.KurlyBaseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.devcourse.kurlymurly.domain.OrderFixture.HEJOW_ORDER;
import static com.devcourse.kurlymurly.domain.OrderFixture.HEJOW_ORDER2;
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

}
