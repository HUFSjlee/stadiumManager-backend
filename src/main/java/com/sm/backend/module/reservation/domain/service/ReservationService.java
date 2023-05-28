package com.sm.backend.module.reservation.domain.service;

import com.sm.backend.common.exception.NotFoundResourceException;
import com.sm.backend.module.member.domain.entity.Member;
import com.sm.backend.module.member.infrastructure.repository.MemberRepository;
import com.sm.backend.module.reservation.domain.entity.Reservation;
import com.sm.backend.module.reservation.domain.entity.ReservationStatus;
import com.sm.backend.module.reservation.infrastructure.ReservationRepository;
import com.sm.backend.module.reservation.presentation.dto.ReservationDto;
import com.sm.backend.module.stadium.domain.entity.ReservableStadium;
import com.sm.backend.module.stadium.infrastructure.repository.ReservableStadiumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        ReservableStadium reservableStadium = reservableStadiumRepository.findById(request.getReservableStadiumId()).orElseThrow(() -> new NotFoundResourceException("Reservable stadium not found"));

        Reservation reservation = Reservation.builder()
                .member(member)
                .reservableStadium(reservableStadium)
                .reservationStatus(ReservationStatus.RESERVATION)
                .build();

        int point = reservation.getMember().getPoint();
        int rentalPrice = reservation.getReservableStadium().getRentalPrice();
        if(point > rentalPrice) {

        }

        var reservedEntity = reservationRepository.save(reservation);

        return ReservationDto.CreateResponse.builder().id(reservedEntity.getId()).build();

        /**
         * 1. 예약 가능한 구장들의 정보를 입력
         * 2. 멤버는 예약 가능한 구장을 선택하여 예약을 함.
         * 3. 멤버가 예약을 하면 멤버가 가지고있는 포인트를 예약 가능한 구장의 이용 가격에 맞게 포인트를 차감하고 예약 신청 완료.
         *  3.1 보유하고 있는 포인트가 이용가격보다 적다면 예약 불가능.
         *  3.2 보유하고 있는 포인트가 이용가격보다 많다면 예약 가능.
         * 4. 예약 신청이 되면 '예약 상태'가 '신청'으로 바뀜.
         * */

        /**
         * <과제>
         * 1. reserve 부분 완료해보기.
         * 2. 코드 이해 안되는 부분 공부.
         * 3. jpa 에서 cascade 부분 공부 orphanremoval <- 공부.
         * */

    }

    /**
     * member랑 reservableStadium이 어떤식으로 되어있는지 디버그 포인트로 확인
     * -> 디버그 포인트로 확인 결과, member와 reservableStadium 전부 null
     * */
    public ReservationDto.FindResponse findById(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new NotFoundResourceException("예약 정보가 없습니다."));

        if (reservation == null) {
            throw new NotFoundResourceException("예약 정보가 없습니다.");
        }

        var findResponse = toFindResponse(reservation);
        return findResponse;
    }

    /*
    * findById 할 때 null값은 정상(연관관계 매핑되어있는 객체들 안에 있는 컬럼들이 null인 것들은 정상 -> fetchType 으로 인한 점들 확인해보기.)
    * */
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

        /**
         * 실제로 응답을 내줄 것들만 정의. -> 다른 응답값도 필요할 것 같을 때는 추가해줘야 함.
         * */
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
     *
     */
}
