package com.devcourse.kurlymurly.web.dto.user;

import java.util.Date;

public sealed interface JoinUser permits JoinUser.Request {
    record Request(String loginId, String password, String checkPassword, String name, String email, String phoneNumber
            , String sex, Date birth, String recommender, String roadAddress) implements JoinUser {
    }
}
