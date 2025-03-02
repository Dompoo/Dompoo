# AuthorizationFilter

- 인증시 저장해둔 권한 정보를 바탕으로 인가 작업을 수행한다.
- `AuthorizationManager`에게 `Authentication`과 `ServletRequest`를 던져서 인가처리를 하도록 한다.
- 기본으로 등록되는 필터이다.

```java
http
    .authorizeHttpRequests((auth) -> auth
        .requestMatchers("/").permitAll()
        .anyRequest().permitAll());
```
