package com.sm.backend.common.exception;

import com.sm.backend.common.response.ResponseCode;

public class ReservationFullException extends BusinessException{

    public ReservationFullException() {
        super(ResponseCode.RESERVATION_FULL, ResponseCode.RESERVATION_FULL.getDefaultMessage());
    }

    public ReservationFullException(String customMessage) {
        super(ResponseCode.RESERVATION_FULL, customMessage);
    }

    public ReservationFullException(ResponseCode responseCode, String customMessage) {
        super(responseCode, customMessage);
    }
}
