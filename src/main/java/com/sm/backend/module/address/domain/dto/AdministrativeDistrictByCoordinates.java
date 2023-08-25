package com.sm.backend.module.address.domain.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdministrativeDistrictByCoordinates {

    @SerializedName("meta")
    private Meta meta;
    @SerializedName("documents")
    private List<Document> documents;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Meta {
        @SerializedName("total_count")
        private int totalCount;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Document {
        @SerializedName("region_type")
        private String regionType;
        @SerializedName("address_name")
        private String addressName;
        @SerializedName("region_1depth_name")
        private String region1depthName;
        @SerializedName("region_2depth_name")
        private String region2depthName;
        @SerializedName("region_3depth_name")
        private String region3depthName;
        @SerializedName("region_4depth_name")
        private String region4depthName;
        @SerializedName("code")
        private String code;
        @SerializedName("x")
        private Double x;
        @SerializedName("y")
        private Double y;
    }
}
