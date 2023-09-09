package com.sm.backend.facade;

import com.sm.backend.module.reservation.domain.entity.ReservationStatus;
import com.sm.backend.module.reservation.domain.service.ReservationService;
import com.sm.backend.module.reservation.infrastructure.ReservationRepository;
import com.sm.backend.module.reservation.presentation.dto.ReservationDto;
import com.sm.backend.module.stadium.domain.entity.Stadium;
import com.sm.backend.module.stadium.infrastructure.repository.ReservableStadiumRepository;
import com.sm.backend.module.stadium.infrastructure.repository.StadiumRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest

public class RedissonLockReservationFacadeTest {
    @Autowired
    private StadiumRepository stadiumRepository;
    @Autowired
    private ReservableStadiumRepository reservableStadiumRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private RedissonClient redissonClient;


    private static final int THREAD_COUNT = 30;

    @BeforeEach
    public void setUp() {
        var stadium = stadiumRepository.findById(1L).orElseThrow();
        var reservableStadium = reservableStadiumRepository.findById(1L).orElseThrow();
        int maximumPersonnel = reservableStadium.getStadium().getMaximumPersonnel();

    }

    @AfterEach
    public void tearDown() {
        stadiumRepository.deleteAll();
        reservationRepository.deleteAll();
    }

    @Test
    void requests_30_at_the_same_time() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    // Redisson 락을 사용하여 예약 시도
                    reservationService.reserve(new ReservationDto.CreateRequest(1L, 3L, ReservationStatus.RESERVED));
                } catch (Exception ex) {
                    // 예약 실패한 경우 처리
                    System.out.println("예약 실패");
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        // 최대 예약 가능 인원은 18명이므로, 예약된 인원은 18명과 같거나 작아야 합니다.
        long count = reservationRepository.count();
        assertTrue(count <= 18);
        assertEquals(18, count);
    }
}

        /**
         * 1.(setup) 'stadium 테이블에'
         *    'stadium_id' 가 '1'인 구장의 참여 가능한 최대 인원이 18명
         *    -> stadium 테이블에서 stadium_id가 1인 구장의 maximum person
         *
         * 2(.test) 동시에 20명이 'stadium_id' 가 '1'인 구장에 예약을 시도.
         *    -> reserve 메서드를 사용해서 20명이 동시에 예약.
         *
         * 3(assert). 결과 -> 18명만 예약되어야 함.
         *
         * t1) 락을 안걸었을 때
         * t2) 락을 걸었을 때
         * **/

//1. redis Lock 부분만 Test 코드를 짜서 내가 생각한 시나리오대로 동작을 하는지 검증.
//2. 블로그 -> n+1 같은거 세세하게 다시 작성해보기. 좀
// 길고 자세하게. 보기 좋게.