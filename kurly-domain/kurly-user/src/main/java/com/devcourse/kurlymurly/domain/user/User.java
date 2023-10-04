package com.devcourse.kurlymurly.domain.user;

import com.devcourse.kurlymurly.data.BaseEntity;
import com.devcourse.kurlymurly.domain.user.strategy.Reward;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    public enum Role {ROLE_USER, ROLE_ADMIN}

    public enum UserStatus {CANCEL, NORMAL}

    public enum Tier {
        THE_PURPLE,
        PURPLE,
        LAVENDER,
        WHITE,
        FRIENDS;
    }

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 50)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Tier tier;

    @Column(nullable = false, length = 50)
    private String email;

    @Embedded
    private UserInfo info;

    private String payPassword;

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @Column(nullable = false)
    private Integer reward;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 10)
    private UserStatus status;

    protected User() {
    }

    public User(String name, String loginId, String password, String email, UserInfo info, String phoneNumber) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.tier = Tier.THE_PURPLE;
        this.email = email;
        this.payPassword = null;
        this.reward = 0;
        this.info = info;
        this.phoneNumber = phoneNumber;
        this.role = Role.ROLE_USER;
        this.status = UserStatus.NORMAL;
    }

    public void update(String name, String password, String email, String sex, LocalDate birth, String phoneNumber) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        info.update(birth, sex);
    }

    public void updatePayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public int saveReward(Reward reward, int totalPrice) {
        int totalReward = reward.saveReward(totalPrice);

        return this.reward += totalReward;
    }

    public String getName() {
        return name;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }

    public User.Role getRole() {
        return this.role;
    }
}

