package com.devcourse.kurlymurly.web.common;

import jakarta.validation.constraints.Positive;

public record PageParam(
        @Positive
        int page,

        @Positive
        int size) {
}
