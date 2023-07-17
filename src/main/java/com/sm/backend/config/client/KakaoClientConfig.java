package com.sm.backend.config.client;

import com.sm.backend.module.kakao.infrastructure.client.KakaoLocalApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class KakaoClientConfig {

    private static final String baseUrl = "https://dapi.kakao.com";

    @Bean
    public KakaoLocalApiClient kakaoLocalApi() {
        // TODO baseUrl 은 yml 설정하고 -> properties -> 이 클래스로 호출해서 넣을 수 있도록
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(KakaoLocalApiClient.class);
    }
}
