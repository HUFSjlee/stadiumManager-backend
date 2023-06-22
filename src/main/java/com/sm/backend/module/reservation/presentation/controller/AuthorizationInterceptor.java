package com.sm.backend.module.reservation.presentation.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");

        //헤더가 존재하지 않거나 value가 MEMBER_TOKEN이 아닌 경우 return false.(인증 실패)
        //MEMBER_TOKEN
        if (!("MEMBER_TOKEN").equals(authorization)) {
            return false;
        }
        return true;
    }
}
