package com.sm.backend.module.stadium.presentation.controller;

import com.sm.backend.common.response.BaseResponse;
import com.sm.backend.module.stadium.domain.service.StadiumService;
import com.sm.backend.module.stadium.presentation.dto.StadiumDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StadiumAdminController {

    private final StadiumService stadiumService;

    /**
     * 구장 등록
     */
    @PostMapping("/stadiums")
    public ResponseEntity create(@Validated @RequestBody StadiumDto.CreateRequest request) {
        var response = stadiumService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(response));
    }
}
