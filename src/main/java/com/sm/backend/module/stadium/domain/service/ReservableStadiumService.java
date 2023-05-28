package com.sm.backend.module.stadium.domain.service;

import com.sm.backend.common.exception.NotFoundResourceException;
import com.sm.backend.module.stadium.domain.entity.ReservableStadium;
import com.sm.backend.module.stadium.domain.mapper.ReservableStadiumMapper;
import com.sm.backend.module.stadium.infrastructure.repository.ReservableStadiumRepository;
import com.sm.backend.module.stadium.presentation.dto.ReservableStadiumDto;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReservableStadiumService {
    private final ReservableStadiumRepository reservableStadiumRepository;
    private final ReservableStadiumMapper reservableStadiumMapper;

    public Page<ReservableStadium> findAll(Pageable pageable) {
        return reservableStadiumRepository.findAll(pageable);
    }

    public Page<ReservableStadium> search(String keyword, Pageable pageable) {
        return reservableStadiumRepository.searchByIdContaining(keyword, pageable);
    }

    public ReservableStadiumDto.FindResponse findById(Long id) {
        // TODO NotFoundResourceException
        ReservableStadium reservableStadium = reservableStadiumRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 예약 가능한 구장 정보가 없습니다. id= " + id));
        return reservableStadiumMapper.toFindResponse(reservableStadium);
    }

    @Transactional
    public ReservableStadiumDto.CreateResponse create(ReservableStadiumDto.CreateRequest request) {
        var reservableStadium = reservableStadiumMapper.toEntity(request);
        var savedEntity = reservableStadiumRepository.save(reservableStadium);
        return ReservableStadiumDto.CreateResponse.builder().id(savedEntity.getId()).build();
    }

    @Transactional
    public ReservableStadiumDto.UpdateResponse update(Long id, ReservableStadiumDto.UpdateRequest request) {
        ReservableStadium reservableStadium = reservableStadiumRepository.findById(id).orElseThrow(() -> new NotFoundResourceException("해당 예약 가능한 구장 정보가 없습니다. id= " + id));

        reservableStadium.update(
                request.getName(),
                request.getRentalPrice(),
                request.getGameRule(),
                request.getStartTime(),
                request.getEndTime(),
                request.getAvailableGender());
        return reservableStadiumMapper.toUpdateResponse(reservableStadium);
    }

    @Transactional
    public ReservableStadiumDto.DeleteResponse delete(Long id) {
        reservableStadiumRepository.deleteById(id);
        return new ReservableStadiumDto.DeleteResponse(id);
    }
}
