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

import java.util.List;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class StadiumService {

    private final StadiumRepository stadiumRepository;
    private final StadiumMapper stadiumMapper;

    // 상위 엔티티 = 1:N 관계에서 1인애들
    // N+1 = 상위 엔티티 전체 조회 쿼리 1개 + 상위 엔티티 row 개수 만큼 자식 엔티티 조회 = 1 + row
    public Page<Stadium> findAll(Pageable pageable) {
        List<Stadium> stadiums = stadiumRepository.findAll(); // 전체 조회하는 쿼리 1개

        // stadium(상위 엔티티) 의 row 개수만큼 자식 연관관계에 있는 테이블을 조회함
        for (var stadium: stadiums) {
            stadium.getReservableStadiums().forEach(it -> System.out.println("reservableStadiumId: " + it.getId()));
        }

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

        stadium.update(
                request.getName(),
                request.getSize(),
                request.getAddress(),
                request.getRegion(),
                request.getMinimumPersonnel(),
                request.getMaximumPersonnel(),
                request.isParkingAvailable(),
                request.isShowerAvailable(),
                request.getDescription(),
                request.getStartTime(),
                request.getEndTime(),
                request.isActive());

        return stadiumMapper.toUpdateResponse(stadium);
    }

    @Transactional
    public StadiumDto.DeleteResponse delete(Long id) {
        stadiumRepository.deleteById(id);
        return new StadiumDto.DeleteResponse(id);
    }
}

