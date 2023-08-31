package com.devcourse.kurlymurly.global.exception;

public class KurlyBaseException extends RuntimeException {
    private final ErrorCode errorCode;

    public KurlyBaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public KurlyBaseException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    ErrorCode getErrorCode() {
        return errorCode;
    }
}
