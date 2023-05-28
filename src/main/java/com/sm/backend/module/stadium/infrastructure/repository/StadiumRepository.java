package com.sm.backend.module.stadium.infrastructure.repository;

import com.sm.backend.module.stadium.domain.entity.Stadium;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StadiumRepository extends JpaRepository<Stadium, Long> {

//    @EntityGraph(attributePaths = "reservableStadiums")
//    @Query("select s from Stadium s")
//    @Query("select DISTINCT s from Stadium s join fetch s.reservableStadiums ") // Fetch Join
    @Override
    List<Stadium> findAll();

    Page<Stadium> searchByNameContaining(String keyword, Pageable pageable);
}
