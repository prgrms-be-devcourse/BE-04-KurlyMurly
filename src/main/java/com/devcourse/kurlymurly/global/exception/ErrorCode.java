package com.devcourse.kurlymurly.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // 400

    // 404
    NEVER_FAVORITE(HttpStatus.NOT_FOUND, "찜 이력이 존재하지 않습니다."),

    // 409

    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 문제입니다. 관리자에게 문의바랍니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
