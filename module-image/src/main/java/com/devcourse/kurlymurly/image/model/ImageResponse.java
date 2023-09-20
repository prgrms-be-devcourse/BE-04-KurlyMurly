package com.devcourse.kurlymurly.image.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ImageResponse(
        @JsonProperty
        Data data,

        @JsonProperty("success")
        boolean success,

        @JsonProperty("status")
        int status
) {
}
