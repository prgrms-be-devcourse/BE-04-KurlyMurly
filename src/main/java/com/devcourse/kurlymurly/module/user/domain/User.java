package com.devcourse.kurlymurly.module.user.domain;

import com.devcourse.kurlymurly.module.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Entity
@DynamicUpdate
@Table(name = "users")
public class User extends BaseEntity {
    protected User() {
    }

    public User(String name, String loginId, String password, String email, UserInfo info, String phoneNumber) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.tier = Tier.THE_PURPLE;
        this.email = email;
        this.info = info;
        this.payPassword = null;
        this.phoneNumber = phoneNumber;
        this.role = Role.USER;
        this.status = UserStatus.NORMAL;
    }

    public void update(String name, String password, String email, String sex, Date birth, String phoneNumber) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        info.update(birth,sex);
    }

    public enum Role {USER, ADMIN}

    public enum UserStatus {CANCEL, NORMAL}

    public enum Tier {
        THE_PURPLE,
        PURPLE,
        LAVENDER,
        WHITE,
        FRIENDS;
    }

    public boolean isEqualPassword(String password) {
        return this.password.equals(password);
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

    @Column(length = 50)
    private String payPassword;

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 10)
    private UserStatus status;

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public UserInfo getInfo() {
        return info;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }
}
