package com.devcourse.kurlymurly.module.user.domain;

import com.devcourse.kurlymurly.module.auth.AuthRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AuthRepositoryTest {

    @Autowired
    private AuthRepository authRepository;

    @Test
    @DisplayName("동일한 아이디가 있는지 확인")
    void existsByLoginId() {
        assertThat(authRepository.existsByLoginId("kurly")).isFalse();
    }

    @Test
    @DisplayName("동일한 이메일이 있는지 확인")
    void existsByEmail() {
        assertThat(authRepository.existsByEmail("kkk@murly.com")).isFalse();
    }
}
