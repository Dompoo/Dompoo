# LogoutFilter

- 로그아웃시 실행되는 필터이다.
- 기본으로 등록된다.
- 로그아웃하여 필터가 실행되면 등록된 `LogoutHandler`들이 작동한다.
- 기본으로 제공되는 `LogoutHandler`들은 다음과 같다.
  - `SecurityContextLogoutHandler` : SecurityContextHolder에 존재하는 SecurityContext 초기화, **기본 등록**
  - `CookieClearingLogoutHandler` : SecurityFilterChain의 logout 메소드에서 지정한 쿠키 삭제
  - `HeaderWriterLogoutHandler` : 클라이언트에게 반환될 헤더 조작
  - `LogoutSuccessEventPublishingLogoutHandler` : 로그아웃 성공 후 특정 이벤트 실행, **기본등록**
- 더 많은 로그아웃 조작을 위해서는 `LogoutHandler`를 구현하고 이를 등록한다.
- 또한 로그아웃 성공시 조작을 위해서는 `LogoutSuccessHandler`를 구현하고 이를 등록한다.

## LogoutHandler 등록

```java

@Slf4j
public class DemoLogoutHandler implements LogoutHandler {
    
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("데모 LogoutHandler 호출");
    }
}

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
            .logout(logout -> logout.addLogoutHandler(new DemoLogoutHandler()))
            .build();
}
```

## LogoutSuccessHandler 등록

```java
@Slf4j
public class DemoLogoutSuccessHandler implements LogoutSuccessHandler {
    
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("로그아웃 성공!");
    }
}

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
            .logout(logout -> logout.addLogoutHandler(new DemoLogoutHandler())
                    .logoutSuccessHandler(new DemoLogoutSuccessHandler()))
            .build();
}
```
