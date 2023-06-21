package com.sm.backend.module.member.domain.entity;

import com.sm.backend.common.base.BaseEntity;
import com.sm.backend.module.reservation.domain.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MemberPointHistory> memberPointHistories = new ArrayList<>();

    @Column(name = "member_name")
    private String name;

    @Column(name = "nickname")
    private String nickName;

    @Column(name = "gender")
    private char gender;

    @Column(name = "birth")
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private Level level;

    @Enumerated(EnumType.STRING)
    @Column(name = "manner")
    private Manner manner;

    @Column(name = "coupon")
    private String coupon;

    @Column(name = "point")
    private int point;

}
