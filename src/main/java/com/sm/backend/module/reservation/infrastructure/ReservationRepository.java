package com.sm.backend.module.reservation.infrastructure;

import com.sm.backend.module.reservation.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
