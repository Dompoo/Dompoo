# DefaultLogoutPageGeneratingFilter

- 기본 로그아웃 페이지를 제공한다. (GET /logout)
- 아래 설정으로 제거한다.

```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
            .formLogin(AbstractHttpConfigurer::disable)
            .build();
}
```
