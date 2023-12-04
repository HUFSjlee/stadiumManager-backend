**<div align="center"> ⚽ Stadium Manager!(풋살&축구 소셜 매치 서비스) 🥅 </div>**
**<div align="center"> [Backend Server]</div>**
2023.04 ~

## :clipboard: 개요   

### 기획 배경  
 - 풋살 또는 축구 매치 진행할 때, 인원 모집과 장비를 챙겨야 하는 번거로움이 있습니다. 이런 번거로움 없이 개인 장비만 챙겨 매치에 참여할 수 있는 풋살 예약 서비스입니다.
 - 해당 서비스에 등록된 매치를 예약할 수 있는 REST API 서버를 개발하였습니다.

### 기술 스택
 - Back-End : Java11, Spring Boot 2.7.11, MVC, Retrofit
 - ORM : Spring Data JPA
 - Database : MySQL8.0, Redis(Redisson library)
 - Infra : Docker
 - REST API Documentation : Swagger, Asciidoc
 - Tools : IntelliJ, Git
 - DevOps :  AWS - EC2, RDS (진행중)
   

## 서비스 구조 (아키텍처)
![프로젝트_아키텍쳐](https://github.com/HUFSjlee/stadiumManager-backend/assets/67497759/460cd781-69fc-4c5c-a4a7-a74691ce5781)


## Sequence (동시성 처리 시, 시퀀스)
![시퀀스다이어그램](https://github.com/HUFSjlee/stadiumManager-backend/assets/67497759/8945b8f1-e044-4171-9971-c9594f7a2bc3)


## ERD 
![image](https://github.com/HUFSjlee/stadiumManager-backend/assets/67497759/54775b8f-735d-48b5-ae2f-4d5745e6d1f2)
   


## 프로젝트 진행 중 이슈

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


- 외부 서비스와 통신하기 위한 방법 (with Retrofit)
  - 카카오 로컬 API 와 같이 외부 서비스와 통신을 위해서는 어떤 라이브러리를 사용할 수 있을지에 대한 고민이 생김
    - 주소 검색 기능을 추가하고 싶어서 카카오 로컬 API와 통신이 가능하도록 해야했다. 여러가지를 검색해 보니, WebFlux와 WebClient, Retrofit의 존재를 알게되었다.
    - 카카오 로컬 API 적용을 위해, server와 client 간 HTTP 통신을 위해 Retrofit을 사용하기로 결정

      
  - Retrofit을 선택한 이유는?
    - 간편한 API 정의가 가능하고 가독성이 좋다는 측면에서 선택
    - OkHttp 라이브러리를 사용함으로써 인터페이스를 통해 편리하고 간결하다는 점이 장점으로 다가옴

  - Retrofit을 사용해서 외부 API를 처음 적용해보고 느낀점
    - server와 client 통신을 위해 라이브러리 사용은 처음해보았는데, OkHttp 라이브러리 인터페이스를 통해 생각보다 간편하게 사용할 수 있었다.


- JPA Query Method
  - JPA Query Method란 무엇일까?
    - JPA는 ORM(Object-Relation Mapping) 기술을 구현한 것이고, 데이터베이스의 테이블을 자바 객체로 매핑하여 데이터를 조작하는 작업을 간편하게 처리할 수 있다
    - 데이터베이스 SQL 구문과 같이 쿼리를 직접 작성하지 않고도 자바 메서드 이름 규칙을 따라 간단한 메서드 선언으로 원하는 데이터를 추출할 수 있다.
      
  - 프로젝트 내 예약 기능에서 사용한 JPA 쿼리 메서드
    - 멤버(A)가 구장(B)를 예약 했다고 가정했을 때, 멤버(A)가 구장(B)을 중복으로 예약되는 것을 방지해야 했다.
    - 그래서, 멤버의 ID와 예약 가능한 구장의 ID가 이미 존재하는지를 확인하기 위해서 ‘existByMemberIdAndReservableStadiumId’ 라는 쿼리 메서드를 정의하여 처리


- Fallback 이란..?
  - 외부 API가 예상치 못한 오류로 인해 동작하지 않는다면?
    - 프로젝트 내에 주소 검색을 기능을 위해 카카오 로컬 API를 적용했는데, 만약 카카오 API가 먹통이 된다면 어떻게 이를 처리할 수 있을지에 대한 생각을 해보게 됨
    - 이에 대해 궁금해서 알아보던 중, Fallback에 대해 알게되었다.

  - 해결 방안
    - 행정안전부 API 등, 다른 외부 API를 추가하고 카카오 로컬 API가 동작하지 않을 때에는 행정안전부 API로 대체될 수 있도록 로직을 작성해야 한다는 사실을 알게됨


- Filter 적용
  - Filter를 적용한 이유는?
    - servlet 실행 전과 후에 요정(request)과 응답(response)에 대한 필터링을 하기위해 Filter를 적용
  - Filter의 동작 과정
    - Filter는 1)초기화 -> 2)필터링 -> 3)소멸 의 단계를 거친다.
    - Filter 인터페이스를 구현할 클래스(MyFilter) 생성한 후 메서드를 구현
      - init()
      - doFilter() -> 서비스의 request와 response에 대한 필터링 로직 작성
      - destroy()
    - @Component
      -Filter를 적용하기 위해서 스프링빈(Spring Bean) 으로 등록하여 사용


