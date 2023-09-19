package com.devcourse.kurlymurly.module.user.domain;

import com.devcourse.kurlymurly.module.user.domain.strategy.Friends;
import com.devcourse.kurlymurly.module.user.domain.strategy.Lavender;
import com.devcourse.kurlymurly.module.user.domain.strategy.Purple;
import com.devcourse.kurlymurly.module.user.domain.strategy.Reward;
import com.devcourse.kurlymurly.module.user.domain.strategy.White;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("kurly", "kurly1234", "murly4321", "kyrly@murly.com"
                , null, "01094828438");
    }

    @Test
    @DisplayName("등급에 따른 적립금 지급 테스트_프렌즈")
    void save_reward_friends() {
        // Given
        Reward reward = new Friends();

        // When
        int totalReward = user.saveReward(reward, 10000);

        // Then
        assertThat(totalReward).isEqualTo(100);
    }

    @Test
    @DisplayName("등급에 따른 적립금 지급 테스트_화이트")
    void save_reward_white() {
        // Given
        Reward reward = new White();

        // When
        int totalReward = user.saveReward(reward, 10000);

        // Then
        assertThat(totalReward).isEqualTo(300);
    }

    @Test
    @DisplayName("등급에 따른 적립금 지급 테스트_라벤더")
    void save_reward_lavender() {
        // Given
        Reward reward = new Lavender();

        // When
        int totalReward = user.saveReward(reward, 10000);

        // Then
        assertThat(totalReward).isEqualTo(500);
    }

    @Test
    @DisplayName("등급에 따른 적립금 지급 테스트_퍼플")
    void save_reward_purple() {
        // Given
        Reward reward = new Purple();

        // When
        int totalReward = user.saveReward(reward, 10000);

        // Then
        assertThat(totalReward).isEqualTo(700);
    }
}
