package com.sm.backend.module.reservation.domain.entity;

import com.sm.backend.common.base.BaseEntity;
import com.sm.backend.module.member.domain.entity.Member;
import com.sm.backend.module.stadium.domain.entity.ReservableStadium;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "reservation")
public class Reservation extends BaseEntity{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservable_stadium_id")
    private ReservableStadium reservableStadium;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @Version
    private Long version;


    public void update(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }
    public void update(Member member, ReservableStadium reservableStadium, ReservationStatus reservationStatus) {
        this.member = member;
        this.reservableStadium = reservableStadium;
        this.reservationStatus = reservationStatus;
    }
}
