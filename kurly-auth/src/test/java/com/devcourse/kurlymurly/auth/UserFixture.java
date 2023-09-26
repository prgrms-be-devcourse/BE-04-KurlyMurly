package com.devcourse.kurlymurly.auth;

import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.module.user.domain.UserInfo;

import java.time.LocalDate;

public enum UserFixture {
    USER_FIXTURE("수연장", "abd1234", "kurly1234", "aaaa@gmail.com",
            new UserInfo(LocalDate.now(), "문희조", "MAN"), "1234-4567");

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
        User user =  new User(name, loginId, password, email, userInfo, phoneNumber);
        user.updatePayPassword("123456");

        return user;
    }
}
