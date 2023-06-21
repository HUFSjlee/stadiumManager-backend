package com.sm.backend.common.exception;

import com.sm.backend.common.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.sm.backend.common.response.ResponseCode.NOT_FOUND_RESOURCE;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<BaseResponse> notFoundException(NotFoundResourceException e) {
        final BaseResponse response = BaseResponse.success(NOT_FOUND_RESOURCE);
        return new ResponseEntity<BaseResponse>(response, HttpStatus.BAD_REQUEST);
    }

}
