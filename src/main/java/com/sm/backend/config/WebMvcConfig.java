package com.sm.backend.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        //등록할 필터를 지정한다.
        filterRegistrationBean.setFilter(new MyFilter());
        //필터는 체인으로 동작하기에 순서가 필요하다. 순서가 낮을수록 먼저 동작한다.
        filterRegistrationBean.setOrder(1);
        //필터를 적용할 URL 패턴을 지정하며, 하나 이상의 패턴을 지정할 수도 있다.
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizationInterceptor())
                .addPathPatterns("/reservations/**");
    }
}