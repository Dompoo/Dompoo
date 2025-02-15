# SecurityContextHolder

- 오키. 대충 구조는 알았다.
- 정리하자면
  - `DelegatingFilterProxy`에서 요청을 가로채서 `SecurityFilterChain`에 넘긴다.
  - `SecurityFilterChain`에 등록된 여러 `SecurityFilter`들을 통해 여러 보안 로직이 돌아간다.
- 문제는 **여러 필터**이다.
- 필터가 여러개라면 각 필터 사이에서 공유해야 하는 **보안 컨텍스트**를 어떻게 공유하겠는가?
- 예를 들어...
  - 앞선 필터에서 로그인 처리가 완료된다. USER이다. (인증)
  - 로그인 정보가 USER이기 때문에 /admin/** 페이지는 들어가지 못한다. (인가)
  - 어떻게 하면 USER라는 정보를 공유할 수 있지?
- 이렇기에 필요한 것이 `SecurityContextHolder`이다.
- 즉 `SecurityContextHolder`를 통해 **한 요청에 대하여, 여러 필터 간 정보 공유가 가능한 저장소이다.**

![SecurityContextHolder](SecurityContextHolder.png)

## SecurityContextHolder, SecurityContext, Authentication

- `SecurityContextHolder`는 **보안 컨텍스트**를 담는 전역적인 저장소이다. (싱글톤)
  - 기본적으로 `ThreadLocal`을 사용하여, 접근 스레드별로 다른 `SecurityContext`를 사용한다.
- `SecurityContext`는 이번 요청에 대한 실제 **보안 컨텍스트**를 저장한다.
- `Authentication`은 인증이 완료되었을 경우 여러 정보와 함께 `SecurityContext`에 저장된다.
  - Principal : 유저에 대한 정보
  - Credentials : 증명 (비밀번호, 토큰)
  - Authorities : 유저의 권한(ROLE) 목록
