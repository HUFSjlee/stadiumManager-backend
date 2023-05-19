package com.sm.backend.module.member.presentation.controller;


import com.sm.backend.common.response.BaseResponse;
import com.sm.backend.module.member.domain.service.MemberService;
import com.sm.backend.module.member.presentation.dto.MemberDto;
import com.sm.backend.module.stadium.presentation.dto.StadiumDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity create(@RequestBody @Valid MemberDto.CreateRequest request) {
        var response = memberService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(response));
    }

    @GetMapping("/members/{id}")
    public BaseResponse<MemberDto.FindResponse> findById(@PathVariable Long id) {
        return BaseResponse.success(memberService.findById(id));
    }
}
