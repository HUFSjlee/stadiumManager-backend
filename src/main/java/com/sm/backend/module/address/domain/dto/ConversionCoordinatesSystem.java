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
public class ConversionCoordinatesSystem {
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
        @SerializedName("x")
        private Double x;
        @SerializedName("y")
        private Double y;
    }
}
