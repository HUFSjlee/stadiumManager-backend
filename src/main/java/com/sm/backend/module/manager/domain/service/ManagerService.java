package com.sm.backend.module.manager.domain.service;

import com.sm.backend.module.manager.domain.entity.Manager;
import com.sm.backend.module.manager.domain.mapper.ManagerMapper;
import com.sm.backend.module.manager.infrastructure.repository.ManagerRepository;
import com.sm.backend.module.manager.presentation.dto.ManagerDto;
import com.sm.backend.module.member.domain.entity.Member;
import com.sm.backend.module.member.domain.mapper.MemberMapper;
import com.sm.backend.module.member.infrastructure.repository.MemberRepository;
import com.sm.backend.module.member.presentation.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final ManagerMapper managerMapper;

    @Transactional
    public ManagerDto.CreateResponse create(ManagerDto.CreateRequest request) {
        var manager = managerMapper.toEntity(request);
        var savedEntity = managerRepository.save(manager);
        return ManagerDto.CreateResponse.builder().id(savedEntity.getId()).build();
    }

    public ManagerDto.FindResponse findById(Long id) {
        Manager manager = managerRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 매니저가 없습니다. id= " + id));
        return managerMapper.toFindResponse(manager);
    }
}
