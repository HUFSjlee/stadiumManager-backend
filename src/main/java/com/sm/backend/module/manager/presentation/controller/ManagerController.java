package com.sm.backend.module.manager.presentation.controller;

import com.sm.backend.common.response.BaseResponse;
import com.sm.backend.module.manager.domain.service.ManagerService;
import com.sm.backend.module.manager.presentation.dto.ManagerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping("/managers")
    public ResponseEntity create(@RequestBody @Valid ManagerDto.CreateRequest request) {
        var response = managerService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(response));
    }
}
