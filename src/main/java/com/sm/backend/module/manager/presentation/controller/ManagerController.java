package com.sm.backend.module.manager.presentation.controller;

import com.sm.backend.common.response.BaseResponse;
import com.sm.backend.module.manager.domain.service.ManagerService;
import com.sm.backend.module.manager.presentation.dto.ManagerDto;
import com.sm.backend.module.member.presentation.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/managers/{id}")
    public BaseResponse<ManagerDto.FindResponse> findById(@PathVariable Long id) {
        return BaseResponse.success(managerService.findById(id));
    }
}
