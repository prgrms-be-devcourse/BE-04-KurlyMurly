package com.devcourse.kurlymurly.global.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public enum ErrorCode {
    // 400
    DELETED_PRODUCT(BAD_REQUEST, "삭제된 상품입니다."),

    // 404
    PRODUCT_NOT_FOUND(NOT_FOUND, "존재하지 않는 상품입니다."),
    NEVER_FAVORITE(NOT_FOUND, "찜 이력이 존재하지 않습니다."),

    // 409

    // 500
    KURLY_SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 내부 문제입니다. 관리자에게 문의바랍니다."),
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
