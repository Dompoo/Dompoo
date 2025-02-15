# HeaderWriterFilter

- HTTP 응답 헤더에 시큐리티 관련 헤더를 추가해준다.
  - 기본적으로 이 필터를 다시 통과하는(응답하는) 타이밍에 헤더를 추가해준다.
- 기본으로 등록되는 필터이다.
- 추가하는 헤더 종류
  - `X-Content-Type-Options` : 컨텐츠 스니핑을 막기 위해 nosniff value를 할당해 서버에서 응답하는 Content-Type과 다른 타입일 경우 읽지 못하도록 설정
  - `X-XSS-Protection` : XSS 공격 감지시 로딩 금지 (0은 비활성화)
  - `Cache-Control` : 이전에 받았던 데이터와 현재 보낼 데이터가 같다면 로딩에 대한 결정 여부
  - `Pragma` : HTTP/1.0 방식에서 사용하던 Cache-Control
  - `Expires` :서버에서 보낼 데이터를 브라우저에서 캐싱할 시간
  - `X-Frame-Options` :  브라우저가 응답 데이터를 iframe, frame, embed, object 태그에서 로딩해도 되는 여부
