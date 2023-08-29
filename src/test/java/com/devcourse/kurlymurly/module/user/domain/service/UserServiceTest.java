package com.devcourse.kurlymurly.module.user.domain.service;

import com.devcourse.kurlymurly.module.product.service.ProductFacade;
import com.devcourse.kurlymurly.module.user.domain.cart.CartRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private ProductFacade productFacade;

    @Mock
    private CartRepository cartRepository;

    @Nested
    @DisplayName("카트 테스트")
    class cartTest {
        private final Long userId = 1L;
        private final Long productId = 1L;
        private final int quantity = 2;

        @Test
        @DisplayName("주문 가능한 상품은 장바구니에 추가할 수 있다.")
        void addCart_Success() {
            // given
            willDoNothing().given(productFacade).validateOrderable(any());

            // when
            userService.addCart(userId, productId, quantity);

            // then
            then(cartRepository).should(times(1)).save(any());
        }

        @Test
        @DisplayName("삭제되었거나 품절인 상품을 장바구니에 담으려고 하면 IllegalStateException을 던진다.")
        void addCart_Fail_ByInvalidStatus() {
            // given
            willThrow(IllegalStateException.class).given(productFacade).validateOrderable(any());

            // when, then
            assertThatExceptionOfType(IllegalStateException.class)
                    .isThrownBy(() -> userService.addCart(userId, productId, quantity));
            then(cartRepository).shouldHaveNoInteractions();
        }
    }
}
