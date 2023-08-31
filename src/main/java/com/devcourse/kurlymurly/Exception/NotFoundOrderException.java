package com.devcourse.kurlymurly.Exception;

public class NotFoundOrderException extends RuntimeException {
    private final static String NOT_FOUND_ORDER_MESSAGE = "존재하는 주문 내역이 없습니다.";

    public NotFoundOrderException() {
        super(NOT_FOUND_ORDER_MESSAGE);
    }
}
