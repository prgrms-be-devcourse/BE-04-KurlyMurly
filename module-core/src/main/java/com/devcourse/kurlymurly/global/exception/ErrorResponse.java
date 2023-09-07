package com.devcourse.kurlymurly.global.exception;

record ErrorResponse(
        String errorCode,
        String message
) {
    static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.name(), errorCode.getMessage());
    }
}
