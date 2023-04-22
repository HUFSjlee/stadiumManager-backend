package com.sm.backend.module.stadium.domain.entity;

import com.sm.backend.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "sm_stadium")
public class Stadium extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stadium_id")
    private Long id;

    @Column(name = "stadium_name")
    private String name;

    @Column(name = "stadium_address")
    private String address;

    @Column(name = "stadium_size")
    private int size;

    @Column(name = "stadium_minimum_people")
    private int minimumPeople;

    @Column(name = "stadium_maximum_people")
    private int maximumPeople;
}
