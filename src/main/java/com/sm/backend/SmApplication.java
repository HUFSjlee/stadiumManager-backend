package com.sm.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmApplication {

    /**  (N:M / 1:N 등 어려울 수 있음.)
     * 다음주 까지 "엔티티 + 테이블 설계" 가 되어있어야 함 -> 그리고 ERD 만들어서 깃허브 wiki 에 그림 붙여넣고 -> 각각 테이블이 무슨 역할을 하는지 간단한 설명
     *
     * 1. 플랩풋볼 보면서 테이블과 엔티티 설계하기
     * 2. JPA 옵션 설정하기 (v완료.)
     * 3. H2 콘솔에서 테이블이 생성되어있는지 확인하고 -> CREATE구문은 NONE으로 설정(DDL auto라는 옵션 참고.), INSERT 구문을 몇개 만들고 ->
     *    서버가 실행될때 테이블이 생성되면서 -> INSERT 도 되게끔
     *      - 구장 등록 (샘플 5개), 회원 (샘플 15개), 매니저 (샘플 5개), 예약 가능한 구장 (샘플 여러개) (v완료.)
     */

    /**
     * 1. ERD 수정  v
     * 2. ERD 에 맞게 Stadium(Entity) 처럼 Entities 만들기 v
     *
     * 3. StadiumAdminController -> 조회(페이징, Paging -> 검색조건을 고려해야 함), 수정(put), 상세, 삭제
     *    - 20개 이상 데이터를 넣어두거나, 생성해두고 -> 조회
     *
     * 4. draw.io 로 등록, 수정, 삭제, 상세, 조회 페이지가 어떻게 생겼을 것 같은지 그리기
     *    - reservable_stadium 도 조회, 수정, 삭제, 상세 만들기
     *    - 데이터가 5개이상 등록
     *
     *
     * 2번 작업할 때 브랜치 이름을 feature/issueNumber(feature/2) -> 작업 끝나면 PR -> merge
     * 3번도 동일하게
     * 4번도 동일하게
     *
     * 총 3개의 브랜치를 생성해서 작업해야 함
     *
     * 5. (다음주에 같이) 회원이 reservable_stadium 에 있는 것 중 1개를 선택해서 예약
     */



    /**
     * 과제 중 문제점.
     * 1. 다대일, 일대일, 일대다 관계를 표현하는 법.
     * 2. level과 region같은 enum 클래스로 다룰 테이블을 어떤 패키지에 어떻게 표현해야하지?
     * 3. reservation 테이블?
     * */

    public static void main(String[] args) {
        SpringApplication.run(SmApplication.class, args);

    }

}
