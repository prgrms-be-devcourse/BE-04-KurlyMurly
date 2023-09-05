package com.devcourse.kurlymurly.module.user.service;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.product.service.ProductFacade;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.module.user.domain.UserInfo;
import com.devcourse.kurlymurly.module.user.domain.UserRepository;
import com.devcourse.kurlymurly.module.user.domain.cart.Cart;
import com.devcourse.kurlymurly.module.user.domain.cart.CartRepository;
import com.devcourse.kurlymurly.module.user.domain.payment.Payment;
import com.devcourse.kurlymurly.module.user.domain.payment.PaymentRepository;
import com.devcourse.kurlymurly.module.user.domain.shipping.Shipping;
import com.devcourse.kurlymurly.module.user.domain.shipping.ShippingRepository;
import com.devcourse.kurlymurly.web.dto.payment.RegisterPayment;
import com.devcourse.kurlymurly.web.dto.product.RemoveCart;
import com.devcourse.kurlymurly.web.dto.user.JoinUser;
import com.devcourse.kurlymurly.web.dto.user.UpdateUser;
import com.devcourse.kurlymurly.web.exception.ExistUserInfoException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.devcourse.kurlymurly.module.user.domain.User.Role.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.will;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private ShippingRepository shippingRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ProductFacade productFacade;

    @Mock
    private CartRepository cartRepository;

    private static JoinUser.Request user;

    @BeforeEach
    void setUp() {
        user = new JoinUser.Request("murly1234", "kurly111", "kurly111", "sehan", "kurly@murly.com", "01094828438"
                , "male", null, "dd", "경기도 구성로");
    }

    @Test
    @DisplayName("회원가입 완료 테스트")
    void join() {
        // Given
        User newUser = new User("kurly", "kurly1234", "murly4321", "kyrly@murly.com"
                , null, "01094828438");

        doReturn(newUser).when(userRepository).save(any());
        doReturn("encryptedPassword").when(passwordEncoder).encode(any());

        // When
        userService.join(user);
    }

    @Test
    @DisplayName("비밀번호가 서로 일차히지 않으면 예외를 던짐")
    void join_fail_IllegalArgumentException() {
        // Given
        user = new JoinUser.Request("murly1234", "kurly111", "kurly1234", "sehan", "kurly@murly.com", "01094828438"
                , "male", null, "dd", "경기 구성로");

        // Then
        assertThrows(IllegalArgumentException.class, () -> userService.join(user));
    }

    @Test
    @DisplayName("아이디가 중복되면 예외를 던짐")
    void join_fail_id_ExistUserInfoException() {
        // Given
        doReturn(true).when(userRepository).existsByLoginId(any());

        // Then
        assertThrows(ExistUserInfoException.class, () -> userService.join(user));
    }

    @Test
    @DisplayName("이메일이 중복되면 예외를 던짐")
    void join_fail_email_ExistUserInfoException() {
        // Given
        doReturn(true).when(userRepository).existsByEmail(any());

        // Then
        assertThrows(ExistUserInfoException.class, () -> userService.join(user));
    }

    @Test
    @DisplayName("샛별 배송 주소 추가 테스트_샛별 배송 지역")
    void add_address_express_address() {
        // Given
        Shipping shipping = new Shipping(1L, "경기 구성 33번길", true);

        //When
        userService.addAddress(1L, "경기 구성로 33번길", false);

        // Then
        assertThat(shipping.getAddress().isExpress()).isTrue();
    }

    @Test
    @DisplayName("샛별 배송 주소 추가 테스트_샛별 배송 불가")
    void add_address_non_express_address() {
        // Given
        Shipping shipping = new Shipping(1L, "불가 컬리번길", true);

        //When
        userService.addAddress(1L, "경기 구성로 33번길", false);

        // Then
        assertThat(shipping.getAddress().isExpress()).isFalse();
    }

    @Test
    @DisplayName("신용카드 결제 수단 추가 테스트")
    void add_credit() {
        // Given
        RegisterPayment.creditRequest request = new RegisterPayment.creditRequest("12341234", "hana", null, 53);

        // When
        userService.addCredit(1L, request);

        // Then
        then(paymentRepository).should(times(1)).save(any());
    }

    @Test
    @DisplayName("간편결제 결제 수단 추가 테스트")
    void add_easy_pay() {
        // given
        RegisterPayment.easyPayRequest request = new RegisterPayment.easyPayRequest("12341234", "hana");

        // when
        userService.addEasyPay(1L, request);

        // then
        then(paymentRepository).should(times(1)).save(any());
    }

    @Test
    @DisplayName("결제 수단 조회 테스트")
    void get_payment() {
        // Given
        Payment payment = new Payment(1L, null);

        doReturn(List.of(payment)).when(paymentRepository).findAllById(Collections.singleton(1L));
        userService.getPayments(1L);

        // then
        then(paymentRepository).should(times(1)).findAllById(any());
    }

    @Test
    @DisplayName("조회 된 결제 수단이 없을 경우 예외를 던진다.")
    void get_payment_fail_ByNotFoundPayments() {
        // When
        doReturn(Collections.emptyList()).when(paymentRepository).findAllById(Collections.singleton(1L));

        // Then
        assertThrows(KurlyBaseException.class, () -> userService.getPayments(1L));
    }

    @Test
    @DisplayName("결제 수단 삭제 테스트")
    void delete_payment() {
        // Given
        Payment payment = new Payment(1L, null);

        doReturn(Optional.of(payment)).when(paymentRepository).findByUserIdAndId(1L, 1L);

        // When
        userService.deletePayment(1L,1L);

        // Then
        then(paymentRepository).should(times(1)).findByUserIdAndId(any(),any());
    }

    @Test
    @DisplayName("조회 된 결제 수단이 없을 경우 예외를 던진다.")
    void delete_payment_fail_ByNotFoundPayments() {
        // When
        doReturn(Optional.empty()).when(paymentRepository).findByUserIdAndId(any(),any());

        // Then
        assertThrows(KurlyBaseException.class, () -> userService.deletePayment(1L, 1L));
    }

    @Test
    @DisplayName("개인정보 변경 테스트_비밀번호")
    void update_user_password() {
        // Given
        UpdateUser.Request request = new UpdateUser.Request("kurly1234", "murly1234", "murly1234"
                , "sehan", "kurly@murly.com", "01094828438", "male", null);

        UserInfo info = new UserInfo(null, "sehan", "male");

        User newUser = new User("kurly", "kurly4321", "encodePassword", "kyrly@murly.com"
                , info, "01094828438");

        doReturn("editEncodePassword").when(passwordEncoder).encode(any());
        doReturn(true).when(passwordEncoder).matches(any(), any());
        doReturn(Optional.of(newUser)).when(userRepository).findById(any());

        // When
        userService.findUpdateUser(1L, request);

        // Then
        assertThat(newUser.isEqualPassword("editEncodePassword")).isTrue();
        assertThat(newUser.getRole()).isEqualTo(USER);
    }

    @Test
    @DisplayName("해당 회원이 조회되지 않으면 예외를 던짐")
    void update_fail_notExistsUser() {
        // Given
        UpdateUser.Request request = new UpdateUser.Request("kurly1234", "murly1234", "murly1234"
                , "sehan", "kurly@murly.com", "01094828438", "male", null);

        doReturn(Optional.empty()).when(userRepository).findById(any());

        // Then
        assertThrows(KurlyBaseException.class, () -> userService.findUpdateUser(1L, request));
    }

    @Test
    @DisplayName("현재 비밀번호가 일치하지 않으면 예외를 던짐")
    void update_user_ByNotCorrectPassword() {
        // Given
        UpdateUser.Request request = new UpdateUser.Request("kurly1234", "murly1234", "murly1234"
                , "sehan", "murly@kurly.com", "01012221212", "male", null);

        User newUser = new User("kurly", "kurly4321", "encodePassword", "kyrly@murly.com"
                , null, "01094828438");

        doReturn(Optional.of(newUser)).when(userRepository).findById(any());

        // When,Then
        assertThrows(KurlyBaseException.class, () -> userService.findUpdateUser(1L, request));
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
            willDoNothing().given(productFacade).validateOrderable(any());

            // when
            userService.addCart(userId, productId, quantity);

            // then
            then(cartRepository).should(times(1)).save(any());
        }

        @Test
        @DisplayName("장바구니에서 상품을 선택 삭제 할 수 있다.")
        void removeProduct_Success() {
            // given
            Cart cart = new Cart(userId, productId, quantity);

            // mocking
            willDoNothing().given(productFacade).validateOrderable(any());
            given(cartRepository.findByProductIdAndUserId(any(), any())).willReturn(Optional.of(cart));

            // when
            userService.addCart(userId, productId, quantity);
            userService.removeProduct(productId, userId);

            // then
            then(cartRepository).should(times(1)).save(any());
        }

        @Test
        @DisplayName("장바구니에서 상품을 리스트로 선택 삭제 할 수 있다.")
        void removeProductList_Success() {
            // given
            Cart cart1 = new Cart(userId, productId, quantity);
            Cart cart2 = new Cart(userId, productId + 1, quantity);
            Cart cart3 = new Cart(userId, productId + 2, quantity);
            List<Long> products = List.of(cart1.getProductId(), cart2.getProductId());
            RemoveCart.Request request = new RemoveCart.Request(products);

            // mocking
            willDoNothing().given(productFacade).validateOrderable(any());
            given(cartRepository.findByProductIdAndUserId(any(), any())).willReturn(Optional.of(cart1));
            given(cartRepository.findByProductIdAndUserId(any(), any())).willReturn(Optional.of(cart2));
            given(cartRepository.findByProductIdAndUserId(any(), any())).willReturn(Optional.of(cart3));
            willDoNothing().given(cartRepository).delete(any());
            given(cartRepository.findAllByUserId(any())).willReturn(List.of(cart3));

            // when
            userService.addCart(userId, productId, quantity);
            userService.addCart(userId, productId + 1, quantity);
            userService.addCart(userId, productId + 2, quantity);
            userService.removeProductList(request, userId);
            List<Cart> carts = cartRepository.findAllByUserId(userId);

            // then
            Assertions.assertEquals(1, carts.size());
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
