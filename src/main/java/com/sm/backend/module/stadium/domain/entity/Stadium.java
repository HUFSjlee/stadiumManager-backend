package com.sm.backend.module.stadium.domain.entity;

import com.sm.backend.common.base.BaseEntity;
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
@Table(name = "stadium")
public class Stadium extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stadium_id")
    private Long id;

    @Column(name = "stadium_name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "stadium_size")
    private int size;

    @Column(name = "stadium_minimum_person")
    private int minimumPerson;

    @Column(name = "stadium_maximum_person")
    private int maximumPerson;

    @Column(name = "parking_available")
    private boolean parkingAvailable;

    @Column(name = "shower_available")
    private boolean showerAvailable;

    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private boolean active;
}
