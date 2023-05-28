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
@Table(name = "cancel_history")
public class MemberPointHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_point_history_id")
    private Long id;

    @Column(name = "amount")
    private int amount;

    @Enumerated(EnumType.STRING)
    private PointType pointType;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
