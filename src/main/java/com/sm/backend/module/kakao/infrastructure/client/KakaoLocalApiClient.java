package com.sm.backend.module.kakao.infrastructure.client;
import com.sm.backend.module.kakao.domain.service.dto.Coordinate;
import com.sm.backend.module.kakao.domain.service.dto.Location;
import lombok.Getter;
import org.hibernate.internal.CoordinatingEntityNameResolver;
import retrofit2.Call;
import retrofit2.http.*;

public interface KakaoLocalApiClient {

    @Headers("Authorization: KakaoAK fb56d4f98c3638072a0e6bce28ebc23b")
    @GET("/v2/local/search/address.json")
    Call<Location> searchAddressList(@Query("query") String query,
                                     @Query("page") int page,
                                     @Query("size") int size);

    @Headers("Authorization: KakaoAK fb56d4f98c3638072a0e6bce28ebc23b")
    @GET("/v2/local/geo/coord2regioncode.json")
    Call<Coordinate> searchCoordinateList(@Query("x") String x,
                                          @Query("y") String y,
                                          @Query("inputCoord") String inputCoord,
                                          @Query("outputCoord") String outputCoord);
}
