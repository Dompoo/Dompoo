# DefaultLoginPageGeneratingFilter

- 이름 그대로 기본 로그인 페이지를 제공한다. (GET /login)
- `UsernamePasswordAuthenticationFilter`과 함께 form 기반 로그인을 사용하지 않으려면 아래와 같이 설정하자.

```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
            .formLogin(AbstractHttpConfigurer::disable)
            .build();
}
```
