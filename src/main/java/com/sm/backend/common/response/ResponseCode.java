package com.sm.backend.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    SUCCESS("200", "success"),
    ;

    private String code;
    private String defaultMessage;
}
