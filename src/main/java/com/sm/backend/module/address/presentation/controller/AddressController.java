package com.sm.backend.module.address.presentation.controller;

import com.sm.backend.common.response.PageResponse;
import com.sm.backend.module.address.domain.service.AddressService;
import com.sm.backend.module.address.presentation.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor // 이거 왜쓰는지
@RestController
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/search/address")
    public PageResponse<SearchAddressDto.Response> search(@RequestParam("query") String query,
                                                          @RequestParam(value = "page", defaultValue = "20") int page,
                                                          @RequestParam(value = "size", defaultValue = "10") int size) {
        // Front -> /search/adress 호출
        // 파라미터는 동일
        // 어떻게 kakaLocalService, Naver..Service, 행안부Service

        // 위 상태로는 불가능하니까 provider (Kakao, Naver, 행안부)
        // 적절한 방법일지 ?


        // 카카오를 기본 -> naver/행안부

        //return (Page<AddressDto.CreateResponse>) addressService.getAPI();
        return addressService.searchAddress(query, page, size);
    }

    @GetMapping("/search/administrative/district")
    public PageResponse<AdministrativeDistrictByCoordinatesDto.Response> searchAdministrative(@RequestParam("x") String x,
                                                                                              @RequestParam("y") String y,
                                                                                              @RequestParam(value = "inputCoord", defaultValue = "WGS84") String inputCoord,
                                                                                              @RequestParam(value = "outputCoord", defaultValue = "WGS84") String outputCoord) {
        return addressService.searchAdministrativeDistrict(x, y, inputCoord, outputCoord);
    }

    @GetMapping("/search/convertAddress/coordinates")
    public PageResponse<ConversionAddressByCoordinatesDto.Response> convertAddressToCoordinates(@RequestParam("x") String x,
                                                                                                @RequestParam("y") String y,
                                                                                                @RequestParam(value = "inputCoord", defaultValue = "WGS84") String inputCoord) {
        return addressService.convertAddressToCoordinates(x, y, inputCoord);
    }

    @GetMapping("/search/convert/coordinates/system")
    public PageResponse<ConversionCoordinatesSystemDto.Response> convertCoordinatesSystem(@RequestParam("x") Double x,
                                                                                          @RequestParam("y") Double y,
                                                                                          @RequestParam(value = "inputCoord", defaultValue = "WTM") String inputCoord,
                                                                                          @RequestParam(value = "outputCoord", defaultValue = "WGS84") String outputCoord) {
        return addressService.convertCoordinatesSystem(x, y, inputCoord, outputCoord);
    }

    @GetMapping("/search/mois/address")
    public PageResponse<MoisRoadNameAddressDto.Response> searchAddress (@RequestParam(value = "confmKey", defaultValue = "devU01TX0FVVEgyMDlzMDcyMDE0NTAzOTExMzk0Njq=") String confmKey,
                                                                        @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                                                                        @RequestParam(value = "countPerPage", defaultValue = "10") int countPerPage,
                                                                        @RequestParam("keyword") String keyword,
                                                                        @RequestParam(value = "resultType", defaultValue = "json") String resultType,
                                                                        @RequestParam(value = "hstryYn", defaultValue = "N") String hstryYn,
                                                                        @RequestParam(value = "firstSort", defaultValue = "NONE") String firstSort,
                                                                        @RequestParam(value = "addInfoYn", defaultValue = "N") String addInfoYn) {
        return addressService.searchMoisRoadNameAddress(confmKey, currentPage, countPerPage, keyword, resultType, hstryYn, firstSort, addInfoYn);
    }
}


