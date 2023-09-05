package com.sm.backend.facade;

import com.sm.backend.module.reservation.domain.entity.ReservationStatus;
import com.sm.backend.module.reservation.domain.service.ReservationService;
import com.sm.backend.module.reservation.infrastructure.ReservationRepository;
import com.sm.backend.module.reservation.presentation.dto.ReservationDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RedissonLockReservationFacadeTest {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationService reservationService;

    private static final int THREAD_COUNT = 100;

    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown() {
        reservationRepository.deleteAll();
    }

    @Test
    void requests_20_at_the_same_time() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    reservationService.reserve(new ReservationDto.CreateRequest(1L, 3L, ReservationStatus.RESERVED));
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        // 기대값: 18
        long count = reservationRepository.count();
        assertEquals(18, count);

    }
}

//1. redis Lock 부분만 Test 코드를 짜서 내가 생각한 시나리오대로 동작을 하는지 검증.
//2. 블로그 -> n+1 같은거 세세하게 다시 작성해보기. 좀 길고 자세하게. 보기 좋게.