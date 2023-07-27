package com.sm.backend.module.address.infrastructure.client;
import com.sm.backend.module.address.domain.service.dto.ConversionAddressByCoordinates;
import com.sm.backend.module.address.domain.service.dto.AdministrativeDistrictByCoordinates;
import com.sm.backend.module.address.domain.service.dto.ConversionCoordinatesSystem;
import com.sm.backend.module.address.domain.service.dto.SearchAddress;
import retrofit2.Call;
import retrofit2.http.*;

public interface KakaoLocalApiClient {

    @Headers("Authorization: KakaoAK fb56d4f98c3638072a0e6bce28ebc23b")
    @GET("/v2/local/search/address.json")
    Call<SearchAddress> searchAddressList(@Query("query") String query,
                                          @Query("page") int page,
                                          @Query("size") int size);

    @Headers("Authorization: KakaoAK fb56d4f98c3638072a0e6bce28ebc23b")
    @GET("/v2/local/geo/coord2regioncode.json")
    Call<AdministrativeDistrictByCoordinates> searchCoordinateList(@Query("x") String x,
                                                                   @Query("y") String y,
                                                                   @Query("inputCoord") String inputCoord,
                                                                   @Query("outputCoord") String outputCoord);

    @Headers("Authorization: KakaoAK fb56d4f98c3638072a0e6bce28ebc23b")
    @GET("/v2/local/geo/coord2address.json")
    Call<ConversionAddressByCoordinates> convertAddressToCoordinates(@Query("x") String x,
                                                                     @Query("y") String y,
                                                                     @Query("input_coord") String inputCoord);

    @Headers("Authorization: KakaoAK fb56d4f98c3638072a0e6bce28ebc23b")
    @GET("/v2/local/geo/transcoord.json")
    Call<ConversionCoordinatesSystem> convertCoordinatesSystem(@Query("x") Double x,
                                                               @Query("y") Double y,
                                                               @Query("input_coord") String inputCoord,
                                                               @Query("output_coord") String outputCoord);
}
