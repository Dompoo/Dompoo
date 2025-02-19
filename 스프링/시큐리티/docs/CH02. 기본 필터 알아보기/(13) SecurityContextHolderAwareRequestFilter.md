# SecurityContextHolderAwareRequestFilter

- ServletRequest 요청에서 `SecurityContextHolder`에 쉽게 접근할 수 있도록 돕는 필터이다.
- 기본으로 등록되는 필터이다.
- 아래 메서드에 추가적으로 접근할 수 있다.
  - authenticate() : 사용자가 인증 여부를 확인하는 메서드
  - login() : 사용자가 AuthenticationManager를 활용하여 인증을 진행하는 메서드
  - logout() : 사용자가 로그아웃 핸들러를 호출할 수 있는 메서드
  - AsyncContext.start() : Callable를 사용하여 비동기 처리를 진행할 때 SecurityContext를 복사하도록 설정하는 메서드