- Interceptor 적용
   - Interceptor 사용 이유?
     - 컨트롤러(Controller)에 접근하는 과정에서 권한 관련 로직을 제어하기 위해 사용.
   - HandlerInterceptor
     - HandlerInterceptor 인터페이스를 구현할 클래스를 생성
     - preHandle()은 무엇인가?
       - 컨트롤러(Controller) 접근하기 전 수행되도록 preHandle() 메서드를 구현
   - WebMvcConfigurerPermalink
     - WebMvcConfigurer 인터페이스 구현함으로써 인터셉터 적용
       - logFilter()
         - 필터를 등록할 때 @Bean으로 등록
         - setFilter() -> 직접 구현한 클래스(MyFilter) 등록
         - setOrder() -> 필터 체인(chain) 순서 등록
         - addUrlPatterns() -> 필터가 수행될 URL을 정의
       - addInterceptors()
         - 애플리케이션 내에 인터셉터를 등록, addPathPatterns() -> 인터셉터를 호출하는 주소와 경로 추가


- 동시성 이슈에 대한 고민
  - 여러 프로세스 및 스레드가 동시에 동일한 데이터 (공유 데이터) 를 조작할 때 타이밍이나 접근 순서에 따라 예상했던 결과가 달라질 수 있는 상황을 의미.
  - 이 프로젝트에서도 여러 사람이 하나의 구장에 동시에 예약을 시도했을 때, 동시성 관련 이슈가 발생할 수 있다는 점을 알게됨

  - 동시성 이슈를 해결한 방법
    - Redis의 Redisson 라이브러리를 사용하여, lock 점유 방식을 설정하여 동시성 이슈를 해결


- 도커 (Docker)
  - 도커에 redis 띄우기
    - docker-compose.yml에 redis 설정 추가
      
      ![image](https://github.com/HUFSjlee/stadiumManager-backend/assets/67497759/2b425341-4788-44f3-8ad3-d4a980489551)
      
  - 도커 컨테이너에 등록
    
      ![image](https://github.com/HUFSjlee/stadiumManager-backend/assets/67497759/3586f848-7389-4613-8d72-7a83d66020bf)




- 테스트 코드 작성
  - stadium 테이블
    - stadium 테이블의 stadium_id 가 1인 구장의 ‘참여 가능한 최대 인원(maximumPersonnel)’은 18명으로 설정되어 있다. (reservable_stadium 테이블에는 stadium_id를 외래키(FK)로 가지고 있다.)
      ![image](https://github.com/HUFSjlee/stadiumManager-backend/assets/67497759/7d6edddc-8452-4e8b-b2d5-a3feb3696226)
      ![image](https://github.com/HUFSjlee/stadiumManager-backend/assets/67497759/9da34eb0-9e54-4871-9f5f-c42d82503482)

    - 테스트 진행 과정 및 이슈
       - 1. stadium_id가 1인 구장에 30명이 동시에 예약 신청을 시도한 상황이라고 가정하고 테스트를 진행해보려한다.
         2. 위 1번을 진행할 때, Lock을 걸었을 때와 Lock을 걸지 않았을 때의 결과값의 차이를 알아보고자 한다.
         3. 우선 현재 프로젝트의 reservationService 내에 있는 reserve 메서드에 Lock을 설정해둔 상태이고, Lock을 걸었을 때의 기댓값을(예측값)을 18로 예측했는데 엉뚱항 결과값이 나오는 문제가 발생
         4. Member 엔티티에서 JPA 쿼리 메서드를 만들어서 사용할 수도 있었지만 첫 테스트이기 때문에 stadium_id가 1인 구장에 예약이 가능한 조건을 충족하는 member를 테스트 코드 내에 하드코딩 해두었다.
 **(lock 획득 조건과 획득 시간, lease time을 수정 후, 정상 동작을 확인함)**
            ![image](https://github.com/HUFSjlee/stadiumManager-backend/assets/67497759/839018d2-abc3-40cc-be91-b0a7668023ce)


개인 블로그 링크 : https://hufsjlee.github.io/categories/project/


## 패키지 구조
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

## 프로젝트 목표
 - 매치 진행을 위해 이용 가능한 구장을 서비스에 등록하여 유저에게 제공합니다.
 - 구장별 매치 일정을 유저가 신청 및 취소할 수 있습니다.
 - 비즈니스 로직에 따라 예약 신청 성공/실패됩니다.
   
   - 유저들의 레벨이 존재합니다. 매치마다 참여 가능 레벨을 설정해 두어 실력별 매치가 가능합니다. (참여 가능 레벨을 초과하였을 때는 예약이 되지 않습니다.)
     
   - 유저들의 캐시가 없다면 예약이 불가능합니다. 캐시가 존재한다면 예약이 성공되며 캐시가 차감됩니다.
     
   - 남성, 여성, 혼성 매치가 있습니다. 성별에 맞지 않은 매치는 예약이 불가능합니다.
     
   - 구장별 이용가능한 인원수가 초과되면 예약이 불가능합니다.
     
 - 구장의 주소 검색
   - 카카오 로컬 API 사용으로 구장 주소를 제공합니다.
     
     
## 기능 소개
 - 구장 관리 API 개발
 - 유저 정보/매니저 정보/구장 정보 API 개발
 - 예약 신청 & 취소 & 변경 API 개발
 - 예약된 매치 목록 확인 API 개발
 - 예약 시 구장의 상세 주소 제공을 위한 카카오 로컬 API 적용



