package com.devcourse.kurlymurly.common;

import org.springframework.core.io.InputStreamResource;

import java.io.InputStream;

public class CustomInputStreamResource extends InputStreamResource {
    private final String originImageName;
    private final long length;

    public CustomInputStreamResource(InputStream inputStream, String originImageName, long length) {
        super(inputStream);
        this.originImageName = originImageName;
        this.length = length;
    }

    @Override
    public String getFilename() {
        return originImageName;
    }

    @Override
    public long contentLength() {
        return length;
    }
}
