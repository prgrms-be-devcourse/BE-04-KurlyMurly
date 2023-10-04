package com.devcourse.kurlymurly.image.exception;

import com.devcourse.kurlymurly.core.exception.KurlyBaseException;

import static com.devcourse.kurlymurly.core.exception.ErrorCode.IMAGE_UPLOAD_FAIL;

public class ImageUploadFailException extends KurlyBaseException {
    public ImageUploadFailException() {
        super(IMAGE_UPLOAD_FAIL);
    }
}
