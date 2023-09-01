package com.devcourse.kurlymurly.global.exception;

public class KurlyBaseException extends RuntimeException {
    private static final String ID_PREFIX = " ID: ";

    private final ErrorCode errorCode;

    public KurlyBaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    private KurlyBaseException(ErrorCode errorCode, Long id) {
        super(errorCode.getMessage() + ID_PREFIX + id);
        this.errorCode = errorCode;
    }

    public static KurlyBaseException withId(ErrorCode errorCode, Long id) {
        return new KurlyBaseException(errorCode, id);
    }

    ErrorCode getErrorCode() {
        return errorCode;
    }
}
