package com.devcourse.kurlymurly.module.user.service;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.product.service.ProductFacade;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.module.user.domain.UserRepository;
import com.devcourse.kurlymurly.module.user.domain.cart.CartRepository;
import com.devcourse.kurlymurly.module.user.domain.payment.CreditInfo;
import com.devcourse.kurlymurly.module.user.domain.payment.Payment;
import com.devcourse.kurlymurly.module.user.domain.payment.PaymentRepository;
import com.devcourse.kurlymurly.module.user.domain.shipping.Shipping;
import com.devcourse.kurlymurly.module.user.domain.shipping.ShippingRepository;
import com.devcourse.kurlymurly.web.dto.payment.RegisterPayment;
import com.devcourse.kurlymurly.web.dto.user.JoinUser;
import com.devcourse.kurlymurly.web.dto.user.UpdateUser;
import com.devcourse.kurlymurly.web.dto.user.shipping.AddAddress;
import com.devcourse.kurlymurly.web.exception.ExistUserInfoException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.devcourse.kurlymurly.module.user.domain.User.Role.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.then;
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
    @DisplayName("개인정보 변경 테스트_비밀번호")
    void update_user_password() {
        // Given
        UpdateUser.Request request = new UpdateUser.Request("kurly1234", "murly1234", "murly1234"
                , null, null, null, null, null);

        User newUser = new User("kurly", "kurly4321", "encodePassword", "kyrly@murly.com"
                , null, "01094828438");

        doReturn("encodePassword").when(passwordEncoder).encode("kurly1234");
        doReturn("editEncodePassword").when(passwordEncoder).encode("murly1234");
        doReturn(Optional.of(newUser)).when(userRepository).findById(any());

        // When
        userService.update(1L, request);

        // Then
        assertThat(newUser.isEqualPassword("editEncodePassword")).isTrue();
        assertThat(newUser.getRole()).isEqualTo(USER);
    }

    @Test
    @DisplayName("존재하지 않는 유저를 조회할 경우 예외를 던짐")
    void update_user_ByNotExistUser() {
        // Given
        UpdateUser.Request request = new UpdateUser.Request("kurly1234", "murly1234", "murly1234"
                , "sehan", "murly@kurly.com", "01012221212", "male", null);

        doReturn(Optional.empty()).when(userRepository).findById(any());

        // When,Then
        assertThrows(KurlyBaseException.class,() -> userService.update(1L,request));
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
        assertThrows(KurlyBaseException.class,() -> userService.update(1L,request));
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
