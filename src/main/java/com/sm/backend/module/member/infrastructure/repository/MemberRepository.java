package com.sm.backend.module.member.infrastructure.repository;

import com.sm.backend.module.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
