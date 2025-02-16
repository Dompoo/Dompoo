# UsernamePasswordAuthenticationFilter

- username/password 기반 form 로그인을 지원하는 필터이다. (기본으로 추가되는 필터는 아니다.)
- `AbstractAuthenticationProcessingFilter`를 상속받는데, `attemptAuthentication()` 메서드만 구현하면 된다.
  - 인증은 크게 3가지 단계로 나뉜다.
    - **클라이언트의 요청으로부터 로그인 정보 추출하기**
    - 해당 로그인 정보를 검증하기
    - 검증 결과를 핸들하기
  - 마지막 2개의 단계는 `AbstractAuthenticationProcessingFilter`에서 이미 구현이 되어 있다.
  - 우리는 해당 추상클래스를 상속하여 어떻게 정보를 추출할지만 구현하면 된다.
- username/password 기반 form 로그인을 쓰고 싶다면 이미 구현된 구현체 `UsernamePasswordAuthenticationFilter`를 쓰면 된다.
- JWT 기반 로그인을 쓰고 싶다면 새로 만들어야 한다.

```java
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtProvider jwtProvider;

    public JwtAuthenticationFilter(
        RequestMatcher requiresAuthenticationRequestMatcher,
        AuthenticationManager authenticationManager,
        JwtProvider jwtProvider
    ) {
        super(requiresAuthenticationRequestMatcher, authenticationManager);
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request, 
        HttpServletResponse response
    ) throws AuthenticationException {
        try {
            Map<String, String> credentials = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            String token = credentials.get("token");

            if (token == null || !jwtProvider.validateToken(token)) {
                throw new RuntimeException("Invalid JWT Token");
            }

            String username = jwtProvider.getUsernameFromToken(token);
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, null);

            return getAuthenticationManager().authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse authentication request", e);
        }
    }
}
```

- 결론적으로 `AuthenticationManager`에게 인증을 위임하면서 해당 메서드가 종료된다.
- 그렇다면 `AuthenticationManager`부터 인증이 어떻게 수행되는걸까?

## AuthenticationManager, AuthenticationProvider

- `AuthenticationManager`는 전달된 `Authentication` 객체를 인증하려고 한다.
- 내부에 등록된 여러개의 `AuthenticationProvider`를 순차적으로 호출한다.
- `AuthenticationProvider`는 해당 토큰 유형이 자신이 처리할 수 있는 유형인지 알 수 있다.
- 처리할 수 있는 `AuthenticationProvider`를 만난다면 해당 `AuthenticationProvider`에게 인증을 위임한다. (여러 방법이 있을 수 있다. DB와 대조 / JWT 토큰 검사 등)
- `AuthenticationProvider`가 인증을 성공한다면, 인증이 완료되었다고 표시된 `Authentication` 객체를 반환한다.

### 참고

- `AuthenticationManager`의 대표적인 구현체는 `ProviderManager`이다.
- 사실 위에 설명한 수행 절차가 `ProviderManager`의 수행절차이다.
- `AuthenticationProvider`의 대표적인 구현체는 `DaoAuthenticationProvider`이다.
  - 이것은 DB등과 상호작용하여 인증을 수행한다.
