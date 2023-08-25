package com.sm.backend.module.stadium.infrastructure.repository;

import com.sm.backend.module.stadium.domain.entity.Stadium;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StadiumRepository extends JpaRepository<Stadium, Long> {
    @Override
    List<Stadium> findAll();

    Page<Stadium> searchByNameContaining(String keyword, Pageable pageable);
}
