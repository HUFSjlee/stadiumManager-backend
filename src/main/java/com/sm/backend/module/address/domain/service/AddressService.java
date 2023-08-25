package com.sm.backend.module.address.domain.service;

import com.sm.backend.common.response.PageResponse;
import com.sm.backend.module.address.domain.dto.AdministrativeDistrictByCoordinates;
import com.sm.backend.module.address.domain.dto.ConversionAddressByCoordinates;
import com.sm.backend.module.address.domain.dto.ConversionCoordinatesSystem;
import com.sm.backend.module.address.domain.dto.SearchAddress;
import com.sm.backend.module.address.infrastructure.client.KakaoLocalApiClient;
import com.sm.backend.module.address.presentation.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AddressService {

    private final KakaoLocalApiClient kakaoLocalApiClient;

    public PageResponse<SearchAddressDto.Response> searchAddress(String query, int page, int size) {
        SearchAddress searchAddress = null;
        try {
            var response = kakaoLocalApiClient.searchAddressList(query, page, size);
            searchAddress = response.execute().body();
        } catch (IOException e) {
            log.error("Exception ERROR: {}", e.getMessage());
        }

        List<SearchAddressDto.Response> items = new ArrayList<>();
        for (var doc: searchAddress.getDocuments()) {
            items.add(SearchAddressDto.Response.builder()
                    .addressName(doc.getAddressName())
                    .build());
        }

        var meta = searchAddress.getMeta();

        var pageResponse = new PageResponse<SearchAddressDto.Response>();
        pageResponse.setPage(meta.getPageableCount(), meta.getTotalCount(), !meta.isEnd());
        pageResponse.setItems(items);

        return pageResponse;
    }

    public PageResponse<AdministrativeDistrictByCoordinatesDto.Response> searchAdministrativeDistrict(String x, String y, String inputCoord, String outputCoord) {
        AdministrativeDistrictByCoordinates administrativeDistrictByCoordinates = null;
        try {
            var coordinateResponse = kakaoLocalApiClient.searchCoordinateList(x, y, inputCoord, outputCoord);
            administrativeDistrictByCoordinates = coordinateResponse.execute().body();
        } catch (IOException e) {
            log.error("Exception ERROR : {}", e.getMessage());
        }

        List<AdministrativeDistrictByCoordinatesDto.Response> items = new ArrayList<>();
        for(var doc : administrativeDistrictByCoordinates.getDocuments()) {
            items.add(AdministrativeDistrictByCoordinatesDto.Response.builder()
                    .regionType(doc.getRegionType())
                    .build());
        }

        var meta = administrativeDistrictByCoordinates.getMeta();

        var pageResponse = new PageResponse<AdministrativeDistrictByCoordinatesDto.Response>();
        pageResponse.setPage(meta.getTotalCount());
        pageResponse.setItems(items);

        return pageResponse;
    }

    public PageResponse<ConversionAddressByCoordinatesDto.Response> convertAddressToCoordinates(String x, String y, String inputCoord) {
       ConversionAddressByCoordinates conversionAddressByCoordinates = null;
        try {
            var response = kakaoLocalApiClient.convertAddressToCoordinates(x, y, inputCoord);
            conversionAddressByCoordinates = response.execute().body();
        } catch (IOException e) {
            log.error("Exception ERROR : {}", e.getMessage());
        }

        List<ConversionAddressByCoordinatesDto.Response> items = new ArrayList<>();
        for(var docs : conversionAddressByCoordinates.getDocuments()) {
            items.add(ConversionAddressByCoordinatesDto.Response.builder()
                    .addressName(docs.getAddress().getAddressName())
                    .build());
        }

        var meta = conversionAddressByCoordinates.getMeta();

        var pageResponse = new PageResponse<ConversionAddressByCoordinatesDto.Response>();
        pageResponse.setPage(meta.getTotalCount());
        pageResponse.setItems(items);

        return pageResponse;
    }

    public PageResponse<ConversionCoordinatesSystemDto.Response> convertCoordinatesSystem(Double x, Double y, String inputCoord, String outputCoord) {
        ConversionCoordinatesSystem conversionCoordinatesSystem = null;
        try {
            var conversionCoordinatesSystemCall = kakaoLocalApiClient.convertCoordinatesSystem(x, y, inputCoord, outputCoord);
            conversionCoordinatesSystem = conversionCoordinatesSystemCall.execute().body();
        } catch (IOException e) {
            log.error("Exception ERROR : {}", e.getMessage());
        }

        List<ConversionCoordinatesSystemDto.Response> items = new ArrayList<>();
        for (var docs : conversionCoordinatesSystem.getDocuments()) {
            items.add(ConversionCoordinatesSystemDto.Response.builder()
                    .x(docs.getX())
                    .y(docs.getY())
                    .build());
        }

        var meta = conversionCoordinatesSystem.getMeta();

        var pageResponse = new PageResponse<ConversionCoordinatesSystemDto.Response>();
        pageResponse.setPage(meta.getTotalCount());
        pageResponse.setItems(items);

        return pageResponse;
    }
}
