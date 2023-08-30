package com.devcourse.kurlymurly.web.dto.user;

import java.util.Date;

public sealed interface CheckEmail permits CheckEmail.Request {
    record Request(String email) implements CheckEmail {
    }
}
