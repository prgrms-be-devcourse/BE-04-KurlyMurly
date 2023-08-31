package com.devcourse.kurlymurly.web.common;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {
    private T data;

    public ApiResponse(T data) {
        this.data = data;
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(data);
    }

    public static <T> ApiResponse<T> ok() {
        return new ApiResponse<>(null);
    }

    public static <T> ApiResponse<T> fail(HttpStatus statusCode, T data) {
        return new ApiResponse<>(data);
    }

    public T getData() {
        return data;
    }
}
