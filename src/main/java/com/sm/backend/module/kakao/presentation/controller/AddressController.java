package com.sm.backend.module.kakao.presentation.controller;

import com.sm.backend.common.response.PageResponse;
import com.sm.backend.module.kakao.domain.service.AddressService;
import com.sm.backend.module.kakao.domain.service.dto.Location;
import com.sm.backend.module.kakao.presentation.dto.AddressDto;
import com.sm.backend.module.kakao.presentation.dto.CoordinateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor // 이거 왜쓰는지
@RestController
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/search/address")
    public PageResponse<AddressDto.Response> search(@RequestParam("query") String query,
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
    public PageResponse<CoordinateDto.Response> searchAdministrative(@RequestParam("x") String x,
                                                                     @RequestParam("y") String y,
                                                                     @RequestParam(value = "inputCoord", defaultValue = "WGS84") String inputCoord,
                                                                     @RequestParam(value = "outputCoord", defaultValue = "WGS84") String outputCoord) {
        return addressService.searchAdministrativeDistrict(x, y, inputCoord, outputCoord);
    }
}


