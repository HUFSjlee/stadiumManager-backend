package com.sm.backend.module.member.domain.mapper;

import com.sm.backend.module.member.domain.entity.Member;
import com.sm.backend.module.member.presentation.dto.MemberDto;
import org.springframework.stereotype.Component;


@Component
public class MemberMapper {
    public Member toEntity(MemberDto.CreateRequest request) {
        return Member.builder()
                .name(request.getName())
                .nickName(request.getNickname())
                .gender(request.getGender())
                .cash(request.getCash())
                .birth(request.getBirth())
                .level(request.getLevel())
                .manner(request.getManner())
                .coupon(request.getCoupon())
                .point(request.getPoint())
                .build();
    }

    public MemberDto.FindResponse toFindResponse(Member entity) {
        return MemberDto.FindResponse.builder()
                .id(entity.getId())
                .build();
    }
}
