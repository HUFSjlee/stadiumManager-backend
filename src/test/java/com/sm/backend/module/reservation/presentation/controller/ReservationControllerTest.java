package com.sm.backend.module.reservation.presentation.controller;

import com.sm.backend.module.reservation.domain.service.ReservationService;
import com.sm.backend.module.reservation.presentation.dto.ReservationDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    @DisplayName("reserve request test")
    void reserve() throws Exception {
        ReservationDto.CreateResponse response = new ReservationDto.CreateResponse(1L);
        when(reservationService.reserve(any(ReservationDto.CreateRequest.class))).thenReturn(response);

        mockMvc.perform(post("/reservations/reserve")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
