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
public class ConversionAddressByCoordinates {

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
        @SerializedName("address")
        private Address address;
        @SerializedName("road_address")
        private RoadAddress roadAddress;

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
            @SerializedName("mountain_yn")
            private String mountainYn;
            @SerializedName("main_address_no")
            private String mainAddressNo;
            @SerializedName("sub_address_no")
            private String subAddressNo;
        }

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
        }
    }
}
