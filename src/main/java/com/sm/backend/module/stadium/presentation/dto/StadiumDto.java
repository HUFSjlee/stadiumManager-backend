package com.sm.backend.module.stadium.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sm.backend.module.stadium.domain.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class StadiumDto {

    @Getter
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BaseResponse {
        private Long id;

        private String name;

        private int size;

        private String address;

        private Region region;

        private int minimumPersonnel;

        private int maximumPersonnel;

        private Boolean parkingAvailable;

        private Boolean showerAvailable;

        private String description;

        private LocalDateTime startTime;

        private LocalDateTime endTime;

        private Boolean active;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateRequest {
        @NotNull
        private String name;

        private int size;

        private String address;

        private Region region;

        @JsonProperty(value = "minimum_personnel")
        private int minimumPersonnel;

        @JsonProperty(value = "maximum_personnel")
        private int maximumPersonnel;

        private boolean parkingAvailable = true;

        private boolean showerAvailable = false;

        private String description;

        @JsonProperty(value = "start_time")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime startTime;

        @JsonProperty(value = "end_time")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime endTime;

        private boolean active = false;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRequest {
        @NotNull
        private String name;

        private int size;

        private String address;

        private Region region;

        @JsonProperty(value = "minimum_personnel")
        private int minimumPersonnel;

        @JsonProperty(value = "maximum_personnel")
        private int maximumPersonnel;

        private boolean parkingAvailable = true;

        private boolean showerAvailable = false;

        private String description;

        @JsonProperty(value = "start_time")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime startTime;

        @JsonProperty(value = "end_time")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime endTime;

        private boolean active = false;
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
    public static class DeleteResponse {
        private Long id;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateResponse {
        private Long id;
        private String description;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FindResponse {
        private Long id;
        private String description;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SearchResponse {
        private Long id;
    }
}
