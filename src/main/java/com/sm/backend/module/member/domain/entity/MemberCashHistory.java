package com.sm.backend.module.member.domain.entity;

import com.sm.backend.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "member_cash_history")
public class MemberCashHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_cash_history")
    private Long id;

    @OneToMany
    @JoinColumn(name = "member_id")
    private List<Member> member;    //연관관계매핑, 외래키 사용에 대한 키워드로 해결했는데, 원리와 방법을 자세히 모르겠음. 왜 리스트로 받지?

    @Column(name = "refund_amount")
    private int refundAmount;

    @Column(name = "refundAt")
    private LocalDateTime refundAt;
}
