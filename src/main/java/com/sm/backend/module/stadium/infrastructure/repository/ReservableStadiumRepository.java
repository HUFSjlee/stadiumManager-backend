package com.sm.backend.module.stadium.infrastructure.repository;

import com.sm.backend.module.stadium.domain.entity.ReservableStadium;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservableStadiumRepository extends JpaRepository<ReservableStadium, Long> {
    Page<ReservableStadium> searchByIdContaining(String keyword, Pageable pageable);
}
