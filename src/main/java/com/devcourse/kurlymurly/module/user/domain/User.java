package com.devcourse.kurlymurly.module.user.domain;

import com.devcourse.kurlymurly.module.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    public enum Role { ROLE_USER, ROLE_ADMIN }

    public enum UserStatus { CANCEL, NORMAL }

    public enum Tier {
        THE_PURPLE,
        PURPLE,
        LAVENDER,
        WHITE,
        FRIENDS;
    }

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
        this.role = Role.ROLE_USER;
        this.status = UserStatus.NORMAL;
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
}
