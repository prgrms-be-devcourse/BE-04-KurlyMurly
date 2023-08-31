package com.devcourse.kurlymurly.Exception;

public class NotFoundOrderSupportException extends RuntimeException {
    private final static String NOT_FOUND_ORDER_MESSAGE = "존재하는 1:1 문의 내역이 없습니다.";

    public NotFoundOrderSupportException() {
        super(NOT_FOUND_ORDER_MESSAGE);
    }
}
