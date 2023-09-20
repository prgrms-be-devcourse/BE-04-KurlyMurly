package com.devcourse.kurlymurly.image.exception;

public class ImageConvertFailException extends RuntimeException {
    private static final String IMAGE_CONVERT_FAIL = "파일 변환에 실패했습니다.";

    public ImageConvertFailException(Throwable cause) {
        super(IMAGE_CONVERT_FAIL, cause);
    }
}
