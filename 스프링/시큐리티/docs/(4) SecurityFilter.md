# SecurityFilter

- `SecurityFilterChain`에 등록되는 각 필터는 각각 cors, csrf, 인증, 인가 등의 역할을 하나씩 맡게 된다.
- 커스텀 `SecurityFilterChain`을 만들게 되면 기본적으로 여러 필터를 제공해준다.

```text
// 기본적으로 제공되는 필터들
Security filter chain: [
  DisableEncodeUrlFilter
  WebAsyncManagerIntegrationFilter
  SecurityContextHolderFilter
  HeaderWriterFilter
  CsrfFilter
  LogoutFilter
  RequestCacheAwareFilter
  SecurityContextHolderAwareRequestFilter
  AnonymousAuthenticationFilter
  ExceptionTranslationFilter
  AuthorizationFilter
]
```

- 필요한 필터는 사용하고, 필요없는 것은 제거하는 등의 작업이 가능하다.

```java
public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
    log.info("필터체인 1 호출!");
    return http
            .securityMatchers((auth) -> auth.requestMatchers("/user"))
            .authorizeHttpRequests((auth) -> auth.requestMatchers("/user").permitAll())
            .cors((cors) -> cors.disable())
            .csrf((csrf) -> csrf.disable())
            .formLogin((login) -> login.disable())
            .addFilterAfter(/* 커스텀 필터 등록 */)
            .build();
}
```

## 기본으로 제공해주는 여러 필터들

- `DisableEncodeUrlFilter` : URL로 간주되지 않는 부분을 포함하지 않도록 설정
- `WebAsyncManagerIntegrationFilter` : 비동기로 처리되는 작업에 대해 알맞은 시큐리티 컨텍스트(세션)을 적용
- `SecurityContextHolderFilter` : 접근한 유저에 대해 **시큐리티 컨텍스트 관리**
- `HeaderWriterFilter` : 보안을 위한 응답 헤더 추가 (X-Frame, X-XSS-Protection, X-Content-Type Options)
- `CorsFilter` : **CORS** 설정 필터
- `CsrfFilter` : **CSRF** 방어 필터
- `UsernamePasswordAuthenticationFilter` : username/password 기반 로그인 처리 시작점 (POST /login)
- `DefaultLoginPageGeneratingFilter` : **기본 로그인 페이지** 생성 (GET /login)
- `LogoutFilter` : 로그아웃 요청 처리 시작점 (GET /logout)
- `DefaultLogoutPageGeneratingFilter` : **기본 로그아웃 페이지** 생성 (GET /logout)
- `BasicAuthenticationFilter` : http basic 기반 로그인 처리 시작점
- `RequestCacheAwareFilter` : 이전 요청 정보가 존재하면 처리 후 현재 요청 판단
- `SecurityContextHolderAwareRequestFilter` : ServletRequest에 서블릿 API 보안을 구현
- `AnonymousAuthenticationFilter` : 최초 접속으로 인증 정보가 없고, 인증을 하지 않았을 경우 세션에 **익명 사용자 설정**
- `ExceptionTranslationFilter` : 인증 및 접근에 대한 **예외 처리**
- `AuthorizationFilter` : 경로 및 권한별 **인가**

## 커스텀 필터

- 필터를 적절하게 구현하여 `addFilterAfter()`, `addFilterBefore()` 등을 통하여 등록하면 된다.
