**<div align="center"> :two_men_holding_hands:Stadium Manager!(풋살&축구 소셜 매치 서비스):soccer: </div>**
**<div align="center"> [Backend Server]</div>**
2023.04 ~

## :clipboard: 개요   

### 기획 배경  
 - 풋살 또는 축구 매치 진행할 때, 인원 모집과 장비를 챙겨야 하는 번거로움이 있습니다. 이런 번거로움 없이 개인 장비만 챙겨 매치에 참여할 수 있는 풋살 예약 서비스입니다.
 - 해당 서비스에 등록된 매치를 예약할 수 있는 REST API 서버를 개발하였습니다.  

### 프로젝트 목표
 - 매치 진행을 위해 이용 가능한 구장을 서비스에 등록하여 유저에게 제공합니다.
 - 구장별 매치 일정을 유저가 신청 및 취소할 수 있습니다.
 - 비즈니스 로직에 따라 예약 신청 성공/실패됩니다.
   
   - 유저들의 레벨이 존재합니다. 매치마다 참여 가능 레벨을 설정해 두어 실력별 매치가 가능합니다. (참여 가능 레벨을 초과하였을 때는 예약이 되지 않습니다.)
     
   - 유저들의 캐시가 없다면 예약이 불가능합니다. 캐시가 존재한다면 예약이 성공되며 캐시가 차감됩니다.
     
   - 남성, 여성, 혼성 매치가 있습니다. 성별에 맞지 않은 매치는 예약이 불가능합니다.
     
   - 구장별 이용가능한 인원수가 초과되면 예약이 불가능합니다.
     
 - 구장의 주소 검색
   - 카카오 로컬 API 사용으로 구장 주소를 제공합니다.
     
### 기능 소개
 - 구장 관리 API 개발
 - 유저 정보/매니저 정보/구장 정보 API 개발
 - 예약 신청 & 취소 & 변경 API 개발
 - 예약된 매치 목록 확인 API 개발
 - 예약 시 구장의 상세 주소 제공을 위한 카카오 로컬 API 적용

### 기술 스택
 - Back-End : Java11, Spring Boot 2.7.11, MVC, Retrofit
 - ORM : Spring Data JPA
 - Database : MySQL8.0,
 - Infra : Docker
 - REST API Documentation : Swagger, Asciidoc
 - Tools : IntelliJ, Git

### 패키지 구조
- 도메인형 구조를 선택
  
  - 어떤 도메인들이 사용되고 있는지를 한눈에 파악할 수 있다는 점이 장점이라 생각되어 선택
    
  - 도메인별 관심사 분리와 집중을 위해 도메인 패키지 아래에 [Presentation-Domain-Infra 형식의 Layered Architecture](https://martinfowler.com/bliki/PresentationDomainDataLayering.html) 적용
    
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

### Sequence
![시퀀스다이어그램](https://github.com/HUFSjlee/stadiumManager-backend/assets/67497759/8945b8f1-e044-4171-9971-c9594f7a2bc3)


### 서비스 구조
![프로젝트_아키텍쳐](https://github.com/HUFSjlee/stadiumManager-backend/assets/67497759/460cd781-69fc-4c5c-a4a7-a74691ce5781)

### 프로젝트 진행 중 이슈

- JPA N+1 문제 
  - 'fetchType = Eager' -> 'fetchType = Lazy' 변경
  - FetchJoin을 활용하여 문제를 개선하였습니다.
 
- Response 관리
  - 공통 응답 코드를 BaseResponse 클래스에서 관리
  - 일관된 응답 형식을 위한 응답 스펙 클래스 정의
  - 클래스 내 필드는 code(응답 코드) / msg(응답 메세지) / result(응답 결과) / timestamp(응답 시간)을 포함
 
- 예외처리
  - 발생하는 예외를 공통으로 처리하기 위해 적용한 방식
    - @ExceptionHandler 적용, 각 클래스에서 발생하는 예외를 처리할 수 있는 ExceptionHandler 사용
    - @RestControllerAdvice, GlobalExceptionHandler 클래스를 만들고, 클래스 내 적절한 예외를 처리하기 위해 만든 Custom Exception을 공통으로 처리    
프로젝트 진행 중 이슈는 개인 블로그에 기록하였습니다.

- 관계형 데이터베이스(RDBMS) N:M(다대다) 문제
  - 관계형 데이터베이스의 사용으로 N:M 관계에 있던 두 테이블을 N:1/1:N 으로 해결하길 원함.
  - 문제 해결을 위해 N:M 관계에 있는 두 테이블 사이에 중간 테이블을 하나 두어 Entity로 만들고, Entity의 필드에 @OneToMany, @ManyToOne 어노테이션(annotation)을 사용하여 N:M 관계를 풀어냄
 
- API 문서화 도구 Swagger의 오류 문제 (~ing)
  - 프로젝트 내 예약 기능 부분에 동시성 처리를 위해 redis 관련 의존성을 추가했더니 예상치 못한 오류 로그를 만남
  - 우선 동시성 처리가 중요하기 때문에 테스트 코드를 작성하고 테스트를 통과하는지 확인 후에 다시 Swagger를 필요로 할 때, 이 오류 해결 예정

- 카카오 로컬 API 적용 (외부 API)
  - 경기장의 위치 정보(주소)를 검색할 기능이 필요하다고 판단하여 카카오 로컬 API를 적용
  - Kakao Developers에서 제공하는 요청과 응답 양식에 맞게 Entity를 추가
  - 응답으로 필요한 필드는 DTO 클래스로 만들어서 관리

- 외부 API 사용 시, 의존성 문제
  - 외부 API와 관련된 필드를 가지고 있는 ConversionCoordinatesSystem 클래스를 어떤 패키지에 위치시켜야 하는지에 대한 고민
  - 고민 끝에, domain 패키지 하위에 위치시키기로 결정. 이유는?
    - 지금까지 진행했던 패키지의 구조상, 외부 API 관련된 필드를 가지고 있는 ConversionCoordinatesSystem 클래스를 Infrastructure 패키지에 위치시키게되면, domain -> infrastructure 형식의 의존성 방향을 나타내는데
      이렇게 되면 infrastructure가 DB 통신과 외부 통신 등의 세부 구현이 변경될 때, domain 패키지도 변경이 일어날 수 있다고 판단





https://hufsjlee.github.io/categories/project/


## ERD 
https://github.com/stadiumManager/stadium-backend/wiki/ERD



