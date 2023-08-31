package com.devcourse.kurlymurly.web.dto.user;

public sealed interface LoginUser permits LoginUser.Request, LoginUser.Response {
    record Request(String loginId, String password) implements LoginUser {
    }

    record Response(String loginId, String token) implements LoginUser {
    }
}
