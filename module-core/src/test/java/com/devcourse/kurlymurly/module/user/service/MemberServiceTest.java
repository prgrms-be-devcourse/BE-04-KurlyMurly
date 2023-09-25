package com.devcourse.kurlymurly.module.user.service;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.user.ShippingFixture;
import com.devcourse.kurlymurly.module.user.UserFixture;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.module.auth.AuthRepository;
import com.devcourse.kurlymurly.module.user.domain.cart.Cart;
import com.devcourse.kurlymurly.module.user.domain.cart.CartRepository;
import com.devcourse.kurlymurly.module.user.domain.payment.Payment;
import com.devcourse.kurlymurly.module.user.domain.payment.PaymentRepository;
import com.devcourse.kurlymurly.module.user.domain.shipping.Shipping;
import com.devcourse.kurlymurly.module.user.domain.shipping.ShippingRepository;
import com.devcourse.kurlymurly.web.dto.user.cart.RemoveCart;
import com.devcourse.kurlymurly.web.dto.user.payment.RegisterPayment;
import com.devcourse.kurlymurly.web.dto.user.payment.UpdatePayPassword;
import com.devcourse.kurlymurly.web.dto.product.RemoveCart;
import com.devcourse.kurlymurly.web.dto.user.Join;
import com.devcourse.kurlymurly.web.dto.user.UpdateUser;
import com.devcourse.kurlymurly.web.dto.user.shipping.GetAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @InjectMocks
    private MemberService memberService;

    @Mock
    private AuthRepository authRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private ShippingRepository shippingRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CartRepository cartRepository;

    private static User user;

    @BeforeEach
    void setUp() {
        user = UserFixture.USER_FIXTURE.toEntity();
    }

    @Nested
    @DisplayName("배송지 관리 테스트")
    class shipping_test {
        private static Shipping shipping;

        @BeforeEach
        void setUp() {
            shipping = ShippingFixture.SHIPPING_FIXTURE.toEntity();
        }

        @Test
        @DisplayName("샛별 배송 주소 추가 테스트_샛별 배송 지역")
        void add_address_express_address() {
            // Given
            Shipping shipping = new Shipping(1L, "경기 구성 33번길", true);

            // When , Then
            assertThat(shipping.getAddress().isExpress()).isTrue();
        }

        @Test
        @DisplayName("샛별 배송 주소 추가 테스트_샛별 배송 불가")
        void add_address_non_express_address() {
            // Given
            Shipping shipping = new Shipping(1L, "불가 컬리번길", true);

            // When , Then
            assertThat(shipping.getAddress().isExpress()).isFalse();
        }

        @Test
        @DisplayName("회원에게 등록된 배송주소들을 가져온다.")
        void get_addresses() {
            // Given
            Shipping shipping2 = new Shipping(1L, "컬리단길", true);

            doReturn(List.of(shipping, shipping2)).when(shippingRepository).findAllByUserId(any());

            // When
            List<GetAddress.Response> addressList = memberService.getAddress(1L);

            // Then
            assertThat(addressList.size()).isEqualTo(2);
        }

        @Test
        @DisplayName("주소 정보 변경 테스트")
        void update_address() {
            // Given
            doReturn(Optional.of(shipping)).when(shippingRepository).findByIdAndUserId(any(), any());

            // When
            memberService.updateAddress(1L, 1L, "멀리단길", "regyu jo", "01000000000");

            // Then
            then(shippingRepository).should(times(1)).findByIdAndUserId(any(), any());
        }

        @Test
        @DisplayName("배송 요청사항 변경 테스트")
        void update_address_info() {
            // Given
            doReturn(Optional.of(shipping)).when(shippingRepository).findByIdAndUserId(any(), any());

            // When
            memberService.updateAddressInfo(1L, 1L, "세한", "01000000000", "DOOR", "1234", "ALWAYS");
            // Then
            then(shippingRepository).should(times(1)).findByIdAndUserId(any(), any());
        }

        @Test
        @DisplayName("존재하지 않는 주소를 변경할 경우 예외를 던짐")
        void update_address_byAddressNotFound() {
            // Given
            doReturn(Optional.empty()).when(shippingRepository).findByIdAndUserId(any(), any());

            // When , Then
            assertThrows(KurlyBaseException.class, () -> memberService.updateAddress(1L, 1L, "멀리단길", "regyu jo", "01000000000"));
        }

        @Test
        @DisplayName("주소 삭제 테스트")
        void delete_address() {
            // Given
            doReturn(Optional.of(shipping)).when(shippingRepository).findByIdAndUserId(any(), any());

            // When
            memberService.deleteAddress(1L, 1L);

            // Then
            then(shippingRepository).should(times(1)).findByIdAndUserId(any(), any());
            then(shippingRepository).should(times(1)).delete(any());
        }

    }

    @Nested
    @DisplayName("결제수단 관리 테스트")
    class payment_test {

        @Test
        @DisplayName("신용카드 결제 수단 추가 테스트")
        void add_credit() {
            // Given
            RegisterPayment.creditRequest request = new RegisterPayment.creditRequest("12341234", "hana", null, "53");

            // When
            memberService.addCredit(1L, request);

            // Then
            then(paymentRepository).should(times(1)).save(any());
        }

        @Test
        @DisplayName("간편결제 결제 수단 추가 테스트")
        void add_easy_pay() {
            // given
            RegisterPayment.easyPayRequest request = new RegisterPayment.easyPayRequest("12341234", "hana");

            // when
            memberService.addEasyPay(1L, request);

            // then
            then(paymentRepository).should(times(1)).save(any());
        }

        @Test
        @DisplayName("결제 수단 조회 테스트")
        void get_payment() {
            // Given
            Payment payment = new Payment(1L, null);

            doReturn(List.of(payment)).when(paymentRepository).findAllByUserId(1L);
            memberService.getPayments(1L);

            // then
            then(paymentRepository).should(times(1)).findAllByUserId(any());
        }

        @Test
        @DisplayName("결제 수단 삭제 테스트")
        void delete_payment() {
            // Given
            Payment payment = new Payment(1L, null);

            doReturn(Optional.of(payment)).when(paymentRepository).findByUserIdAndId(1L, 1L);

            // When
            memberService.deletePayment(1L, 1L);

            // Then
            then(paymentRepository).should(times(1)).findByUserIdAndId(any(), any());
        }

        @Test
        @DisplayName("조회 된 결제 수단이 없을 경우 예외를 던진다.")
        void delete_payment_fail_ByNotFoundPayments() {
            // When
            doReturn(Optional.empty()).when(paymentRepository).findByUserIdAndId(any(), any());

            // Then
            assertThrows(KurlyBaseException.class, () -> memberService.deletePayment(1L, 1L));
        }

        @Test
        @DisplayName("결제 비밀번호 설정")
        void update_pay_password() {
            // Given
            UpdatePayPassword.Request request = new UpdatePayPassword.Request("123456");

            doReturn(Optional.of(user)).when(authRepository).findById(any());

            // When
            memberService.updatePaymentPassword(1L, request.payPassword());

            // Then
            then(authRepository).should(times(1)).findById(any());
        }

        @Test
        @DisplayName("존재하지 않는 유저를 조회할 경우 예외를 던짐")
        void update_pay_password_notFoundUser() {
            // Given
            UpdatePayPassword.Request request = new UpdatePayPassword.Request("123456");

            doReturn(Optional.empty()).when(authRepository).findById(any());

            // When , Then
            assertThrows(KurlyBaseException.class, () -> memberService.updatePaymentPassword(1L, request.payPassword()));
        }

    }

    @Nested
    @DisplayName("개인정보 변경 테스트")
    class update {
        private static UpdateUser.Request request;

        @BeforeEach
        void setUp() {
            request = new UpdateUser.Request("kurly1234", "murly1234", "murly1234"
                    , "sehan", "kurly@murly.com", "01094828438", "male", null);
        }

        @Test
        @DisplayName("개인정보 변경 테스트_비밀번호")
        void update_user_password() {
            // Given
            doReturn(true).when(passwordEncoder).matches(any(), any());
            doReturn(Optional.of(user)).when(authRepository).findById(any());

            // When
            memberService.findUpdateUser(1L, request);

            // Then
            assertThat(user.validatePassword(request.password(),passwordEncoder)).isTrue();
        }

        @Test
        @DisplayName("해당 회원이 조회되지 않으면 예외를 던짐")
        void update_fail_notExistsUser() {
            // Given
            doReturn(Optional.empty()).when(authRepository).findById(any());

            // Then
            assertThrows(KurlyBaseException.class, () -> memberService.findUpdateUser(1L, request));
        }
    }

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

            // when
            memberService.addCart(userId, productId, quantity);

            // then
            then(cartRepository).should(times(1)).save(any());
        }

        @Test
        @DisplayName("장바구니에서 상품을 선택 삭제 할 수 있다.")
        void removeCartItem_Success() {
            // given
            Cart cart = new Cart(userId, productId, quantity);

            // mocking
            given(cartRepository.findById(any())).willReturn(Optional.of(cart));

            // when
            memberService.addCart(userId, productId, quantity);
            memberService.removeCartItem(1L);

            // then
            then(cartRepository).should(times(1)).save(any());
        }

        @Test
        @DisplayName("장바구니에서 상품을 리스트로 선택 삭제 할 수 있다.")
        void removeCartItemList_Success() {
            // given
            Cart cart1 = new Cart(userId, productId, quantity);
            Cart cart2 = new Cart(userId, productId + 1, quantity);
            Cart cart3 = new Cart(userId, productId + 2, quantity);
            List<Long> products = List.of(1L, 2L);
            RemoveCart.Request request = new RemoveCart.Request(products);

            // mocking
            given(cartRepository.findAllById(any())).willReturn(List.of(cart1, cart2));
            willDoNothing().given(cartRepository).deleteAllInBatch(any());
            given(cartRepository.findAllByUserId(any())).willReturn(List.of(cart3));

            // when
            memberService.addCart(userId, productId, quantity);
            memberService.addCart(userId, productId + 1, quantity);
            memberService.addCart(userId, productId + 2, quantity);
            memberService.removeCartItemList(request.cartIds());
            List<Cart> carts = cartRepository.findAllByUserId(userId);

            // then
            assertEquals(1, carts.size());
        }

        @Test
        @DisplayName("장바구니 상품 수량을 증가 시킬 수 있다.")
        void increaseItemQuantity_Success() {
            // given
            Cart cart = new Cart(userId, productId, quantity);

            // mocking
            given(cartRepository.findById(any())).willReturn(Optional.of(cart));

            // when
            memberService.addCart(userId, productId, quantity);
            memberService.changeItemQuantity(1L, true);

            // then
            then(cartRepository).should(times(1)).save(any());
        }

        @Test
        @DisplayName("장바구니 상품 수량을 감소 시킬 수 있다.")
        void decreaseItemQuantity_Success() {
            // given
            Cart cart = new Cart(userId, productId, quantity);

            // mocking
            given(cartRepository.findById(any())).willReturn(Optional.of(cart));

            // when
            memberService.addCart(userId, productId, quantity);
            memberService.changeItemQuantity(1L, false);

            // then
            then(cartRepository).should(times(1)).save(any());
        }

        @Test
        @DisplayName("장바구니 상품 수량은 1개 미만일 수 없다.")
        void decreaseItemQuantity_Fail() {
            // given
            Cart cart = new Cart(userId, productId, 1);

            // mocking
            given(cartRepository.findById(any())).willReturn(Optional.of(cart));

            // when, then
            memberService.addCart(userId, productId, quantity);
            then(cartRepository).should(times(1)).save(any());
            assertThatExceptionOfType(KurlyBaseException.class)
                    .isThrownBy(() -> memberService.changeItemQuantity(1L, false));
        }

//        @Test
//        @DisplayName("삭제되었거나 품절인 상품을 장바구니에 담으려고 하면 IllegalStateException을 던진다.")
//        void addCart_Fail_ByInvalidStatus() {
//            // given
//
//            // when, then
//            assertThatExceptionOfType(IllegalStateException.class)
//                    .isThrownBy(() -> userService.addCart(userId, productId, quantity));
//            then(cartRepository).shouldHaveNoInteractions();
//        }
    }
}
