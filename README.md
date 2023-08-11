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

### 프로젝트 진행 중 이슈 (왜 이렇게 했으며, 무엇을 해결하려고 했는지?)

### JPA N+1

이게 무엇이며, 코드 어떤 부분에서 이슈가 있었는가?(어떤 조회API를 개발할 때 발생했었는데 어떻게 해결하려고 노력하였는지.) (Q. 이 방법으로는 해결할 생각은 안해봤나? / Q. 다른 방법을 시도할 생각은 해보았는가?)

- ‘fetchType = Eager’ → ‘fetchType = Lazy’로 변경했습니다.
- FetchJoin을 활용하여 문제를 개선했습니다.

### RESPONSE

- 공통 응답 코드 BaseResponse 클래스에서 관리
- 일관된 응답 형식을 위한 응답 스펙 클래스 정의
- 클래스 내 필드는 code(응답 코드) / msg(응답 메세지) / result(응답 결과) / timestamp(응답 시간)를 포함

### EXCEPTION

- @ExceptionHandler 적용
    - 각 클래스에서 발생하는 예외를 처리할 수 있는 Exception Handler 사용
- @RestControllerAdvice
    - GlobalExceptionHandler 클래스를 만들고, 클래스 내 적절한 예외를 처리하기 위해 만든 Custom Exception을 공통으로 처리

### JPA_N:M 관계 문제

- 관계형 데이터베이스의 사용으로 N:M관계로 만들어두었던 테이블을 해결해야 했습니다.
- 문제 해결을 위해 중간 테이블을 Entity로 만들고 @OneToMany, @ManyToOne 어노테이션을 활용하여 1:N 과 N:1 의 관계로 풀어냈습니다.

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

### 카카오 로컬(Local) API 적용

- 외부 API를 사용해 구장의 위치 정보(주소)를 검색할 수 있습니다.
- Kakao developers 에서 제공하는 요청과 응답을 양식에 맞게 작성하고, 응답으로 필요한 필드는 dto클래스로 만들었습니다.

### Retrofit

- 카카오 로컬 API를 사용할 때, 서버와 클라이언트 간 HTTP 통신을 위해 Retrofit 라이브러리를 사용했습니다.
- 서버와 클라이언트의 통신을 위한 라이브러리 사용은 처음이었는데, Okhttp를 라이브러리 사용으로 더 편하고 빠른 장점이 있어서 채택하게 되었습니다.
- RetrofitAPI 인터페이스 생성
    - 사용할 메소드를 정의하고 Server API에 맞는 @Header 정보를 작성
- kakaoLocalApi() 메소드 정의
    - Retrofit 인스턴스 생성
        - 카카오의 baseUrl 등록
        - .addConverterFactory(GsonConverterFactory.create) → JSON을 변환해줄 Gson 변환기 등록
- @GET (Http Get 요청)
    - EndPoint 작성
- 반환 타입
    - call<객체타입>



## ERD 
https://github.com/stadiumManager/stadium-backend/wiki/ERD



