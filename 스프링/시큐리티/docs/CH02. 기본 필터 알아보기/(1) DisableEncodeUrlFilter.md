# DisableEncodeUrlFilter

- 세션을 사용할 때, 보통 `sessionid`를 쿠키에 넣어서 반환해준다.
- 근데 만약 쿠키를 지원하지 않는 클라이언트는 세션 정보를 어디에 넣어서 반환해줘야 할까?
- 보통 이럴 경우에는 URL에 세션 정보를 인코딩하여 저장한다.
  - `HttpServletResponse.encodeURL()`, `encodeRedirectURL()` 등을 통해 세션 정보가 URL에 인코딩된다.

## URL에 세션 정보를 넣는 것이 무엇이 문제일까?

- 이렇게 공유된 URL을 누군가 읽는다면 세션을 탈취할 수 있다.
- 세션이 탈취되면 **세션고정공격**등 보안에 취약해진다.
- 따라서 호환성을 조금 낮추더라도 URL에 세션 정보를 포함하지 않고 싶을 수 있다!

## DisableEncodeUrlFilter의 역할

- `DisableEncodeUrlFilter`은 `HttpServletResponse.encodeURL()`, `encodeRedirectURL()`등이 호출되더라도 세션 정보가 URL에 인코딩되는 것을 막는다.
- 이를 통해 **URL을 통한 세션 탈취를 방지**할 수 있다.
- 보안상 필요하기 때문에 기본적으로 포함되는 필터이다.
