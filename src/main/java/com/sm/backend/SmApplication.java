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
     * 3. StadiumAdminController -> 조회(페이징, Paging -> 검색조건을 고려해야 함), 수정(put), 상세, 삭제
     *    - 20개 이상 데이터를 넣어두거나, 생성해두고 -> 조회
     * 4. draw.io 로 등록, 수정, 삭제, 상세, 조회 페이지가 어떻게 생겼을 것 같은지 그리기
     *    - reservable_stadium 도 조회, 수정, 삭제, 상세 만들기
     *    - 데이터가 5개이상 등록
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
     * 1. 다대일, 일대일, 일대다 관계를 표현하는 법. (해결)
     * 2. level과 region같은 enum 클래스로 다룰 테이블을 어떤 패키지에 어떻게 표현해야하지? (해결)
     *    ------> stadium -> entity -> region 클래스 참고.
     * 3. reservation 테이블? (해결)
     * */

    /**
     * POSTMAN 으로 보낼 수 있게.
     *
     *    플랩풋볼 -> 많은 사용자들이 예약
     *       1. member 데이터가 미리 등록되어있어야 함 (회원 등록 API만 간단하게 만들기) -> 1개
     *            - 서비스에서는 상세조회, 등록
     *       2. manager 매니저도 미리 등록되어있어야 함 (등록만) -> 1개
     *            - 서비스에서는 상세조회, 등록
     *       3. stadium 구장도 미리 등록되어있어야 함 (crud 전부) -> 1개
     *       4. ReservableStadium 예약 가능한 구장도 미리 등록되어 있어야 함 (crud 전부) -> 5개
     *
     *            1~4 까지가 선행 되어야 함
     *           회원들이 실제로 ReservableStadium 에 대해서 예약 할 수 있음
     *
     * */


    /**
     * <2023-05-13>
     * 1. 회원 화면 그리기
     * 2. 비지니스 로직 순서도 그리기 FLOW
     * 3. 로직 까지 작성해보기
     * 4. @RestControllerAdvice + @ExceptionHandler(키워드 검색) 통해서 ExceptionHandlerAdvice 만든 후 예외를 발생 시켜보고 before & after
     *
     * (option :김영한님 Spring 기본편 보면서 지금 진행 중인 내 프로젝트에 적용할만한 내용들이 뭐가 있을지 생각해보면서 강의 듣기.)
     */

    public static void main(String[] args) {
        SpringApplication.run(SmApplication.class, args);

    }

}
