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
import com.devcourse.kurlymurly.web.dto.user.shipping.GetAddress;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.devcourse.kurlymurly.module.user.domain.User.Role.*;
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
    @DisplayName("회원에게 등록된 배송주소들을 가져온다.")
    void get_addresses() {
        // Given
        Shipping shipping1 = new Shipping(1L, "컬리단길", true);
        Shipping shipping2 = new Shipping(1L, "컬리단길", true);

        doReturn(List.of(shipping1, shipping2)).when(shippingRepository).findAllByUserId(any());

        // When
        List<GetAddress.Response> addressList = userService.getAddress(1L);

        // Then
        assertThat(addressList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("주소 정보 변경 테스트")
    void update_address() {
        // Given
        Shipping shipping = new Shipping(1L, "컬리단길", true);

        doReturn(Optional.of(shipping)).when(shippingRepository).findByIdAndUserId(any(), any());

        // When
        userService.updateAddress(1L, 1L, "멀리단길", "regyu jo", "01000000000");

        // Then
        then(shippingRepository).should(times(1)).findByIdAndUserId(any(), any());
    }

    @Test
    @DisplayName("존재하지 않는 주소를 변경할 경우 예외를 던짐")
    void update_address_byAddressNotFound() {
        // Given
        doReturn(Optional.empty()).when(shippingRepository).findByIdAndUserId(any(), any());

        // When , Then
        assertThrows(KurlyBaseException.class, () -> userService.updateAddress(1L, 1L, "멀리단길", "regyu jo", "01000000000"));
    }

    @Test
    @DisplayName("주소 삭제 테스트")
    void delete_address() {
        // Given
        Shipping shipping = new Shipping(1L, "컬리단길", true);

        doReturn(Optional.of(shipping)).when(shippingRepository).findByIdAndUserId(any(), any());

        // When
        userService.deleteAddress(1L, 1L);

        // Then
        then(shippingRepository).should(times(1)).findByIdAndUserId(any(), any());
        then(shippingRepository).should(times(1)).delete(any());
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
        userService.deletePayment(1L, 1L);

        // Then
        then(paymentRepository).should(times(1)).findByUserIdAndId(any(), any());
    }

    @Test
    @DisplayName("조회 된 결제 수단이 없을 경우 예외를 던진다.")
    void delete_payment_fail_ByNotFoundPayments() {
        // When
        doReturn(Optional.empty()).when(paymentRepository).findByUserIdAndId(any(), any());

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
        assertThat(newUser.getRole()).isEqualTo(ROLE_USER);
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
        void removeCartItem_Success() {
            // given
            Cart cart = new Cart(userId, productId, quantity);

            // mocking
            willDoNothing().given(productFacade).validateOrderable(any());
            given(cartRepository.findById(any())).willReturn(Optional.of(cart));

            // when
            userService.addCart(userId, productId, quantity);
            userService.removeCartItem(1L);

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
            userService.addCart(userId, productId, quantity);
            userService.addCart(userId, productId + 1, quantity);
            userService.addCart(userId, productId + 2, quantity);
            userService.removeCartItemList(request.cartIds());
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
            willDoNothing().given(productFacade).validateOrderable(any());
            given(cartRepository.findById(any())).willReturn(Optional.of(cart));

            // when
            userService.addCart(userId, productId, quantity);
            userService.changeItemQuantity(1L, true);

            // then
            then(cartRepository).should(times(1)).save(any());
        }

        @Test
        @DisplayName("장바구니 상품 수량을 감소 시킬 수 있다.")
        void decreaseItemQuantity_Success() {
            // given
            Cart cart = new Cart(userId, productId, quantity);

            // mocking
            willDoNothing().given(productFacade).validateOrderable(any());
            given(cartRepository.findById(any())).willReturn(Optional.of(cart));

            // when
            userService.addCart(userId, productId, quantity);
            userService.changeItemQuantity(1L, false);

            // then
            then(cartRepository).should(times(1)).save(any());
        }

        @Test
        @DisplayName("장바구니 상품 수량은 1개 미만일 수 없다.")
        void decreaseItemQuantity_Fail() {
            // given
            Cart cart = new Cart(userId, productId, 1);

            // mocking
            willDoNothing().given(productFacade).validateOrderable(any());
            given(cartRepository.findById(any())).willReturn(Optional.of(cart));

            // when, then
            userService.addCart(userId, productId, quantity);
            then(cartRepository).should(times(1)).save(any());
            assertThatExceptionOfType(KurlyBaseException.class)
                    .isThrownBy(() -> userService.changeItemQuantity(1L, false));
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