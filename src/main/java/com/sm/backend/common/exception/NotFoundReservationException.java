package com.sm.backend.common.exception;

import com.sm.backend.common.response.ResponseCode;

public class NotFoundReservationException extends BusinessException{
    public NotFoundReservationException() {
        super(ResponseCode.NOT_FOUND_RESERVATION, ResponseCode.NOT_FOUND_RESERVATION.getDefaultMessage());
    }

    public NotFoundReservationException(String customMessage) {
        super(ResponseCode.NOT_FOUND_RESERVATION, customMessage);
    }

    public NotFoundReservationException(ResponseCode responseCode, String customMessage) {
        super(responseCode, customMessage);
    }
}
