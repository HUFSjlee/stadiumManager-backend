package com.sm.backend.module.stadium.presentation.controller;

import com.sm.backend.common.response.BaseResponse;
import com.sm.backend.module.stadium.domain.entity.ReservableStadium;
import com.sm.backend.module.stadium.domain.service.ReservableStadiumService;
import com.sm.backend.module.stadium.presentation.dto.ReservableStadiumDto;
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
public class ReservableStadiumController {
    private final ReservableStadiumService reservableStadiumService;

    @GetMapping("/reservable-stadiums")
    public Page<ReservableStadium> read() {
        PageRequest pageRequest = PageRequest.of(0,5);
        return reservableStadiumService.findAll(pageRequest);
    }

    @GetMapping("/reservable-stadiums/search")
    public Page<ReservableStadium> search(@RequestParam("keyword") String keyword,
                                @RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "size", defaultValue = "5") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return reservableStadiumService.search(keyword,pageRequest);
    }

    @GetMapping("/reservable-stadiums/{id}")
    public BaseResponse<ReservableStadiumDto.FindResponse> findById(@PathVariable Long id) {
        return BaseResponse.success(reservableStadiumService.findById(id));
    }

    @PostMapping("/reservable-stadiums")
    public ResponseEntity create(@RequestBody @Valid ReservableStadiumDto.CreateRequest request) {
        var response = reservableStadiumService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(response));
    }

    @PutMapping("/reservable-stadiums/{id}")
    public BaseResponse<ReservableStadiumDto.UpdateResponse> update(@PathVariable Long id, @Validated @RequestBody ReservableStadiumDto.UpdateRequest request) {
        return BaseResponse.success(reservableStadiumService.update(id, request));
    }

    @DeleteMapping("/reservable-stadiums/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        var response = reservableStadiumService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(response));
    }
}
