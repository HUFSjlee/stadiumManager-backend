package com.sm.backend.module.stadium.domain.service;

import com.sm.backend.common.exception.NotFoundResourceException;
import com.sm.backend.module.stadium.domain.entity.Stadium;
import com.sm.backend.module.stadium.domain.mapper.StadiumMapper;
import com.sm.backend.module.stadium.infrastructure.repository.StadiumRepository;
import com.sm.backend.module.stadium.presentation.dto.StadiumDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class StadiumService {

    private final StadiumRepository stadiumRepository;
    private final StadiumMapper stadiumMapper;

    public Page<Stadium> findAll(Pageable pageable) {
        return stadiumRepository.findAll(pageable);
    }

    public Page<Stadium> search(String keyword, Pageable pageable) {
        return stadiumRepository.searchByNameContaining(keyword, pageable);
    }
    public StadiumDto.FindResponse findById(Long id) {
        Stadium stadium = stadiumRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 구장 정보가 없습니다. id= " + id));
        return stadiumMapper.toFindResponse(stadium);
    }

    @Transactional
    public StadiumDto.CreateResponse create(StadiumDto.CreateRequest request) {
        var stadium = stadiumMapper.toEntity(request);
        var savedEntity = stadiumRepository.save(stadium);
        return StadiumDto.CreateResponse.builder().id(savedEntity.getId()).build();
    }

    @Transactional
    public StadiumDto.UpdateResponse update(Long id, StadiumDto.UpdateRequest request) {
        Stadium stadium = stadiumRepository.findById(id).orElseThrow(() -> new NotFoundResourceException("해당 구장 정보가 없습니다. id= " + id));

        stadium.update(request.getName(), request.getSize(),
                request.getAddress(), request.getMinimumPersonnel(),
                request.getMaximumPersonnel(), request.getParkingAvailable(),
                request.getShowerAvailable(), request.getDescription(),
                request.getStartTime(), request.getEndTime(), request.getActive());
        return stadiumMapper.toUpdateResponse(stadium);
    }

    @Transactional
    public StadiumDto.DeleteResponse delete(Long id) {
        stadiumRepository.deleteById(id);
        return new StadiumDto.DeleteResponse(id);
    }
}

