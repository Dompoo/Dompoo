# CorsFilter

- CorsConfigurationSource에 설정한 값에 따라 필터단에서 응답 헤더를 설정하는 필터이다.
- 기본으로 등록되는 필터이다.
- `CorsFilter`는 특별하게 스프링 이전에도 사용되었기 때문에 `GenericFilter`를 상속한다.
- 정말 자주 만나는 문제이다. 클라이언트와 서버가 출처가 거의 항상 다르기 때문에 CORS 설정 관련 문제를 자주 만난다.

## CORS 설정이란

- 아주 간단한 요청(현실에 없음)의 경우에는 바로 요청할 수 있다.
- Authorization 헤더를 포함하거나 컨텐트 타입이 application/json 이라면 **브라우저는 본 요청 전에 Preflight 요청을 보내게 된다.**
- CORS 설정을 한다는 것은 이 Preflight 요청이 들어왔을 때 어떻게 응답할 것인가에 대한 설정이다.
- 브라우저가 OPTIONS 요청을 전송하면 우리가 설정한 CORS 설정의 값으로 응답이 가고, 허용된 경우에만 브라우저가 요청을 전송한다.
- 이 CORS 기능은 브라우저의 기능이며, 서버-서버 사이 통신이나, curl 통신같은 경우에는 CORS가 작동하지 않는다.

## Spring Security를 타는 요청에 대해 CORS 설정

```java
public class DemoCorsConfig implements CorsConfigurationSource {
    
    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")); // 허용하는 메서드
        config.setAllowCredentials(true); // 자격 증명을 포함할 수 있는가? -> 포함된다면 Origin을 명시해주어야 한다.
        config.setAllowedHeaders(Collections.singletonList("*")); // 허용하는 헤더
        config.setMaxAge(3600L); // 브라우저가 Preflight 응답값을 캐싱해둘 시간
        return config;
    }
}

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
            .cors(cors -> cors.configurationSource(new DemoCorsConfig()))
            .build();
}
```

## Spring MVC 레벨에서의 CORS 설정

```java
@Configuration
public class DemoWebConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowCredentials(true)
                .allowedHeaders("*")
                .maxAge(3600);
    }
}
```
