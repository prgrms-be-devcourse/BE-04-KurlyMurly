package com.devcourse.kurlymurly.auth;

import com.devcourse.kurlymurly.common.exception.KurlyBaseException;
import com.devcourse.kurlymurly.domain.user.User;
import com.devcourse.kurlymurly.domain.user.UserRepository;
import com.devcourse.kurlymurly.domain.user.shipping.ShippingRepository;
import com.devcourse.kurlymurly.web.user.Join;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.devcourse.kurlymurly.auth.UserFixture.USER_FIXTURE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ShippingRepository shippingRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User user;

    @BeforeEach
    void setUp() {
        user = USER_FIXTURE.toEntity();
    }

    @Nested
    @DisplayName("회원가입 테스트")
    class join {
        private static Join.Request joinRequest;

        @BeforeEach
        void setUp() {
            joinRequest = new Join.Request("murly1234", "kurly111", "kurly111", "sehan", "kurly@murly.com", "01094828438"
                    , "male", null, "dd", "경기도 구성로");
        }

        @Test
        @DisplayName("회원가입 완료 테스트")
        void join() {
            // Given
            doReturn(user).when(userRepository).save(any());
            doReturn("encryptedPassword").when(passwordEncoder).encode(any());

            // When
            authService.join(joinRequest);
        }

        @Test
        @DisplayName("아이디가 중복되면 예외를 던짐")
        void join_fail_id_ExistUserInfoException() {
            // Given
            doReturn(true).when(userRepository).existsByLoginId(any());

            // Then
            assertThrows(KurlyBaseException.class, () -> authService.join(joinRequest));
        }

        @Test
        @DisplayName("이메일이 중복되면 예외를 던짐")
        void join_fail_email_ExistUserInfoException() {
            // Given
            doReturn(true).when(userRepository).existsByEmail(any());

            // Then
            assertThrows(KurlyBaseException.class, () -> authService.join(joinRequest));
        }
    }
}
