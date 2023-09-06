package com.devcourse.kurlymurly.web.dto.user;

public sealed interface LoginUser permits LoginUser.Request {
    record Request(String loginId, String password) implements LoginUser {
    }
}
