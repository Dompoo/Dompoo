# SecurityFilterChain

- 스프링 시큐리티 의존성 추가시 `DefaultSecurityFilterChain`가 등록된다.
- 내가 원하는 필터체인을 등록하기 위해서는 커스텀을 해주면 된다.

```java
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/img/**");
    }
    
    @Bean
    @Order(1) // 등록되는 순서 지정
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
        log.info("필터체인 1 호출!");
        return http
                .securityMatchers((auth) -> auth.requestMatchers("/user"))
                .authorizeHttpRequests((auth) -> auth.requestMatchers("/user").permitAll())
                .build();
    }
    
    @Bean
    @Order(2)
    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
        log.info("필터체인 2 호출!");
        return http
                .securityMatchers((auth) -> auth.requestMatchers("/admin"))
                .authorizeHttpRequests((auth) -> auth.requestMatchers("/admin").permitAll())
                .build();
    }
}
```

- 여러 필터체인을 등록할 경우에는 `securityMatchers()`를 통해 매칭을 꼭 지정해주어야 한다.
- `@Order`를 통해 등록되는 순서를 지정할 수 있다.
- 정적 리소스등 무시하도록 설정하고 싶은 url은 `WebSecurityCustomizer` 빈을 반환하여 설정할 수 있다.
