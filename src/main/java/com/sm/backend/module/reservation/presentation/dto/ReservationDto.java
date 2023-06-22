package com.sm.backend.module.reservation.presentation.dto;

import com.sm.backend.module.reservation.domain.entity.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

public class ReservationDto {
    @Getter
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BaseResponse {
        private Long id;
        private Long reservableStadiumId;
        private Long memberId;
        private ReservationStatus reservationStatus;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRequest {
        @NotNull
        private Long reservableStadiumId;
        @NotNull
        private Long memberId;

        private ReservationStatus reservationStatus;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateResponse {
        private Long id;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FindResponse {
        private Long id;
        private Long memberId;
        private Long reservableStadiumId;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CancelReservationResponse {
        private Long id;
        private boolean success;
        private String message;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CancelReservationRequest {
        private Long id;
        private boolean success;
        private String message;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateRequest {
        @NotNull
        private Long reservableStadiumId;
        @NotNull
        private Long memberId;
        private ReservationStatus reservationStatus;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateResponse {
        @NotNull
        private Long reservableStadiumId;
        @NotNull
        private Long memberId;
        private ReservationStatus reservationStatus;
    }

}
