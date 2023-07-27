package com.sm.backend.config.client;

import com.sm.backend.module.address.infrastructure.client.KakaoLocalApiClient;
import com.sm.backend.module.address.infrastructure.client.MoisLocalApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class MoisClientConfig {
    private static final String baseUrl = "https://business.juso.go.kr";

    @Bean
    public MoisLocalApiClient moisLocalApiClient() {
        // TODO baseUrl 은 yml 설정하고 -> properties -> 이 클래스로 호출해서 넣을 수 있도록
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(MoisLocalApiClient.class);
    }
}
