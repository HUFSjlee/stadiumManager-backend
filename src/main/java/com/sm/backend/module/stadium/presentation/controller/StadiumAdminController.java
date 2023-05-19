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

    /**
     * 목록 페이징 + 검색
     * 1. 페이징이 되어야 하고 -> 응답으로도 페이지 관련 필드들을 리턴해줘야함 (어떤 페이징 관련 필드를 응답으로 줄건지 설계)
     * 2. 검색 (필터링) -> 이름 같은 조건으로 검색했을 때, 해당 데이터만 조회 되어야 함 <v 해결>
     *
     *
     * 해결해야 할 것
     * 1. BaseEntity, exception, BaseResponse, ResponseCode 코드 분석 후 적용.
     */

    @GetMapping("/stadiums")
    public Page<Stadium> read() {
        PageRequest pageRequest = PageRequest.of(0,5);
        return stadiumService.findAll(pageRequest);
    }

    // TODO page, size 말고 Pageable 로 사용해보기 + 추가로 정렬 조건까지 queryString 으로 주기
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
