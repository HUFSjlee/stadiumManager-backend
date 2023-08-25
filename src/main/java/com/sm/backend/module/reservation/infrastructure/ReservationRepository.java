package com.sm.backend.module.reservation.infrastructure;

import com.sm.backend.module.reservation.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByMemberIdAndReservableStadiumId(Long memberId, Long reservableStadiumId);

    @Query("SELECT count(*) FROM Reservation r WHERE r.reservableStadium.id = :reservableStadiumId")
    Long findByReservationStadiumId(@Param("reservableStadiumId") Long reservableStadiumId);
}
