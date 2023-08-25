package com.sm.backend.module.stadium.presentation.controller;

import com.sm.backend.common.response.BaseResponse;
import com.sm.backend.module.stadium.domain.entity.Stadium;
import com.sm.backend.module.stadium.domain.service.StadiumService;
import com.sm.backend.module.stadium.presentation.dto.StadiumDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class StadiumAdminController {

    private final StadiumService stadiumService;

    @GetMapping("/stadiums")
    public Page<Stadium> read() {
        PageRequest pageRequest = PageRequest.of(0,5);
        return stadiumService.findAll(pageRequest);
    }

    @GetMapping("/stadiums/search")
    public Page<Stadium> search(@RequestParam("keyword") String keyword,
                                @RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "size", defaultValue = "5") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return stadiumService.search(keyword,pageRequest);
    }

    @GetMapping("/stadiums/{id}")
    public BaseResponse<StadiumDto.FindResponse> findById(@PathVariable Long id) {
        return BaseResponse.success(stadiumService.findById(id));
    }

    @PostMapping("/stadiums")
    public ResponseEntity create(@RequestBody @Valid StadiumDto.CreateRequest request) {
        var response = stadiumService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(response));
    }

    @PutMapping("/stadiums/{id}")
    public BaseResponse<StadiumDto.UpdateResponse> update(
            @PathVariable Long id,
            @Validated @RequestBody StadiumDto.UpdateRequest request
    ) {
        return BaseResponse.success(stadiumService.update(id, request));
    }

    @DeleteMapping("/stadiums/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        var response = stadiumService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(response));
    }
}
