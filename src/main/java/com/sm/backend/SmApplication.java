package com.sm.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmApplication {

    /**
     * 다음주 까지 "엔티티 + 테이블 설계" 가 되어있어야 함 -> 그리고 ERD 만들어서 깃허브 wiki 에 그림 붙여넣고 -> 각각 테이블이 무슨 역할을 하는지 간단한 설명
     *
     * 1. 플랫 풋볼 보면서 테이블과 엔티티 설계하기
     * 2. JPA 옵션 설정하기
     * 3. H2 콘솔에서 테이블이 생성되어있는지 확인하고 -> INSERT 구문을 몇개 만들고 -> 서버가 실행될때 테이블이 생성되면서 -> INSERT 도 되게끔
     *      - 구장 등록 (샘플 5개), 회원 (샘플 15개), 매니저 (샘플 5개), 예약 가능한 구장 (샘플 여러개)
     */

    public static void main(String[] args) {
        SpringApplication.run(SmApplication.class, args);
    }

}
