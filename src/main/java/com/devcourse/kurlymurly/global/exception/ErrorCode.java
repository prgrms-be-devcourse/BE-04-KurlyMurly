package com.devcourse.kurlymurly.global.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public enum ErrorCode {
    // 400
    DELETED_PRODUCT(BAD_REQUEST, "삭제된 상품입니다."),
    FAIL_USER_LOGIN(NOT_FOUND, "존재하지 않는 계정입니다."),
    NOT_CORRECT_JWT_SIGN(BAD_REQUEST,"잘못된 JWT SIGN값입니다."),
    NOT_CORRECT_JWT(BAD_REQUEST,"잘못된 JWT 토큰입니다."),
    EXPIRED_JWT_TOKEN(BAD_REQUEST,"만료된 토큰입니다."),
    NOT_SUPPORTED_JWT_TOKEN(BAD_REQUEST,"지원하지 않는 토근입니다."),

    // 404
    NOT_FOUND_REVIEW(NOT_FOUND, "존재하는 리뷰가 없습니다."),
    NOT_FOUND_REVIEW_LIKE(NOT_FOUND, "존재하는 리뷰 좋아요가 없습니다."),
    NOT_FOUND_ORDER_SUPPORT(NOT_FOUND, "존재하는 1:1 문의 내역이 없습니다."),
    NOT_FOUND_ORDER(NOT_FOUND, "존재하는 주문 내역이 없습니다."),
    NEVER_FAVORITE(NOT_FOUND, "찜 이력이 존재하지 않습니다."),
    PRODUCT_NOT_FOUND(NOT_FOUND, "존재하는 상품이 없습니다"),
    NEVER_WRITE_PRODUCT_SUPPORT(NOT_FOUND, "작성한 상품 문의가 없습니다"),
    NOT_CORRECT_PASSWORD(NOT_FOUND, "현재 비밀번호를 확인해주세요"),
    NOT_EQUAL_PASSWORD(NOT_FOUND, "동일한 비밀번호를 입력해주세요."),
    NOT_EXISTS_USER(NOT_FOUND, "존재하지 않는 회원입니다."),
    NOT_FOUND_PAYMENT(NOT_FOUND, "존재하는 결제수단이 없습니다."),
    CART_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하는 장바구니 상품이 없습니다"),

    // 409
    NOT_AUTHOR(CONFLICT, "작성자가 아닙니다."),

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
