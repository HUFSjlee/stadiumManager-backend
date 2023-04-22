package com.sm.backend.module.stadium.presentation;

import com.sm.backend.common.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StadiumAdminController {

    static class SampleDto {
        private String name = "sample";
        private String address = "Seoul";
        private String phone = "010-1234-1234";
    }

    /**
     * 구장 등록
     */
    @PostMapping("/stadiums")
    public BaseResponse<SampleDto> create() {
        return BaseResponse.success(new SampleDto());
    }
}
