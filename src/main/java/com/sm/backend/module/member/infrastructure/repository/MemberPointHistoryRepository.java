package com.sm.backend.module.member.infrastructure.repository;

import com.sm.backend.module.member.domain.entity.MemberPointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberPointHistoryRepository extends JpaRepository<MemberPointHistory, Long> {
}
