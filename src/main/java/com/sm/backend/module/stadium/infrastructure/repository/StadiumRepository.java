package com.sm.backend.module.stadium.infrastructure.repository;

import com.sm.backend.module.stadium.domain.entity.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StadiumRepository extends JpaRepository<Stadium, Long> {
}
