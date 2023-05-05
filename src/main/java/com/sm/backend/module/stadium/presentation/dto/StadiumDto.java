package com.sm.backend.module.stadium.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sm.backend.module.stadium.domain.entity.Stadium;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class StadiumDto {
    public StadiumDto() {}
    public StadiumDto(Stadium stadium) {
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateRequest {
        @NotNull
        private String name;

        @Min(1)
        private int size;

        private String address;

        @Min(1)
        private int minimumPerson;

        @Min(1)
        private int maximumPerson;

        private boolean parkingAvailable = true;

        private boolean showerAvailable = false;

        private String descripton;

        //@DateTimeFormat
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime startTime;

        //@DateTimeFormat
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime endTime;

        private boolean active = false;

        private boolean del = false;
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
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRequest {
        @NotNull
        private String name;

        @Min(1)
        private Integer size;

        private String address;

        @Min(1)
        private Integer minimumPerson;

        @Min(1)
        private Integer maximumPerson;

        private Boolean parkingAvailable;

        private Boolean showerAvailable;

        private String descripton;

        //@DateTimeFormat
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime startTime;

        //@DateTimeFormat
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime endTime;

        private Boolean active;

        private Boolean del;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateResponse {
        private Long id;
    }
}
