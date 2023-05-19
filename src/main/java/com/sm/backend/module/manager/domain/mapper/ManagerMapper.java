package com.sm.backend.module.manager.domain.mapper;

import com.sm.backend.module.manager.domain.entity.Manager;
import com.sm.backend.module.manager.presentation.dto.ManagerDto;
import org.springframework.stereotype.Component;

@Component
public class ManagerMapper {
    public Manager toEntity(ManagerDto.CreateRequest request) {
        return Manager.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .gender(request.getGender())
                .account(request.getAccount())
                .build();
    }

    public ManagerDto.FindResponse toFindResponse(Manager entity) {
        return ManagerDto.FindResponse.builder()
                .id(entity.getId())
                .build();
    }
}
