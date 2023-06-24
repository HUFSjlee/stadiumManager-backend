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
    /**
     * preHandler : 컨트롤러에 도착하기전에 동작하는 메소드로 return값이 true면 진행, false면 멈춤.
     * postHandler : 컨트롤러에 도착하여 view가 랜더링되기 전에 동작.
     * afterCompletion : view가 정상적으로 랜더링된 후에 마지막에 실행.
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");

        log.info("===============================================");
        log.info("==================== BEGIN ====================");
        //헤더가 존재하지 않거나 value가 MEMBER_TOKEN이 아닌 경우 return false.(인증 실패)
        //MEMBER_TOKEN
        if (!("MEMBER_TOKEN").equals(authorization)) {
            log.info("Not a valid token");
            return false;
        }
        log.info("Request URL : {} " + request.getRequestURI());
        return true;
    }
}
