package com.sm.backend.module.stadium.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sm.backend.common.base.BaseEntity;
import com.sm.backend.module.member.domain.entity.Member;
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

    public void update(String name, int rentalPrice, String gameRule, LocalDateTime startTime, LocalDateTime endTime, char availableGender) {
        //this.name = name;
        this.rentalPrice = rentalPrice;
        this.gameRule = gameRule;
        this.startTime = startTime;
        this.endTime = endTime;
        this.availableGender = availableGender;
    }
}
