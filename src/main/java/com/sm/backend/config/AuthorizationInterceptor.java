package com.sm.backend.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");

        log.info("===============================================");
        log.info("==================== BEGIN ====================");
        if (!("MEMBER_TOKEN").equals(authorization)) {
            log.info("Not a valid token");
            return false;
        }
        log.info("Request URL : {} " + request.getRequestURI());
        return true;
    }
}

