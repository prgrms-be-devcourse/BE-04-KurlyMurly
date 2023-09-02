package com.devcourse.kurlymurly.global.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public enum ErrorCode {
    // 400
    DELETED_PRODUCT(BAD_REQUEST, "삭제된 상품입니다."),

    // 404
    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND, "존재하는 리뷰가 없습니다."),
    NOT_FOUND_REVIEW_LIKE(HttpStatus.NOT_FOUND, "존재하는 리뷰 좋아요가 없습니다."),
    NOT_FOUND_ORDER_SUPPORT(HttpStatus.NOT_FOUND, "존재하는 1:1 문의 내역이 없습니다."),
    NOT_FOUND_ORDER(HttpStatus.NOT_FOUND, "존재하는 주문 내역이 없습니다."),
    NEVER_FAVORITE(HttpStatus.NOT_FOUND, "찜 이력이 존재하지 않습니다."),
    NOT_CORRECT_PASSWORD(HttpStatus.NOT_FOUND, "현재 비밀번호를 확인해주세요"),
    NOT_EQUAL_PASSWORD(HttpStatus.NOT_FOUND, "동일한 비밀번호를 입력해주세요."),

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
