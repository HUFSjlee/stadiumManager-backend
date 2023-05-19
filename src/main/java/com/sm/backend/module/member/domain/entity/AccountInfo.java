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
@Table(name = "account_info")
public class AccountInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_info_id")
    private Long id;

    @Column(name = "mem")
    private Long memberId;

    @Column(name = "account_bank")
    private String accountBank;

    @Column(name = "account_number")
    private int accountNumber;
}
