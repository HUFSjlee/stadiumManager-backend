package com.sm.backend.module.stadium.domain.mapper;

import com.sm.backend.module.stadium.domain.entity.ReservableStadium;
import com.sm.backend.module.stadium.presentation.dto.ReservableStadiumDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReservableStadiumMapper {
    public ReservableStadium toEntity(ReservableStadiumDto.CreateRequest request) {
        return ReservableStadium.builder()
                //.name(request.getName())
                .rentalPrice(request.getRentalPrice())
                .gameRule(request.getGameRule())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .availableGender(request.getAvailableGender())
                .availableLevel(request.getAvailableLevel())
                .createdAt(LocalDateTime.now())
                .createdBy("SYSTEM")
                .updatedAt(LocalDateTime.now())
                .updatedBy("SYSTEM")
                .build();
    }

    public ReservableStadiumDto.UpdateResponse toUpdateResponse(ReservableStadium entity) {
        return ReservableStadiumDto.UpdateResponse.builder()
                .id(entity.getId())
                .build();
    }

    public ReservableStadiumDto.FindResponse toFindResponse(ReservableStadium entity) {
        return ReservableStadiumDto.FindResponse.builder()
                .id(entity.getId())
                .build();
    }
}
