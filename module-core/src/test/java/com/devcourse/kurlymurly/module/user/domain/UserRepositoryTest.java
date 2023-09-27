package com.devcourse.kurlymurly.module.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("동일한 아이디가 있는지 확인")
    void existsByLoginId() {
        assertThat(userRepository.existsByLoginId("kurly")).isFalse();
    }

    @Test
    @DisplayName("동일한 이메일이 있는지 확인")
    void existsByEmail() {
        assertThat(userRepository.existsByEmail("kkk@murly.com")).isFalse();
    }
}
