package com.sm.backend.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    SUCCESS("200", "success"),
    NOT_FOUND_RESOURCE("C100", "Not Found Resources"),

    RESERVATION_FULL("C200", "Reservation Full"),

    IMPOSSIBLE_RESERVATION("C200","Impossible Reservation"),
    NOT_FOUND_RESERVATION("C200","Not found Reservation")
    ;

    private String code;
    private String defaultMessage;
}
