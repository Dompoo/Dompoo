# BasicAuthenticationFilter

- **Form 인증 방식**은 form 태그에 username/password 입력해서 전송했었다.
- **Basic 인증 방식**은 Authorization 헤더에 username/password를 BASE64 인코딩하여 전송하면 된다. (브라우저가 방법을 제공한다.)
  - 서버는 인증 완료 후 해당 정보를 세션에 저장한다.
