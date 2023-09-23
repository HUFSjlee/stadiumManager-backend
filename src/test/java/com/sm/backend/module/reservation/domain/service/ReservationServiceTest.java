package com.sm.backend.module.reservation.domain.service;
import com.sm.backend.common.exception.ImpossibleReservationException;
import com.sm.backend.module.manager.domain.entity.Manager;
import com.sm.backend.module.member.domain.entity.Member;
import com.sm.backend.module.member.infrastructure.repository.MemberRepository;
import com.sm.backend.module.reservation.domain.entity.ReservationStatus;
import com.sm.backend.module.reservation.infrastructure.ReservationRepository;
import com.sm.backend.module.reservation.presentation.dto.ReservationDto;
import com.sm.backend.module.stadium.domain.entity.ReservableStadium;
import com.sm.backend.module.stadium.domain.entity.ReservableStadiumStatus;
import com.sm.backend.module.stadium.domain.entity.Stadium;
import com.sm.backend.module.stadium.infrastructure.repository.ReservableStadiumRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.api.RedissonClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.sm.backend.module.member.domain.entity.Level.ROOKIE1;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @InjectMocks
    private ReservationService reservationService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ReservableStadiumRepository reservableStadiumRepository;
    @Mock
    private ReservationRepository reservationRepository;

    @Spy
    private RedissonClient redissonClient;

    //spy대신 다른걸 써본다던지, lock이 null인 부분을 해결해보기.
    //다른 mocking도 만들어서 테스트코드 작성해보기.

    @Test
    void 포인트가_부족하면_예약이_불가능하다() {

        // given -> Mocking 과정
        var member = new Member(1L, emptyList(), emptyList(), "LSJ", "my-nickname",'남', LocalDate.of(2000, 12, 31),ROOKIE1, null, null, BigDecimal.ZERO);
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));

        var reservableStadium = new ReservableStadium(1L,new Stadium(),emptyList(),new Manager(),10000,"사람을 향한 거친 태클 금지",LocalDateTime.now(), LocalDateTime.now(),'남', ReservableStadiumStatus.AVAILABLE,ROOKIE1);
        when(reservableStadiumRepository.findById(anyLong())).thenReturn(Optional.of(reservableStadium));

        // when, then
        var request = new ReservationDto.CreateRequest(1L,11L,ReservationStatus.RESERVED);
        assertThrows(ImpossibleReservationException.class, () -> reservationService.reserve(request));
    }

    @Test
    void 레벨을_초과하면_예약이_불가능하다() {

    }


}