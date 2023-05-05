package com.sm.backend.module.stadium.domain.service;

import com.sm.backend.module.stadium.domain.entity.Stadium;
import com.sm.backend.module.stadium.infrastructure.repository.StadiumRepository;
import com.sm.backend.module.stadium.presentation.dto.StadiumDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class StadiumService {

    private final StadiumRepository stadiumRepository;

    public StadiumDto.CreateResponse create(StadiumDto.CreateRequest request) {
        Stadium stadium = Stadium.builder()
                .name(request.getName())
                .size(request.getSize())
                .address(request.getAddress())
                .minimumPerson(request.getMinimumPerson())
                .maximumPerson(request.getMaximumPerson())
                .parkingAvailable(request.isParkingAvailable())
                .showerAvailable(request.isShowerAvailable())
                .descripton(request.getDescripton())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .active(request.isActive())
                .del(request.isDel())
                .createdAt(LocalDateTime.now())
                .createdBy("SYSTEM")
                .updatedAt(LocalDateTime.now())
                .updatedBy("SYSTEM")
                .build();

        var savedEntity = stadiumRepository.save(stadium);
        return StadiumDto.CreateResponse.builder().id(savedEntity.getId()).build();
    }

    @Transactional
    public Long update(Long id, StadiumDto.UpdateRequest request) {
        Stadium stadium = stadiumRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 구장 정보가 없습니다. id= " + id));
        stadium.update(request.getName(), request.getSize(), request.getAddress(), request.getMinimumPerson(), request.getMaximumPerson(), request.getParkingAvailable(), request.getShowerAvailable(), request.getDescripton(), request.getStartTime(), request.getEndTime(), request.getActive(), request.getDel());
        return id;
    }

    public StadiumDto delete(Long id) {
        stadiumRepository.deleteById(id);
        return new StadiumDto();
    }

    public StadiumDto findById(Long id) {
        Stadium stadium = stadiumRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 구장 정보가 없습니다. id= " + id));
        return new StadiumDto(stadium);
    }
}

