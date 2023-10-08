package com.devcourse.kurlymurly.global;

import com.devcourse.kurlymurly.common.exception.ErrorCode;
import com.devcourse.kurlymurly.common.exception.ErrorResponse;
import com.devcourse.kurlymurly.common.exception.KurlyBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.devcourse.kurlymurly.common.exception.ErrorCode.CLIENT_INPUT_INVALID;
import static com.devcourse.kurlymurly.common.exception.ErrorCode.KURLY_SERVER_ERROR;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 애플리케이션 공통 예외(KurlyBaseException)를 잡아주는 예외 핸들러
     * 예외에 맞게 응답 코드를 넘겨준다.
     */
    @ExceptionHandler(KurlyBaseException.class)
    public ResponseEntity<ErrorResponse> handleKurlyBaseException(KurlyBaseException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse response = ErrorResponse.from(errorCode);
        log.warn("KurlyBaseException Occurs : {}", errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }

    /**
     * Bean Validation 실패를 잡아주는 예외 핸들러
     * @Validated는 아직 미적용
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleValidationFailException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.info("ValidationFailed : {}", errorMessage);
        return new ErrorResponse(CLIENT_INPUT_INVALID.name(), errorMessage);
    }

    /**
     * 인증 실패 예외를 잡아주는 핸들러
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    public ErrorResponse handleUnexpectedException(AuthenticationException e) {
        log.warn("LoginFailed : {}", e.getClass().getSimpleName());
        log.warn("ErrorMessage : {}", e.getMessage());
        return ErrorResponse.from(ErrorCode.BAD_CREDENTIAL);
    }

    /**
     * 위의 예외를 제외한 예상하지 못한 예외를 잡아주는 핸들러
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnexpectedException(RuntimeException e) {
        log.warn("UnexpectedException Occurs : {}", e.getMessage());
        return ErrorResponse.from(KURLY_SERVER_ERROR);
    }
}
