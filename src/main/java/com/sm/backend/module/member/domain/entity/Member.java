package com.sm.backend.module.member.domain.entity;

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
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_name")
    private String name;

    @Column(name = "nickname")
    private String nickName;

    @Column(name = "gender")
    private char gender;

    @Column(name = "cash")
    private int cash;

    @Column(name = "birth")
    private LocalDateTime brith;

    @Column(name = "level")
    private String level;

    @Column(name = "manner")
    private String manner;

    @Column(name = "coupon")
    private String coupon;

    @Column(name = "point")
    private int point;

}
