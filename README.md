**<div align="center"> :two_men_holding_hands:Stadium Manager!(풋살&축구 소셜 매치 서비스):soccer: </div>**
**<div align="center"> [Backend Server]</div>**
2023.04 ~

## :clipboard: 개요   

### 기획배경  
 - 풋살 & 축구 매치를 진행하기 위해서는 인원 모집과 장비를 챙겨야 하는 번거로움이 있습니다.
 - 위와 같은 번거로움 없이 개인장비만 챙겨 매치에 참여할 수 있는 서비스를 제공하고자 시작하게 되었습니다.
 - 해당 서비스에 등록된 매치를 예약할 수 있는 REST API 서버를 개발하였습니다.  

### 프로젝트 목표
 - 매치 진행을 위해 이용 가능한 구장을 서비스에 등록하고 제공합니다.
 - 구장 별 매치 일정을 유저가 신청 및 취소할 수 있습니다.
 - 비즈니스 로직에 따라 예약 신청 성공/실패 됩니다.
   
   - 유저들의 레벨이 존재합니다. 매치마다 참여 가능 레벨을 설정해두어 실력별 매치가 가능합니다.(참여 가능 레벨이 초과되었을 때에는 예약이 되지 않습니다) 
   - 유저들의 캐시가 없다면 예약이 불가능합니다. 캐시가 존재한다면 예약이 성공되며 캐시가 차감됩니다.
   - 남성, 여성, 혼성 매치가 있습니다. 성별에 맞지않은 매치는 예약이 불가능합니다.
   - 구장별 이용가능한 인원 수가 초과되면 예약이 불가능합니다.
     
 - 구장의 주소 검색
   - 카카오 로컬 API 사용으로 구장 주소를 제공합니다.  
### 기능 소개
 - 구장 관리 API 개발
 - 유저 정보/매니저 정보/구장 정보 API 개발
 - 예약 신청 & 취소 &변경 API 개발
 - 예약된 매치 목록 확인 API 개발
 - 예약 시 구장의 상세 주소 제공을 위한 카카오 로컬 API 적용

### 기술 스택
 - Back-End : Java11, Spring Boot 2.7.11, MVC, Retrofit
 - ORM : Spring Data JPA
 - Database : MySQL8.0, Docker
 - REST API Documentation : Swagger, Asciidoc
 - Tools : IntelliJ, Git

### 패키지 구조

- 도메인형 구조를 선택
  - 어떤 도메인들이 사용되고 있는지를 한눈에 파악할 수 있다는 점이 장점이라 생각되어 선택
  - 도메인별 관심사 분리와 집중을 위해 도메인 패키지 아래에 [Presentation-Domain-Infra 형식의 LayeredArchitecture](https://martinfowler.com/bliki/PresentationDomainDataLayering.html) 적용
  - common / config / module 패키지 분리
    - common (base, exception, response)
        - 날짜와 시간처럼 중복되어 사용되는 필드를 포함하고 있는 클래스
        - ExceptionHandler, Custom Exception을 포함한 모든 Exception
        - 응답 코드와 응답 내용과 관련된 Base Response와 Pagination과 관련된 Page Response 등 Response 분리
    - config (client)
        - 카카오 API와 같은 외부 클라이언트를 포함
        - Filter와 Interceptor 관리
        - Rest API Documentation 관리
    - module (stadium / reservation / member / manager / address)
        - 서비스 내 필요한 도메인 관리

### 서비스 구조
![프로젝트_아키텍쳐](https://github.com/HUFSjlee/stadiumManager-backend/assets/67497759/460cd781-69fc-4c5c-a4a7-a74691ce5781)

### 프로젝트 진행 중 이슈
프로젝트 진행 중 이슈는 개인 블로그에 기록하였습니다.

https://hufsjlee.github.io/categories/project/



## ERD 
https://github.com/stadiumManager/stadium-backend/wiki/ERD



