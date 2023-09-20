package com.devcourse.kurlymurly.image.exception;

public class ImageUploadFailException extends RuntimeException {
    private static final String IMAGE_UPLOAD_FAIL = "파일 업로드에 실패했습니다.";

    public ImageUploadFailException() {
        super(IMAGE_UPLOAD_FAIL);
    }
}
