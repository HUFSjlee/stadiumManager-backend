package com.sm.backend.module.reservation.infrastructure;

import com.sm.backend.module.reservation.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    //select reservation_id from reservation where member_id = ? and reservable_stadium_id = ?
    //@Query(value = "select Reservation.id" + " from Reservation " + " where member.id = ? and ReservableStadium.id = ? ", nativeQuery = true)

    //@Query("select reservationId from Reservation r where r.memberId = ? and r.reservableStadiumId = ? ")


    //@Query("SELECT r.id FROM Reservation r WHERE r.member.id = :memberId  AND r.reservableStadium.id = :reservableStadiumId ")
    //Long findById(@Param("memberId")Long memberId, @Param("reservableStadiumId") Long reservableStadiumId);

    //Reservation findByMemberIdAndReservableStadiumId(Long memberId, Long reservableStadiumId);

    boolean existsByMemberIdAndReservableStadiumId(Long memberId, Long reservableStadiumId);

    @Query("SELECT count(*) FROM Reservation r WHERE r.reservableStadium.id = :reservableStadiumId")
    Long findByReservationStadiumId(@Param("reservableStadiumId") Long reservableStadiumId);
}
