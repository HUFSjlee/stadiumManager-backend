package com.sm.backend.module.address.presentation.controller;

import com.sm.backend.common.response.PageResponse;
import com.sm.backend.module.address.domain.service.AddressService;
import com.sm.backend.module.address.presentation.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/search/address")
    public PageResponse<SearchAddressDto.Response> search(@RequestParam("query") String query,
                                                          @RequestParam(value = "page", defaultValue = "20") int page,
                                                          @RequestParam(value = "size", defaultValue = "10") int size) {
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
}


