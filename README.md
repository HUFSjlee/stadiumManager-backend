**<div align="center"> :two_men_holding_hands:Stadium Manager!(풋살&축구 소셜 매치 서비스):soccer: </div>**
**<div align="center"> [Backend Server]</div>**
2023.04 ~

## :clipboard: 개요   

### 기획배경  
 - 풋살&축구 매치를 진행하기 위해서는 인원 모집과 장비를 챙겨야 하는 번거로움 있습니다.
 - 위와 같은 번거로움 없이 개인장비만 챙겨 매치에 참가할 수 있는 서비스를 제공하고자 시작하게 되었습니다.
 - 해당 서비스에 등록되어 있는 매치를 예약할 수 있는 REST API 서버를 개발하였습니다.  

### 프로젝트 목표
 - 서비스에 제휴 구장을 등록하고 구장의 매치 일정을 유저가 직접 확인하고 신청할 수 있습니다.
 - 신청한 매치를 취소할 수 있습니다.
 - 유저들의 레벨 포인트가 존재하고 매치마다 참여 가능 레벨 포인트를 두어 실력별 매치가 가능합니다.

### 기능 소개
 - 구장 관리 API 개발
 - 유저 정보/매니저 정보/구장 정보 API 개발
 - 예약 신청 & 취소 &변경 API 개발
 - 예약된 매치 목록 확인 API 개발
 - 예약시 구장의 상세 주소 확인을 위한 카카오 로컬 API 적용

### 기술 스택
 - Back-End : Java11, Spring Boot 2.7.11, MVC, Retrofit
 - ORM : Spring Data JPA
 - Database : MySQL8.0, Docker
 - REST API Documentation : Swagger, Asciidoc
 - Tools : IntelliJ, Git

### 패키지 구조

- 도메인형 구조를 선택
  - 어떤 도메인들이 사용되고 있는지를 한눈에 파악할 수 있다는 점이 장점이라 생각되어 선택
  - 도메인 패키지 아래에 [Presentation-Domain-Infra 형식의 LayeredArchitecture](https://martinfowler.com/bliki/PresentationDomainDataLayering.html) 적용

### 서비스 구조
![프로젝트_아키텍쳐](https://github.com/HUFSjlee/stadiumManager-backend/assets/67497759/460cd781-69fc-4c5c-a4a7-a74691ce5781)

### 프로젝트 진행 중 이슈

### JPA N+1 문제
- ‘fetchType = Eager’ → ‘fetchType = Lazy’로 변경
- FetchJoin을 활용하여 문제를 개선

### RESPONSE 관리

- 공통 응답 코드 BaseResponse 클래스에서 관리
- 일관된 응답 형식을 위한 응답 스펙 클래스 정의
- 클래스 내 필드는 code(응답 코드) / msg(응답 메세지) / result(응답 결과) / timestamp(응답 시간)를 포함

### EXCEPTION 공통 처리

- @ExceptionHandler 적용
    - 각 클래스에서 발생하는 예외를 처리할 수 있는 Exception Handler 사용
- @RestControllerAdvice
    - GlobalExceptionHandler 클래스를 만들고, 클래스 내 적절한 예외를 처리하기 위해 만든 Custom Exception을 공통으로 처리

### JPA_N:M 관계 문제

- 관계형 데이터베이스의 사용으로 N:M관계로 만들어두었던 테이블을 해결해야 했습니다.
- 문제 해결을 위해 중간 테이블을 Entity로 만들고 @OneToMany, @ManyToOne 어노테이션을 활용하여 1:N 과 N:1 의 관계로 풀어냈습니다.

### 카카오 로컬(Local) API 적용

- 외부 API를 사용해 구장의 위치 정보(주소)를 검색할 수 있습니다.
- Kakao developers 에서 제공하는 요청과 응답을 양식에 맞게 작성하고, 응답으로 필요한 필드는 dto클래스로 만들었습니다.

### 외부 API 사용 시, 의존성 문제
- 외부 API와 관련 된 필드를 가지고 있는 ConversionCoordinatesSystem 과 같은 class는 어떤 패키지에 위치해야 하는걸까?
- 고민 끝에, domain 패키지에 넣기로 결정
- 이유는 infrastructure에 위치하게 되면 domain -> infrastructure 형식의 의존성 방향을 나타내는데, 이렇게 되면 infrastructure가 DB통신, 외부 통신등의 세부 구현 기술이 변경될 때 domain 패키지도 변경이 일어나기 때문 

### Filter와 Interceptor

### Filter

- 서블릿 실행 전, 후에 요청과 응답에 대한 필터링을 위해 적용하였습니다.
- Filter 동작과정
    - Filter는 초기화 → 필터링 → 소멸의 단계를 거칩니다.
    - Filter 인터페이스를 구현할 클래스(MyFilter)생성한 후 메소드 구현
        - init()
        - doFilter() : 서비스의 request와 response에 대한 필터링 로직 작성
        - destroy()
- @Component
    - Filter를 적용하기 위해 스프링 빈으로 등록하여 사용했습니다.

### Interceptor

- 컨트롤러의 메소드에 접근하는 과정에서 권한 관련 로직으로 제어하기 위해 사용했습니다.
- HandlerInterceptor
    - HandlerInterceptor 인터페이스를 구현할 클래스를 생성
    - preHandle() 정의
        - 컨트롤러 접근 전 수행되도록 preHandle() 사용했습니다.
        
         
        
- WebMvcConfigurer 인터페이스 구현으로 필터와 인터셉터 적용
    - logFilter()
        - 필터 등록할 때 @Bean으로 등록해줍니다.
        - setFilter() → 직접 구현한 클래스(MyFilter) 등록
        - setOrder() → 필터 체인 순서 등록
        - addUrlPatterns() → 필터가 수행될 URL 정의
    - addInterceptors()
        - 애플리케이션 내 인터셉터 등록
            - addPathPatterns() → 인터셉터를 호출하는 주소와 경로 추가

### JPA 쿼리 메소드

- 멤버(A)가 구장(B)를 예약 했다고 가정했을 때, 멤버(A)가 구장(B)을 중복으로 예약하는 것을 방지해야합니다.
- ‘`existsByMemberIdAndReservableStadiumId`’ 와 같은 쿼리메소드를 만들어서 처리했습니다.

### 첫 Retrofit 사용 (webflux와 webclient)

- 카카오 로컬 API를 사용할 때, 서버와 클라이언트 간 HTTP 통신을 위해 Retrofit 라이브러리를 사용했습니다.
- 서버와 클라이언트의 통신을 위한 라이브러리 사용은 처음이었는데, Okhttp를 라이브러리 사용으로 인터페이스를 통해 더 편하고 간결한 장점이 있어서 채택하게 되었습니다.

### 만약 서비스에서 카카오 로컬 API가 동작하지 않는 이슈가 발생한다면?
- 이에 대해 궁금증을 가지고 알아보던 중, Fallback에 대해서 알게 되었습니다.
- 행안부 등 다른 외부 API를 추가하고 카카오 로컬 API가 동작하지 않을 때 행안부 API로 대체될 수 있도록 로직을 작성해야 할 것으로 보입니다.




## ERD 
https://github.com/stadiumManager/stadium-backend/wiki/ERD



