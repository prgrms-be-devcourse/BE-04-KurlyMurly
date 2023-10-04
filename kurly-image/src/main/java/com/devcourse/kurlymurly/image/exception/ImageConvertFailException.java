package com.devcourse.kurlymurly.image.exception;

import com.devcourse.kurlymurly.core.exception.ErrorCode;
import com.devcourse.kurlymurly.core.exception.KurlyBaseException;

public class ImageConvertFailException extends KurlyBaseException {
    public ImageConvertFailException(Throwable cause) {
        super(ErrorCode.IMAGE_CONVERT_FAIL, cause);
    }
}
