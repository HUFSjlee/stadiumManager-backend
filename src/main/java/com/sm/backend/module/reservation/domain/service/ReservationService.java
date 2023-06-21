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
import com.sm.backend.module.stadium.domain.entity.AvailableLevel;
import com.sm.backend.module.stadium.domain.entity.ReservableStadium;
import com.sm.backend.module.stadium.infrastructure.repository.ReservableStadiumRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReservationService {

    private final MemberRepository memberRepository;
    private final ReservableStadiumRepository reservableStadiumRepository;
    private final ReservationRepository reservationRepository;


    @Transactional
    public ReservationDto.CreateResponse reserve(ReservationDto.CreateRequest request) {

        Member member = memberRepository.findById(request.getMemberId()).orElseThrow(() -> new NotFoundResourceException("Member not found"));
        log.info("{} 멤버가 존재합니다 //보통 한글로 적지 않는다.", member.getId());
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


        /**잔액이 부족할 때 예약이 불가능한 예외 처리
         * 1. 포인트(point)가 얼마나 있는지 알아야하고
         * 2. 예약 비용(rental price)가 얼마인지 알아야함.
         * 3. 포인트가 예약 비용보다 적으면 예외 발생.
         * */
        int point = reservation.getMember().getPoint();
        int rentalPrice = reservation.getReservableStadium().getRentalPrice();
        if (point < rentalPrice) {
            throw new ImpossibleReservationException("Not enough point.");
        }

        /**이미 내가 해당 구장을 예약을 했을 때
         *1. memberId와 reservableStadiumId를 findByIds 로 조회.
         * */
        // 쿼리문 => select reservation_id from reservation where member_id = ? and reservable_stadium_id = ?
        boolean reservation2 = reservationRepository.existsByMemberIdAndReservableStadiumId(reservation.getMember().getId(), reservation.getReservableStadium().getId());

        if (reservation2) {
            throw new ImpossibleReservationException("Already registered");
        }


        /**정원이 가득 찼을 때
         * 1. 현재 내가 예약하려는 구장에 몇 명이 예약했는지 알아야 함. Reservation 테이블에서 조회.
         * 2. 구장의 최대 수용 인원을 알아야 함. Stadium ENTITY의 maximumPerson 정보를 가져옴.
         * */
        var count = reservationRepository.findByReservationStadiumId(request.getReservableStadiumId());
        var reservationStadium = reservableStadiumRepository.findById(request.getReservableStadiumId())
                .orElseThrow(() -> new NotFoundResourceException());
        var maximumPersonnel = reservationStadium.getStadium().getMaximumPersonnel();

        if (count >= maximumPersonnel) {
            throw new ReservationFullException();
        }


        /**일정 티어를 넘어가면 예약 불가능.
         * 1. 예약하려는 사람의 티어 정보를 가져와야함.
         * 2. 예약 가능한 구장의 예약 가능한 티어 정보를 가져와야함.
         * 3. 1,2를 비교해서 예약 가능한 티어 범위가 아니라면 예약 불가능.
         * */
        Level level = reservation.getMember().getLevel();
        AvailableLevel availableLevel = reservation.getReservableStadium().getAvailableLevel();

        //ordinal 말고 다른 방식으로 고쳐보기.
        if(level.ordinal() > availableLevel.ordinal()) {
            throw new ImpossibleReservationException("Reservation not allowed for this level");
        }

        var reservedEntity = reservationRepository.save(reservation);

        return ReservationDto.CreateResponse.builder().id(reservedEntity.getId()).build();
    }
    //1. 검증 로직은 됐는데, 예약할 때 포인트 차감 같은 것 추가하기.
    //2. 예약 수정.

    /** 예약 취소 메서드
     * 1. 예약되어 있는 reservationId 가져오기.
     * reservationRepository 에서 findById 메서드로 reservationId를 가져온다
     *
     * 2. 가져온 reservationId 의 정보가 RESERVATION 상태인지 확인하고, RESERVATION 상태가 아니라면 예외발생.
     * 3. 예약 정보가 존재하고 RESERVATION 상태가 맞다면 CANCELED 상태로 변경.
     * 4. updatedAt, updatedBy 상태 변경?
     */
    public ReservationDto.CancelReservationResponse cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new NotFoundResourceException("Not found Reservation"));

        //예약 상태가 RESERVED 가 아니라면 예외.
        var reservationStatus = reservation.getReservationStatus();
        if(reservationStatus != ReservationStatus.RESERVED) {
            throw new NotFoundReservationException("Not found Reservation Information");
        }

        reservation.update(ReservationStatus.CANCELED);
        //var canceledReservationEntity = reservationRepository.save(reservation);

        return ReservationDto.CancelReservationResponse.builder()
                //.id(canceledReservationEntity.getId())
                //.success(true)
                //.message("Reservation has been canceled.")
                .build();
    }

    public ReservationDto.FindResponse findById(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new NotFoundResourceException("예약 정보가 없습니다."));

        if (reservation == null) {
            throw new NotFoundResourceException("예약 정보가 없습니다.");
        }

        var findResponse = toFindResponse(reservation);
        return findResponse;
    }

    private ReservationDto.FindResponse toFindResponse (Reservation entity) {
        // getReservableStadium() 을 하는 순간 쿼리가 나가고, reservableStadium 엔티티에 값이 들어와있을 것임
        // getStadium() 도 동일
        // var reservableStadium = entity.getReservableStadium();
        //var stadiumId = reservableStadium.getId();
        //var stadiumName = stadium.getName();

        //var member = entity.getMember();
        //var memberName = member.getName();

        //return ReservationDto.FindResponse.builder()
         //       .id(entity.getId())
           //     .build();
        var reservableStadium = entity.getReservableStadium();
        var stadiumId = reservableStadium.getId();

        //var stadiumName = reservableStadium.getStadium().getName();

        var member = entity.getMember();
        var memberId = member.getId();

        return ReservationDto.FindResponse.builder()
                .id(entity.getId())
                .reservableStadiumId(stadiumId)
                .memberId(memberId)
                .build();
    }



    /**
     * 아주 중요 X 100
     *         1. OneToMany, ManyToOne 단방향, 양방향 매핑 방법에 대해서 공부 v
     *         2. Entity FetchType 을 LAZY 로 되어있는 상태에서 쿼리 나가는거 확인 --> 쿼리 나온거 디코 채널에 올리기 v
     *         3. Entity FetchType 을 EAGER 로 변경하고 쿼리 나가는거 확인 --> 쿼리 나온거 디코 채널에 올리기 v
     *            -- 왜 LAZY 로 설정을 해야 하는지 v
     *
     *         4. N+1 문제를 구글링 해서 -> 이 문제가 뭔지 보고 -> 내가 작성한 코드에서 발생하는 쿼리가 N+1 문제인지 확인
     *            (N+1 발생했다고 판단되면 디코에 올리기  => 호출할 때 발생한 쿼리와 그 코드 디코에 올리기.)
     *         5. N+1 문제 해결하기.(여러 방법 중 어떤 방법을 선택했는지. 이유?)
     *
     *         6. Response 스펙에 맞게 결과 나오는거 확인
     *         7. 검색 API 만들기(Paging + 키워드를 통한 검색).
     */

    /** 6/4
     * 1. reserve 부분 완료해보기.
     * 2. 코드 이해 안되는 부분 공부.
     * 3. jpa 에서 cascade 부분 공부 orphanremoval <- 공부.
     * */

    /** 6/11
     * 1. 예약 취소. //여러 조건들 생각해보고 주석 남기면서.. v (조금 더 수정해보기, updateRequest와 updateResponse 비교.)
     * 2. @ExceptionHandler 키워드를 이용해서 예외가 발생했을 때, 예외 발생 포맷을 어떤 응답으로 내려줄 것인지(postman 예외 문구),,
     * 3. manner(enum으로 수정)랑 tier도 고려해서 예약 가능하게 끔 수정. (매너가 너무 안좋으면 예약 불가, 티어가 너무 높으면 예약 불가.)
     *    - 생각해보니 manner 점수로 예약 가능 여부를 판단하는 것은 필요없어보임.
     * */
}
