package com.sm.backend.module.stadium.domain.service;

import com.sm.backend.module.stadium.domain.entity.Stadium;
import com.sm.backend.module.stadium.infrastructure.repository.StadiumRepository;
import com.sm.backend.module.stadium.presentation.dto.StadiumDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class StadiumService {

    private final StadiumRepository stadiumRepository;

    public StadiumDto.CreateResponse create(StadiumDto.CreateRequest request) {
        Stadium stadium = Stadium.builder()
                .name(request.getName())
                .address(request.getAddress())
                .description(request.getDescription())
                .minimumPerson(request.getMinimumPerson())
                .maximumPerson(request.getMaximumPerson())
                .active(request.isActive())
                .parkingAvailable(request.isParkingAvailable())
                .showerAvailable(request.isShowerAvailable())
                .createdAt(LocalDateTime.now())
                .createdBy("SYSTEM")
                .updatedAt(LocalDateTime.now())
                .updatedBy("SYSTEM")
                .build();

        var savedEntity = stadiumRepository.save(stadium);

        return StadiumDto.CreateResponse.builder().id(savedEntity.getId()).build();
    }
}

