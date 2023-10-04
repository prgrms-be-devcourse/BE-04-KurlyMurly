package com.devcourse.kurlymurly.image.exception;

import com.devcourse.kurlymurly.common.exception.KurlyBaseException;

import static com.devcourse.kurlymurly.common.exception.ErrorCode.IMAGE_CONVERT_FAIL;

public class ImageConvertFailException extends KurlyBaseException {
    public ImageConvertFailException(Throwable cause) {
        super(IMAGE_CONVERT_FAIL, cause);
    }
}
