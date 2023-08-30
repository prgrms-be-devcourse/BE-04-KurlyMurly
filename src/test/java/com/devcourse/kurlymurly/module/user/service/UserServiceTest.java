package com.devcourse.kurlymurly.module.user.service;

import com.devcourse.kurlymurly.module.user.domain.UserRepository;
import com.devcourse.kurlymurly.web.dto.user.JoinUser;
import com.devcourse.kurlymurly.web.exception.ExistUserInfoException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private static JoinUser.Request user;

    @BeforeEach
    void setUp() {
        user = new JoinUser.Request("murly1234", "kurly111", "kurly111", "sehan", "kurly@murly.com", "01094828438"
                , "male", null, "dd", "경기도 기흥구", "경기도 구성로");
    }

    @Test
    @DisplayName("회원가입 완료 테스트")
    void join() {
        // Given
        doReturn(null).when(userRepository).save(any());
        doReturn("encryptedPassword").when(passwordEncoder).encode(any());

        // When
        userService.join(user);
    }

    @Test
    @DisplayName("비밀번호가 서로 일차히지 않으면 예외를 던짐")
    void join_fail_IllegalArgumentException() {
        // Given
        user = new JoinUser.Request("murly1234", "kurly111", "kurly1234", "sehan", "kurly@murly.com", "01094828438"
                , "male", null, "dd", "경기도 기흥구", "경기도 구성로");

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
}
