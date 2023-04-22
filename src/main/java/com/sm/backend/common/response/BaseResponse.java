package com.sm.backend.common.response;

import java.time.LocalDateTime;

/*
 {"name":"sample", "address":"Seoul", "phone":"010-1234-1234"}

 {"code": 200,  "msg":"success", "result":{"name":"sample", "address":"Seoul", "phone":"010-1234-1234"}}
 */

public class BaseResponse<T> {

    private String code;
    private String msg;
    private T result;
    private LocalDateTime timestamp;

    public static <T> BaseResponse<T> success(T t) {
        return new BaseResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDefaultMessage(), t, LocalDateTime.now());
    }

    public BaseResponse(String code, String msg, T result, LocalDateTime timestamp) {
        this.code = code;
        this.msg = msg;
        this.result = result;
        this.timestamp = timestamp;
    }
}
