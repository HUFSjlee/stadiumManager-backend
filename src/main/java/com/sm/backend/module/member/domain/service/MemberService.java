package com.sm.backend.module.member.domain.service;

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
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Transactional
    public MemberDto.CreateResponse create(MemberDto.CreateRequest request) {
        var member = memberMapper.toEntity(request);
        var savedEntity = memberRepository.save(member);
        return MemberDto.CreateResponse.builder().id(savedEntity.getId()).build();
    }

    public MemberDto.FindResponse findById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 멤버가 없습니다. id= " + id));
        return memberMapper.toFindResponse(member);
    }
}
