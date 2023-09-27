package com.sm.backend.module.reservation.domain.service;
import com.sm.backend.common.exception.ImpossibleReservationException;
import com.sm.backend.common.exception.ReservationFullException;
import com.sm.backend.module.manager.domain.entity.Manager;
import com.sm.backend.module.member.domain.entity.Level;
import com.sm.backend.module.member.domain.entity.Member;
import com.sm.backend.module.member.infrastructure.repository.MemberRepository;
import com.sm.backend.module.reservation.domain.entity.Reservation;
import com.sm.backend.module.reservation.domain.entity.ReservationStatus;
import com.sm.backend.module.reservation.infrastructure.ReservationRepository;
import com.sm.backend.module.reservation.presentation.dto.ReservationDto;
import com.sm.backend.module.stadium.domain.entity.Region;
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
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.io.InterruptedIOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.sm.backend.module.member.domain.entity.Level.PRO3;
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
    @Mock
    private RLock lock;

    //spy대신 다른걸 써본다던지, lock이 null인 부분을 해결해보기.
    //다른 mocking도 만들어서 테스트코드 작성해보기.

    @Test
    void 포인트가_부족하면_예약이_불가능하다() throws InterruptedException {

        // [given] -> Mocking 과정
        //lock을 획득하면 true를 리턴.
        when(redissonClient.getLock("reservation-lock")).thenReturn(lock);
        when(lock.tryLock(50L,30L, TimeUnit.SECONDS)).thenReturn(true);

        var member = new Member(50L, emptyList(), emptyList(), "LSJ", "my-nickname",'남', LocalDate.of(2000, 12, 31),ROOKIE1, null, null, BigDecimal.valueOf(10000));
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));

        var reservableStadium = new ReservableStadium(50L,new Stadium(1L,emptyList(),"교대풋살장",30,"강남",10,18,false,false,"없음",LocalDateTime.now(),LocalDateTime.now(),false, Region.BUSAN),emptyList(),new Manager(),10000,"사람을 향한 거친 태클 금지",LocalDateTime.now(), LocalDateTime.now(),'남', ReservableStadiumStatus.AVAILABLE,ROOKIE1);
        when(reservableStadiumRepository.findById(anyLong())).thenReturn(Optional.of(reservableStadium));

        // when, then
        var request = new ReservationDto.CreateRequest(50L,50L,ReservationStatus.RESERVED);
        assertThrows(RuntimeException.class, () -> reservationService.reserve(request));
    }

    @Test
    void 레벨을_초과하면_예약이_불가능하다() throws InterruptedException {

        //given
        when(redissonClient.getLock("reservation-lock")).thenReturn(lock);
        when(lock.tryLock(10L, 2L, TimeUnit.SECONDS)).thenReturn(true);

        //회원의 레벨을 초과한 상태의 mock 객체 생성
        var member = new Member(1L, emptyList(), emptyList(), "LSJ", "my-nickname", '남', LocalDate.of(2000, 12, 31), PRO3 , null, null, BigDecimal.valueOf(10000));
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));

        //예약 가능한 경기장 mock 객체 생성
        var reservableStadium = new ReservableStadium(1L, new Stadium(), emptyList(), new Manager(), 10000, "사람을 향한 거친 태클 금지", LocalDateTime.now(), LocalDateTime.now(), '남', ReservableStadiumStatus.AVAILABLE, ROOKIE1);
        when(reservableStadiumRepository.findById(anyLong())).thenReturn(Optional.of(reservableStadium));

        // when, then
        var request = new ReservationDto.CreateRequest(1L,11L,ReservationStatus.RESERVED);
        assertThrows(RuntimeException.class, () -> reservationService.reserve(request));
    }

    @Test
    void 중복_예약이_불가능하다() throws InterruptedException{
        //given
        when(redissonClient.getLock("reservation-lock")).thenReturn(lock);
        when(lock.tryLock(10L, 2L, TimeUnit.SECONDS)).thenReturn(true);

        //given
        //회원과 예약가능한구장의 두 mock 객체 생성
        var member = new Member(1L, emptyList(), emptyList(), "LSJ", "my-nickname", '남', LocalDate.of(2000, 12, 31), ROOKIE1, null, null, BigDecimal.valueOf(10000));
        var reservableStadium = new ReservableStadium(1L, new Stadium(), emptyList(), new Manager(), 10000, "사람을 향한 거친 태클 금지", LocalDateTime.now(), LocalDateTime.now(), '남', ReservableStadiumStatus.AVAILABLE, ROOKIE1);

        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        when(reservableStadiumRepository.findById(anyLong())).thenReturn(Optional.of(reservableStadium));


        //중복 예약 데이터 설정
        when(reservationRepository.existsByMemberIdAndReservableStadiumId(member.getId(), reservableStadium.getId())).thenReturn(true);

        //중복 예약이 확인되면 예외 발생
        var request = new ReservationDto.CreateRequest(1L,1L, ReservationStatus.RESERVED);

        //예외가 발생하는지 확인할 때 existingReservation 객체를 사용
        assertThrows(RuntimeException.class, ()-> reservationService.reserve(request));
    }

    @Test
    void 예약_가능_인원을_초과하면_예약이_불가능하다() throws InterruptedException {
        when(redissonClient.getLock("reservation-lock")).thenReturn(lock);
        when(lock.tryLock(10L, 2L, TimeUnit.SECONDS)).thenReturn(true);

        var member = new Member(1L, emptyList(), emptyList(), "LSJ", "my-nickname", '남', LocalDate.of(2000, 12, 31), ROOKIE1, null, null, BigDecimal.valueOf(10000));
        var reservableStadium = new ReservableStadium(1L, new Stadium(), emptyList(), new Manager(), 10000, "사람을 향한 거친 태클 금지", LocalDateTime.now(), LocalDateTime.now(), '남', ReservableStadiumStatus.AVAILABLE, ROOKIE1);

        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        when(reservableStadiumRepository.findById(anyLong())).thenReturn(Optional.of(reservableStadium));

        var reservations = new ArrayList<Reservation>();
        for(int i=0; i<reservableStadium.getStadium().getMaximumPersonnel();i++) {
            reservations.add(new Reservation());
        }
        when(reservationRepository.findAll()).thenReturn(reservations);

        //예약 가능 인원을 초과하면 예외 발생.
        var request = new ReservationDto.CreateRequest(1L,1L,ReservationStatus.RESERVED);
        assertThrows(RuntimeException.class, () -> reservationService.reserve(request));
    }
}