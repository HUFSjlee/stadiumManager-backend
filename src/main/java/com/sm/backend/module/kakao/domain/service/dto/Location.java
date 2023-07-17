package com.sm.backend.module.kakao.domain.service.dto;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @SerializedName("documents")
    private List<Document> documents;

    @SerializedName("meta")
    private Meta meta;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Document {
        @SerializedName("address_name")
        private String addressName;

        @SerializedName("address_type")
        private String addressType;

        @SerializedName("x")
        private String x;

        @SerializedName("y")
        private String y;
        @SerializedName("address")
        private Address address;

        @SerializedName("road_address")
        private RoadAddress roadAddress;


        @Getter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class RoadAddress {
            @SerializedName("address_name")
            private String addressName;

            @SerializedName("region_1depth_name")
            private String region1depthName;

            @SerializedName("region_2depth_name")
            private String region2depthName;

            @SerializedName("region_3depth_name")
            private String region3depthName;

            @SerializedName("road_name")
            private String roadName;

            @SerializedName("underground_yn")
            private String undergroundYn;

            @SerializedName("main_building_no")
            private String mainBuildingNo;

            @SerializedName("sub_building_no")
            private String subBuildingNo;

            @SerializedName("building_name")
            private String buildingName;

            @SerializedName("zone_no")
            private String zoneNo;

            @SerializedName("x")
            private String x;

            @SerializedName("y")
            private String y;
        }

        @Getter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Address {
            @SerializedName("address_name")
            private String addressName;

            @SerializedName("region_1depth_name")
            private String region1depthName;

            @SerializedName("region_2depth_name")
            private String region2depthName;

            @SerializedName("region_3depth_name")
            private String region3depthName;

            @SerializedName("region_3depth_h_name")
            private String region3depthHName;

            @SerializedName("h_code")
            private String hCode;

            @SerializedName("b_code")
            private String bCode;

            @SerializedName("mountain_yn")
            private String mountainYn;

            @SerializedName("main_address_no")
            private String mainAddressNo;

            @SerializedName("sub_address_no")
            private String subAddressNo;

            @SerializedName("x")
            private String x;

            @SerializedName("y")
            private String y;

        }
    }

    @Getter
    public static class Meta {
        @SerializedName("is_end")
        private boolean isEnd;
        @SerializedName("pageable_count")
        private int pageableCount;
        @SerializedName("total_count")
        private int totalCount;
    }
}

