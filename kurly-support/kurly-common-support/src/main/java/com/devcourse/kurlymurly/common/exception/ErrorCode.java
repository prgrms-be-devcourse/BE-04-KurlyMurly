package com.devcourse.kurlymurly.common.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

public enum ErrorCode {
    // 400
    CANCELED_ORDER(BAD_REQUEST, "이미 취소된 주문입니다."),
    DELIVERED_ORDER(BAD_REQUEST, "이미 배송된 주문입니다."),
    DELETED_PRODUCT(BAD_REQUEST, "삭제된 상품입니다."),
    NOT_CORRECT_QUANTITY(BAD_REQUEST, "상품의 최소 수량은 1개입니다."),
    CLIENT_INPUT_INVALID(BAD_REQUEST, null),
    INORDERABLE_PRODUCT(BAD_REQUEST, "주문할 수 없는 상품입니다."),
    INVALID_ORDER_LINE_INDEX(BAD_REQUEST, "주문 상품 인덱스로 잘못된 값이 들어왔습니다."),

    // 401
    NOT_CORRECT_PAY_PASSWORD(UNAUTHORIZED, "잘못된 결제 비밀번호입니다."),
    NOT_CORRECT_JWT_SIGN(UNAUTHORIZED, "잘못된 JWT SIGN값입니다."),
    NOT_CORRECT_JWT(UNAUTHORIZED, "잘못된 JWT 토큰입니다."),
    EXPIRED_JWT_TOKEN(UNAUTHORIZED, "만료된 토큰입니다."),
    NOT_SUPPORTED_JWT_TOKEN(UNAUTHORIZED, "지원하지 않는 토큰입니다."),
    NOT_AUTHORIZED_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다."),

    // 404
    NOT_FOUND_REVIEW(NOT_FOUND, "존재하는 리뷰가 없습니다."),
    NOT_FOUND_REVIEW_LIKE(NOT_FOUND, "존재하는 리뷰 좋아요가 없습니다."),
    NOT_FOUND_ORDER_SUPPORT(NOT_FOUND, "존재하는 1:1 문의 내역이 없습니다."),
    ORDER_NOT_FOUND(NOT_FOUND, "존재하는 주문 내역이 없습니다."),
    NEVER_FAVORITE(NOT_FOUND, "찜 이력이 존재하지 않습니다."),
    PRODUCT_NOT_FOUND(NOT_FOUND, "존재하는 상품이 없습니다"),
    NEVER_WRITE_PRODUCT_SUPPORT(NOT_FOUND, "작성한 상품 문의가 없습니다"),
    NOT_EXISTS_USER(NOT_FOUND, "존재하지 않는 회원입니다."),
    NOT_FOUND_PAYMENT(NOT_FOUND, "존재하는 결제수단이 없습니다."),
    CART_NOT_FOUND(NOT_FOUND, "존재하는 장바구니 상품이 없습니다"),
    SHIPPING_NOT_FOUND(NOT_FOUND, "주소가 존재하지 않습니다."),
    CATEGORY_NOT_FOUND(NOT_FOUND, "존재하지 않는 카테고리입니다."),
    NEVER_LIKED(NOT_FOUND, "후기를 좋아요한 적이 없습니다."),
    FAIL_USER_LOGIN(NOT_FOUND, "존재하지 않는 계정입니다."),

    // 409
    NOT_ORDER_HOST(CONFLICT, "해당 주문을 주문한 사용자가 아닙니다."),
    NOT_AUTHOR(CONFLICT, "작성자가 아닙니다."),
    NOT_OWNER(CONFLICT, "주문자가 아닙니다."),
    EXIST_SAME_ID(CONFLICT, "사용 불가능한 아이디 입니다."),
    EXIST_SAME_EMAIL(CONFLICT, "사용 불가능한 이메일 입니다."),

    // 422
    LOGIN_FAILED(UNPROCESSABLE_ENTITY, "아이디, 비밀번호를 확인해주세요."),

    // 500
    KURLY_SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 내부 문제입니다. 관리자에게 문의바랍니다."),
    IMAGE_UPLOAD_FAIL(INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다."),
    IMAGE_CONVERT_FAIL(INTERNAL_SERVER_ERROR, "파일 변환에 실패했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
