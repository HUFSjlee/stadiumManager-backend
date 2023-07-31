**<div align="center"> :two_men_holding_hands:Stadium Manager!(풋살&축구 소셜 매치 서비스):soccer: </div>**
**<div align="center"> [Backend Server]</div>**
2023.04 ~

## :clipboard: 개요   

### 기획배경  
 - 풋살&축구 매치를 진행하기 위해서는 인원 모집과 장비를 챙겨야 하는 번거로움 있습니다.
 - 위와 같은 번거로움 없이 개인장비만 챙겨 매치에 참가할 수 있는 서비스를 제공하고자 시작하게 되었습니다.
 - 해당 서비스에 등록되어 있는 매치를 예약할 수 있는 **REST API 서버**를 개발하였습니다.  
   - GitHub Repository : https://github.com/stadiumManager/stadium-backend

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
 - Back-end : Java11, Spring Boot 2.7.11, Spring Data JPA, MVC, Gradle
 - Database : MySQL8.0, Docker
 - Tools : IntelliJ, Git(+Git Flow)

### 개발 이슈
 - 왜 이렇게 했으며, 무엇을 해결하려고 했는지?
 - 패키지 구조 DDD --> 왜 이런 패키지 구조로 작성했는지, 각 패키지 안에 클래스 구조(어떤 클래스)는 어떻게 되어있는지
 - JPA N+1 문제
 - ExceptionHandler -> AOP
 - Filter, Interceptor
 - 외부 API 사용 방법
 - 동시성처리(redis) (x)
 - Cloud : AWS(EC2,S3,RDS,,,)
   








# stadium-backend

## ERD 
https://github.com/stadiumManager/stadium-backend/wiki/ERD

## 예상 화면 설계
https://github.com/stadiumManager/stadium-backend/wiki/%EA%B4%80%EB%A6%AC%EC%9E%90-%ED%8E%98%EC%9D%B4%EC%A7%80-%ED%99%94%EB%A9%B4-%EC%84%A4%EA%B3%84






Filter, Interceptor도 작성하면 좋다.
