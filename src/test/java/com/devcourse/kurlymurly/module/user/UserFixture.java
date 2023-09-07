package com.devcourse.kurlymurly.module.user;

import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.module.user.domain.UserInfo;

import java.util.Date;

public enum UserFixture {
    USER_FIXTURE("수연장", "abd1234", "11", "aaaa@gmail.com",
            new UserInfo(new Date(System.currentTimeMillis()), "문희조", "MAN"), "1234-4567");정

    private final String name;
    private final String loginId;
    private final String password;
    private final String email;
    private final UserInfo userInfo;
    private final String phoneNumber;

    UserFixture(String name, String loginId, String password, String email, UserInfo userInfo, String phoneNumber) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.userInfo = userInfo;
        this.phoneNumber = phoneNumber;
    }

    public User toEntity() {
        return new User(name, loginId, password, email, userInfo, phoneNumber);
    }
}
