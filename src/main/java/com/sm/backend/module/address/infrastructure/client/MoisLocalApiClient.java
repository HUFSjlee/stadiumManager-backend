package com.sm.backend.module.address.infrastructure.client;

import com.sm.backend.module.address.domain.service.dto.MoisRoadNameAddress;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface MoisLocalApiClient {
    @Headers("devU01TX0FVVEgyMDlzMDcyMDE0NTAzOTExMzk0Njq=")
    @GET("/addrlink/addrLinkApi.do")
    Call<MoisRoadNameAddress> searchRoadAddressList(@Query("confmKey") String confmKey,
                                                @Query("currentPage")  int currentPage,
                                                @Query("countPerPage") int countPerPage,
                                                @Query("keyword") String keyword,
                                                @Query("resultType") String resultType,
                                                @Query("hstryYn") String hstryYn,
                                                @Query("firstSort") String firstSort,
                                                @Query("addInfoYn") String addInfoYn);


}
