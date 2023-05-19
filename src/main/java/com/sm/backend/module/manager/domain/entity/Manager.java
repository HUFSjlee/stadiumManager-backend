package com.sm.backend.module.manager.domain.entity;

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
@Table(name = "manager")
public class Manager {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    private Long id;

    @Column(name = "manager_name")
    private String name;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "gender")
    private char gender;

    @Column(name = "account")
    private String account;
}
