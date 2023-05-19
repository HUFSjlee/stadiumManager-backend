package com.sm.backend.module.stadium.domain.mapper;

import com.sm.backend.module.stadium.domain.entity.Stadium;
import com.sm.backend.module.stadium.presentation.dto.StadiumDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Role: entity -> dto or dto -> entity
 */
@Component
public class StadiumMapper {

    public Stadium toEntity(StadiumDto.CreateRequest request) {
        return Stadium.builder()
                .name(request.getName())
                .size(request.getSize())
                .region(request.getRegion())
                .address(request.getAddress())
                .minimumPersonnel(request.getMinimumPersonnel())
                .maximumPersonnel(request.getMaximumPersonnel())
                .parkingAvailable(request.isParkingAvailable())
                .showerAvailable(request.isShowerAvailable())
                .description(request.getDescription())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .active(request.isActive())
                .createdAt(LocalDateTime.now())
                .createdBy("SYSTEM")
                .updatedAt(LocalDateTime.now())
                .updatedBy("SYSTEM")
                .build();
    }

    public StadiumDto.UpdateResponse toUpdateResponse(Stadium entity) {
        return StadiumDto.UpdateResponse.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .build();
    }

    public StadiumDto.FindResponse toFindResponse(Stadium entity) {
        return StadiumDto.FindResponse.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .build();
    }
}
