package com.devcourse.kurlymurly.web.dto.user;

public sealed interface CheckId permits CheckId.Request{
    record Request(String loginId) implements CheckId {
    }
}
