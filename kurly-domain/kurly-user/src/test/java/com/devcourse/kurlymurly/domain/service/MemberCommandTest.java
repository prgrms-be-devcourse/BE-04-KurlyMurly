package com.devcourse.kurlymurly.domain.service;

import com.devcourse.kurlymurly.domain.user.ShippingFixture;
import com.devcourse.kurlymurly.domain.user.User;
import com.devcourse.kurlymurly.domain.user.payment.Payment;
import com.devcourse.kurlymurly.domain.user.payment.PaymentRepository;
import com.devcourse.kurlymurly.domain.user.shipping.Shipping;
import com.devcourse.kurlymurly.domain.user.shipping.ShippingRepository;
import com.devcourse.kurlymurly.web.user.RegisterPayment;
import com.devcourse.kurlymurly.web.user.UpdatePayPassword;
import com.devcourse.kurlymurly.web.user.UpdateUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.devcourse.kurlymurly.domain.user.UserFixture.USER_FIXTURE;
import static com.devcourse.kurlymurly.module.user.PaymentFixture.PAYMENT_FIXTURE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MemberCommandTest {
    @InjectMocks
    private MemberCommand memberCommand;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private ShippingRepository shippingRepository;

    private static User user;

    @BeforeEach
    void setUp() {
        user = USER_FIXTURE.toEntity();
    }

    @Nested
    @DisplayName("개인정보 변경 테스트")
    class update_test {
        private static UpdateUser.Request request;

        @BeforeEach
        void setUp() {
            request = new UpdateUser.Request("kurly1234", "murly1234", "murly1234"
                    , "sehan", "kurly@murly.com", "01094828438", "male", null);
        }

        @Test
        @DisplayName("개인정보 변경 테스트")
        void update_user() {
            // When
            memberCommand.updateUserInfo(request, "encryptedPassword", user);

            // Then
            assertThat(user.getName()).isEqualTo("sehan");
        }
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
        @DisplayName("주소 정보 변경 테스트")
        void update_address() {
            // When
            memberCommand.updateAddress(shipping, "멀리단길", "ray", "01000000000");

            // Then
            assertThat(shipping.getAddress().getDescribedAddress()).isEqualTo("컬리단길 멀리단길");
        }

        @Test
        @DisplayName("배송 요청사항 변경 테스트")
        void update_address_info() {
            // When
            memberCommand.updateAddressInfo(shipping, "sehan", "01000000000", "DOOR", "1234", "ALWAYS");

            // Then
            assertThat(shipping.getInfo().getReceiver()).isEqualTo("sehan");
        }

        @Test
        @DisplayName("주소 삭제 테스트")
        void delete_address() {
            // When
            memberCommand.deleteAddress(shipping);

            // Then
            then(shippingRepository).should(times(1)).delete(any());
        }

    }

    @Nested
    @DisplayName("결제수단 관리 테스트")
    class payment_test {
        private static Payment payment;

        @BeforeEach
        void setUp() {
            payment = PAYMENT_FIXTURE.toEntity();
        }


        @Test
        @DisplayName("신용카드 결제 수단 추가 테스트")
        void add_credit() {
            // Given
            RegisterPayment.CreditRequest request = new RegisterPayment.CreditRequest("12341234", "hana", null, "53");

            // When
            memberCommand.addCredit(1L, request);

            // Then
            then(paymentRepository).should(times(1)).save(any());
        }

        @Test
        @DisplayName("간편결제 결제 수단 추가 테스트")
        void add_easy_pay() {
            // given
            RegisterPayment.EasyPayRequest request = new RegisterPayment.EasyPayRequest("12341234", "hana");

            // when
            memberCommand.addEasyPay(1L, request);

            // then
            then(paymentRepository).should(times(1)).save(any());
        }

        @Test
        @DisplayName("결제 수단 삭제 테스트")
        void delete_payment() {
            // When
            memberCommand.deletePayment(payment);
        }

        @Test
        @DisplayName("결제 비밀번호 설정")
        void update_pay_password() {
            // Given
            UpdatePayPassword.Request request = new UpdatePayPassword.Request("123456");

            // When
            memberCommand.updatePaymentPassword(user, "encodedPayPassword");
        }
    }
}
