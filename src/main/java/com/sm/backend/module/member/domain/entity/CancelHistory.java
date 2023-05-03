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
public class CancelHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cencel_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member memberId;
    //다대일 관계라고 생각해서 ManyToOne 사용.

    @Column(name = "cancel_date")
    private LocalDateTime cancelDate;
}
