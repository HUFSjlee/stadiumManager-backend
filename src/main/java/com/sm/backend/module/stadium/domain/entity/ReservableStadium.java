package com.sm.backend.module.stadium.domain.entity;

import com.sm.backend.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "stadium_name")
    private String name;

    @Column(name = "rental_price")
    private int rentalPrice;

    @Column(name = "rule")
    private String rule;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "available_gender")
    private char availableGender;

    public void update(String name, int rentalPrice, String rule, LocalDateTime startTime, LocalDateTime endTime, char availableGender) {
        this.name = name;
        this.rentalPrice = rentalPrice;
        this.rule = rule;
        this.startTime = startTime;
        this.endTime = endTime;
        this.availableGender = availableGender;
    }
}
