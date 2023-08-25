package com.sm.backend.module.manager.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import javax.validation.constraints.NotNull;

public class ManagerDto {

    @Getter
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BaseResponse {
        private Long id;
        private String name;
        private String phoneNumber;
        private char gender;
        private String account;
    }

    @Getter
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateRequest {
        @NotNull
        private String name;
        private String phoneNumber;
        private char gender;
        private String account;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateResponse {
        private Long id;
    }

    @Getter
    @SuperBuilder
    @AllArgsConstructor
    public static class FindResponse {
        private Long id;
    }
}
