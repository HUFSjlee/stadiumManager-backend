package com.sm.backend.module.reservation.presentation.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.sm.backend.common.response.BaseResponse;
import com.sm.backend.module.member.presentation.dto.MemberDto;
import com.sm.backend.module.reservation.domain.entity.Reservation;
import com.sm.backend.module.reservation.presentation.dto.ReservationDto;
import com.sm.backend.module.reservation.domain.service.ReservationService;
import com.sm.backend.module.stadium.domain.entity.ReservableStadium;
import com.sm.backend.module.stadium.presentation.dto.StadiumDto;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//@Api(tags = {"예약 API 정보를 제공하는 Controller"})
@RequiredArgsConstructor
@RequestMapping("/reservations")
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    //@ApiOperation(value = "예약 메서드")
    @PostMapping("/reserve")
    public ResponseEntity reserve(@Validated @RequestBody ReservationDto.CreateRequest request) {
        var response = reservationService.reserve(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(response));
    }

    @GetMapping("/{id}")
    public BaseResponse<ReservationDto.FindResponse> findById(@PathVariable Long id) {
        return BaseResponse.success(reservationService.findById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity cancelReservation(@PathVariable Long id) {
        var cancelReservationResponse = reservationService.cancelReservation(id);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(cancelReservationResponse));
    }

    @PutMapping("/{id}")
    public BaseResponse<ReservationDto.UpdateResponse> update(
            @PathVariable Long id,
            @Validated @RequestBody ReservationDto.UpdateRequest request
    ) {
        return BaseResponse.success(reservationService.update(id, request));
    }
}
