package com.sm.backend.module.reservation.domain.service;

import com.sm.backend.common.exception.ImpossibleReservationException;
import com.sm.backend.common.exception.NotFoundReservationException;
import com.sm.backend.common.exception.NotFoundResourceException;
import com.sm.backend.common.exception.ReservationFullException;
import com.sm.backend.module.member.domain.entity.Level;
import com.sm.backend.module.member.domain.entity.Member;
import com.sm.backend.module.member.infrastructure.repository.MemberRepository;
import com.sm.backend.module.reservation.domain.entity.Reservation;
import com.sm.backend.module.reservation.domain.entity.ReservationStatus;
import com.sm.backend.module.reservation.infrastructure.ReservationRepository;
import com.sm.backend.module.reservation.presentation.dto.ReservationDto;
import com.sm.backend.module.stadium.domain.entity.ReservableStadium;
import com.sm.backend.module.stadium.infrastructure.repository.ReservableStadiumRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReservationService {

    private final MemberRepository memberRepository;
    private final ReservableStadiumRepository reservableStadiumRepository;
    private final ReservationRepository reservationRepository;
    private final RedissonClient redissonClient;

    @Transactional
    public ReservationDto.CreateResponse reserve(ReservationDto.CreateRequest request) {

        RLock lock = redissonClient.getLock("reservation-lock");

        /**
         * redis-cli -> 명령어 치면 락 걸린거 확인할 수 있는데
         * 1 ~ 30
         * 1 부터 차근차근 디버그 찍어서 락이 생성이 되었는지 확인
         */

        try {
            var isAvailable = lock.tryLock(10L, 2L, TimeUnit.SECONDS);

            if (!isAvailable) {
                throw new Exception();
            }

            log.info("IS LOCKED: {}", lock.isLocked());

            Member member = memberRepository.findById(request.getMemberId()).orElseThrow(() -> new NotFoundResourceException("Member not found"));
            // 여기서 DB 통해서 조회하는지, 아니면 CACHE 통해서 조회되는지
            ReservableStadium reservableStadium = reservableStadiumRepository.findById(request.getReservableStadiumId()).orElseThrow(() -> new NotFoundResourceException("Reservable stadium not found"));

            Reservation reservation = Reservation.builder()
                    .member(member)
                    .reservableStadium(reservableStadium)
                    .reservationStatus(ReservationStatus.RESERVED)
                    .createdAt(LocalDateTime.now())
                    .createdBy("USER")
                    .updatedAt(LocalDateTime.now())
                    .updatedBy("USER")
                    .build();

            BigDecimal point = new BigDecimal(String.valueOf(reservation.getMember().getPoint()));
            BigDecimal rentalPrice = new BigDecimal(reservation.getReservableStadium().getRentalPrice());

            if (point.compareTo(rentalPrice) < 0) {
                throw new ImpossibleReservationException("Not enough point. Recharge your points.");
            }

            BigDecimal remainingPoint = point.subtract(rentalPrice);

            member.updatePoint(remainingPoint);

            boolean existMemberReservableStadiumId = reservationRepository.existsByMemberIdAndReservableStadiumId(reservation.getMember().getId(), reservation.getReservableStadium().getId());
            if (existMemberReservableStadiumId) {
                throw new ImpossibleReservationException("Already registered");
            }

            var reservations = reservationRepository.findAll();
            log.info("{} Reservation count, memberID: {}", reservations.size(), member.getId());
            var reservationStadium = reservableStadiumRepository.findById(request.getReservableStadiumId())
                    .orElseThrow(() -> new NotFoundResourceException());
            log.info("{} ReservationStadium exists.", reservationStadium.getId());

            var maximumPersonnel = reservationStadium.getStadium().getMaximumPersonnel();

            if (reservations.size() >= maximumPersonnel) {
                throw new ReservationFullException();
            }


            Level memberLevel = reservation.getMember().getLevel();
            Level availableLevel = reservation.getReservableStadium().getLevel();

            if(memberLevel.getLevelPoint() > availableLevel.getLevelPoint()) {
                throw new ImpossibleReservationException("Reservation not allowed for this level");
            }

            var reservedEntity = reservationRepository.save(reservation);

            return ReservationDto.CreateResponse.builder().id(reservedEntity.getId()).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                log.info("UNLOCK");
                lock.unlock();
            }
        }
    }

    @Transactional
    public ReservationDto.UpdateResponse update(Long id, ReservationDto.UpdateRequest request) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new NotFoundResourceException("해당 예약 내역이 없습니다. id= " + id));
        log.info("{} reservation exists",reservation.getId());

        reservation.update(reservation.getMember(),reservation.getReservableStadium(),reservation.getReservationStatus());

        return ReservationDto.UpdateResponse.builder()
                .reservableStadiumId(reservation.getReservableStadium().getId())
                .memberId(reservation.getMember().getId())
                .reservationStatus(reservation.getReservationStatus())
                .build();
    }

    @Transactional
    public ReservationDto.CancelReservationResponse cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new NotFoundResourceException("Not found Reservation"));
        log.info("{} reservation exists.", reservation.getId());

        var reservationStatus = reservation.getReservationStatus();
        if(reservationStatus != ReservationStatus.RESERVED) {
            throw new NotFoundReservationException("Not found Reservation Information");
        }

        reservation.update(ReservationStatus.CANCELED);
        var canceledReservationEntity = reservationRepository.save(reservation);

        return ReservationDto.CancelReservationResponse.builder()
                .id(canceledReservationEntity.getId())
                .success(true)
                .message("Reservation has been canceled.")
                .build();
    }

    public ReservationDto.FindResponse findById(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new NotFoundResourceException("Not found Reservation Information."));
        log.info("{} reservation exists.", reservation.getId());

        if (reservation == null) {
            throw new NotFoundResourceException("Not found Reservation Information.");
        }

        var findResponse = toFindResponse(reservation);
        return findResponse;
    }

    private ReservationDto.FindResponse toFindResponse (Reservation entity) {
        var reservableStadium = entity.getReservableStadium();
        var stadiumId = reservableStadium.getId();

        var member = entity.getMember();
        var memberId = member.getId();

        return ReservationDto.FindResponse.builder()
                .id(entity.getId())
                .reservableStadiumId(stadiumId)
                .memberId(memberId)
                .build();
    }
}
