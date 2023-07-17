package com.sm.backend.module.reservation.presentation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sm.backend.module.reservation.domain.entity.ReservationStatus;
import com.sm.backend.module.reservation.domain.service.ReservationService;
import com.sm.backend.module.reservation.presentation.dto.ReservationDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@AutoConfigureRestDocs
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    @DisplayName("구장을 예약한다.")
    void reserve() throws Exception {

        // given
        long memberId = 1L;
        long reservableStadiumId = 2L;
        ReservationStatus reservationStatus = ReservationStatus.RESERVED;

        ReservationDto.CreateRequest request = new ReservationDto.CreateRequest(memberId, reservableStadiumId, reservationStatus);
        ReservationDto.CreateResponse response = new ReservationDto.CreateResponse(1L);

        // Stubbing the necessary methods in reservationService
        when(reservationService.reserve(any(ReservationDto.CreateRequest.class))).thenReturn(response);
        //파마미터없이 any로 쓰는 법도 있고, 그냥 any로 쓰면 파라미터에 null값이 들어갈 수 있는데 null이 들어가는지 안들어가는지 확인.



        // when and then
        // Preprocessors.preprocessXXX -> JSON을 깔끔하게 포맷팅하여 문서를 생성해 줌.
        mockMvc.perform(post("/reservations/reserve")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcRestDocumentation.document("reserve",
                        Preprocessors.preprocessRequest(prettyPrint()), //requestFeilds나 response를 작성할 수 있다.(보완)
                        Preprocessors.preprocessResponse(prettyPrint())))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
