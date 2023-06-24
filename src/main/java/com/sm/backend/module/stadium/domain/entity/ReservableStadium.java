package com.sm.backend.module.stadium.domain.entity;

import com.sm.backend.common.base.BaseEntity;
import com.sm.backend.module.manager.domain.entity.Manager;
import com.sm.backend.module.member.domain.entity.Level;
import com.sm.backend.module.reservation.domain.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "reservable_stadium")
public class ReservableStadium extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservable_stadium_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id")
    private Stadium stadium;

    @OneToMany(mappedBy = "reservableStadium", fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @Column(name = "rental_price")
    private int rentalPrice;

    @Column(name = "game_rule")
    private String gameRule;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "available_gender")
    private char availableGender;

    @Enumerated(EnumType.STRING)
    @Column(name = "reservable_stadium_status")
    private ReservableStadiumStatus reservableStadiumStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "available_level")
    private Level level;

    public void update(int rentalPrice, String gameRule, LocalDateTime startTime, LocalDateTime endTime, char availableGender, ReservableStadiumStatus reservableStadiumStatus, Level level) {
        this.rentalPrice = rentalPrice;
        this.gameRule = gameRule;
        this.startTime = startTime;
        this.endTime = endTime;
        this.availableGender = availableGender;
        this.reservableStadiumStatus = reservableStadiumStatus;
        this.level = level;
    }
}
