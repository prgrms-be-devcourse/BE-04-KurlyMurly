package com.devcourse.kurlymurly.web.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"success", "data"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KurlyResponse<T> {
    private final boolean success;
    private final T data;

    KurlyResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public static KurlyResponse<Void> ok(boolean success) {
        return new KurlyResponse<>(success, null);
    }

    public static <T> KurlyResponse<T> ok(T data) {
        return new KurlyResponse<>(true, data);
    }

    public static KurlyResponse<Void> noData() {
        return new KurlyResponse<>(true, null);
    }
}
