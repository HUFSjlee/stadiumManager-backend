package com.sm.backend.common.exception;

import com.sm.backend.common.response.ResponseCode;

public class ImpossibleReservationException extends BusinessException{

    public ImpossibleReservationException() {
        super(ResponseCode.IMPOSSIBLE_RESERVATION, ResponseCode.IMPOSSIBLE_RESERVATION.getDefaultMessage());
    }

    public ImpossibleReservationException(String customMessage) {
        super(ResponseCode.IMPOSSIBLE_RESERVATION, customMessage);
    }

    public ImpossibleReservationException(ResponseCode responseCode, String customMessage) {
        super(responseCode, customMessage);
    }
}
