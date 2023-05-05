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
@Table(name = "stadium")
public class Stadium extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stadium_id")
    private Long id;

    @Column(name = "stadium_name")
    private String name;

    @Column(name = "size")
    private int size;

    @Column(name = "address")
    private String address;

    @Column(name = "minumum_perssonel")
    private int minimumPerson;

    @Column(name = "maximum_perssonel")
    private int maximumPerson;

    @Column(name = "parking_available")
    private boolean parkingAvailable;

    @Column(name = "shower_available")
    private boolean showerAvailable;

    @Column(name = "description")
    private String descripton;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "active")
    private boolean active;

    @Column(name = "del")
    private boolean del;

    public void update(String name, int size, String address, int minimumPerson, int maximumPerson, boolean parkingAvailable, boolean showerAvailable, String descripton, LocalDateTime startTime, LocalDateTime endTime, boolean active, boolean del) {
        this.name = name;
        this.size = size;
        this.address = address;
        this.minimumPerson = minimumPerson;
        this.maximumPerson = maximumPerson;
        this.parkingAvailable = parkingAvailable;
        this.showerAvailable = showerAvailable;
        this.descripton = descripton;
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = active;
        this.del = del;
    }
}
