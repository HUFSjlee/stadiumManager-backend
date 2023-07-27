package com.sm.backend.module.address.domain.service.dto;

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
public class MoisRoadNameAddress {

    @SerializedName("common")
    private Common common;
    @SerializedName("juso")
    private List<Juso> jusoList;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Common {
        @SerializedName("totalCount")
        private String totalCount;
        @SerializedName("currentPage")
        private int currentPage;
        @SerializedName("countPerPage")
        private int countPerPage;
        @SerializedName("errorCode")
        private String errorCode;
        @SerializedName("errorMessage")
        private String errorMessage;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Juso {
        @SerializedName("roadAddr")
        private String roadAddr;
        @SerializedName("roadAddrPart1")
        private String roadAddrPart1;
        @SerializedName("roadAddrPart2")
        private String roadAddrPart2;
        @SerializedName("jibunAddr")
        private String jibunAddr;
        @SerializedName("engAddr")
        private String engAddr;
        @SerializedName("zipNo")
        private String zipNo;
        @SerializedName("admCd")
        private String admCd;
        @SerializedName("rnMgtSn")
        private String rnMgtSn;
        @SerializedName("bdMgtSn")
        private String bdMgtSn;
        @SerializedName("detBdNmList")
        private String detBdNmList;
        @SerializedName("bdNm")
        private String bdNm;
        @SerializedName("bdKdcd")
        private String bdKdcd;
        @SerializedName("siNm")
        private String siNm;
        @SerializedName("sggNm")
        private String sggNm;
        @SerializedName("emdNm")
        private String emdNm;
        @SerializedName("liNm")
        private String liNm;
        @SerializedName("rn")
        private String rn;
        @SerializedName("udrtYn")
        private String udrtYn;
        @SerializedName("buldMnnm")
        private Number buldMnnm;
        @SerializedName("buldSlno")
        private Number buldSlno;
        @SerializedName("mtYn")
        private String mtYn;
        @SerializedName("lnbrMnnm")
        private Number lnbrMnnm;
        @SerializedName("lnbrSlno")
        private Number lnbrSlno;
        @SerializedName("emdNo")
        private String emdNo;
        @SerializedName("hstryYn")
        private String hstryYn;
        @SerializedName("relJibun")
        private String relJibun;
        @SerializedName("hemdNm")
        private String hemdNm;
    }
}
