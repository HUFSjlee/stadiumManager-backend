package com.sm.backend.module.stadium.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class StadiumDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateRequest {
        @NotNull
        private String name;
        private String address;
        @Min(1)
        private int size;
        @Min(1)
        private int minimumPerson;
        @Min(1)
        private int maximumPerson;
        private String description;
        private boolean parkingAvailable = false;
        private boolean showerAvailable = false;
        private boolean active = false;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateResponse {
        private Long id;
    }
}
