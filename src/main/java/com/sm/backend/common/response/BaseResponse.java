package com.sm.backend.common.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BaseResponse<T> {

    private String code;
    private String msg;
    private T result;
    private LocalDateTime timestamp;

    public static <T> BaseResponse<T> success(T t) {
        return new BaseResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDefaultMessage(), t, LocalDateTime.now());
    }

    public static <T> BaseResponse<T> success() {
        return new BaseResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDefaultMessage(), null, LocalDateTime.now());
    }

    public static BaseResponse fail(ResponseCode t) {
        return new BaseResponse(t.getCode(), t.getDefaultMessage(), t, LocalDateTime.now());
    }

    public static <T> BaseResponse<T> fail() {
        return new BaseResponse(ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getDefaultMessage(), null, LocalDateTime.now());
    }

    public BaseResponse(String code, String msg, T result, LocalDateTime timestamp) {
        this.code = code;
        this.msg = msg;
        this.result = result;
        this.timestamp = timestamp;
    }
}
