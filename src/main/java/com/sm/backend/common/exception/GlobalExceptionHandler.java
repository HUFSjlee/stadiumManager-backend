package com.sm.backend.common.exception;

import com.sm.backend.common.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 개발자가 작성한 'Custom Exception'을 공통으로 처리하기 위함
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse> businessException(BusinessException e) {
        log.error("BusinessException occurred: {}", e.getMessage(), e);
        final BaseResponse<BusinessException> response = BaseResponse.fail(e.responseCode);
        //final BaseResponse<> response = BaseResponse.fail(NOT_FOUND_EXCEPTION);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    //Custom Exception을 '제외한' 모든 익셉션을 공통으로 처리해야 함.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> exception(Exception e) {
        log.error("Unhandled Exception occurred: {}", e.getMessage(), e);
        final BaseResponse<Exception> response = BaseResponse.fail();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
