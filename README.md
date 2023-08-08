**<div align="center"> :two_men_holding_hands:Stadium Manager!(풋살&축구 소셜 매치 서비스):soccer: </div>**
**<div align="center"> [Backend Server]</div>**
2023.04 ~

## :clipboard: 개요   

### 기획배경  
 - 풋살&축구 매치를 진행하기 위해서는 인원 모집과 장비를 챙겨야 하는 번거로움 있습니다.
 - 위와 같은 번거로움 없이 개인장비만 챙겨 매치에 참가할 수 있는 서비스를 제공하고자 시작하게 되었습니다.
 - 해당 서비스에 등록되어 있는 매치를 예약할 수 있는 **REST API 서버**를 개발하였습니다.  

### 프로젝트 목표
 - 서비스에 제휴 구장을 등록하고 구장의 매치 일정을 유저가 직접 확인하고 신청할 수 있습니다.
 - 신청한 매치를 취소할 수 있습니다.
 - 유저들의 레벨 포인트가 존재하고 매치마다 참여 가능 레벨 포인트를 두어 실력별 매치가 가능합니다.

### 기능 소개
 - 서비스 관리자 권한용 API 개발
 - 유저 정보/매니저 정보/구장 정보 API 개발
 - 예약 신청&취소&변경 API 개발
 - 예약된 매치 목록 확인 API 개발
 - 예약시 구장의 상세 주소 확인을 위한 **카카오 로컬 API** 적용

### 기술 스택
 - Back-End : Java11, Spring Boot 2.7.11, Spring Data JPA, MVC, Retrofit, Gradle
 - Database : MySQL8.0, Docker
 - REST API Documentation : Swagger, Asciidoc
 - Tools : IntelliJ, Git(+Git Flow)

### 개발 이슈  
 - Layered Architecture 적용
    - 각 도메인별 관심사를 집중시키기 위해 도메인별로 presentation - domain - infrastructure 3 계층 구조로 나누었습니다.
 - JPA N+1 문제
    - FetchJoin을 활용하여 문제를 개선했습니다. 
 - 공통 예외 처리
   - @ExceptionHandler 적용
      - 각 클래스에서 발생하는 예외를 처리할 수 있는 Exception Handler를 추가
   - AOP
      - 모든 클래스에서 발생하는 예외를 처리하기 위해 사용
   - GlobalExceptionHandler 클래스에서 @RestControllerAdvice 어노테이션 사용
      - 개발자가 작성한 Custom Exception을 공통으로 처리하기 위해 사용하였습니다.
 - Filter, Interceptor
 - 외부 API (카카오 로컬 API) 적용 방법
   - OkHttp 라이브러리와 Retrofit 라이브러리
    - Annotation 사용을 통해 가독성 측면에서 이점을 가질 수 있고, 클라이언트 <-> 서버간에 Http 통신을 위해 사용했습니다.

 - memberId, reservableStadiumId 병합 컬럼 JPA 쿼리 메소드


 - 왜 이렇게 했으며, 무엇을 해결하려고 했는지?
 - 패키지 구조 DDD --> 왜 이런 패키지 구조로 작성했는지, 각 패키지 안에 클래스 구조(어떤 클래스)는 어떻게 되어있는지
 - 동시성처리(redis) (x)
 - Cloud : AWS(EC2,S3,RDS,,,) (x)
 - 테스트코드 (x)



## ERD 
https://github.com/stadiumManager/stadium-backend/wiki/ERD

## 예상 화면 설계
https://github.com/stadiumManager/stadium-backend/wiki/%EA%B4%80%EB%A6%AC%EC%9E%90-%ED%8E%98%EC%9D%B4%EC%A7%80-%ED%99%94%EB%A9%B4-%EC%84%A4%EA%B3%84

