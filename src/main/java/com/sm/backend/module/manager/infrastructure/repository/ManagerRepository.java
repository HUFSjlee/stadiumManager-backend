package com.sm.backend.module.manager.infrastructure.repository;

import com.sm.backend.module.manager.domain.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
