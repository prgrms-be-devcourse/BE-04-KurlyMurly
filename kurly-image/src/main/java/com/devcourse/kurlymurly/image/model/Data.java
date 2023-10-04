package com.devcourse.kurlymurly.image.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Data(
        @JsonProperty("id")
        String id,

        @JsonProperty("title")
        String title,

        @JsonProperty("url_viewer")
        String urlViewer,

        @JsonProperty("url")
        String url,

        @JsonProperty("display_url")
        String displayUrl,

        @JsonProperty("width")
        int width,

        @JsonProperty("height")
        int height,

        @JsonProperty("size")
        int size,

        @JsonProperty("time")
        long time,

        @JsonProperty("expiration")
        int expiration,

        @JsonProperty("delete_url")
        String deleteUrl
) {
}
