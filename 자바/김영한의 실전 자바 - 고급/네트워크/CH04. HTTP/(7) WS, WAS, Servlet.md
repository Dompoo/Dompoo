# WS, WAS, Servlet

## Web Server, Web Application Server 란?

- 웹서버는 동적인 컨텐츠를 서빙하는 서버이다. (사진, 영상, html 등)
  - `Apache`, `Nginx` 등이 대표적이다.
  - 상대적으로 적은 리소스를 사용한다.
- 하지만 동적인 컨텐츠를 만들고 싶다면? 프로그램 코드를 실행하여 그 결과를 반영한 컨텐츠를 서빙하고 싶다면?
- 그런 경우에 사용하는 것이 WAS이다. (Application이 붙은 이유는 프로그램 코드를 실행하기 때문이다.)
  - 비즈니스 로직을 처리하고 데이터베이스와 연동될 수 있다.
  - `Tomcat`, `Jetty` 등이 대표적이다.
  - 상대적으로 많은 리소스를 사용한다.
- 실제로는 둘 다 사용한다.
  - 클라이언트 -> 웹서버 -> WAS -> DB 의 순서로 구성한다.
  - 웹서버는 빠르기 때문에 프록시의 역할을 수행하며 필요한 경우 WAS에게 요청을 위임한다.
    - 로드 밸런싱을 통해 다중 WAS를 운영할 수 있다.
    - WAS가 외부에 노출되지 않아 보안성이 강화된다.
    - 정적인 컨텐츠는 느린 WAS에게 위임하지 않고, 직접 처리한다.
  - WAS는 필요한 경우에 동적인 컨텐츠를 내려준다.
  - WAS가 장애를 겪더라도 웹서버를 통해 정적인 에러페이지를 주는 등의 기능으로 사용자 경험을 개선할 수 있다.

## WAS와 Servlet

- 그러면 이제 애플리케이션을 개발하고 **WAS**를 통해 동적인 컨텐츠를 반환할 수 있게 되었다.
- 하지만, 각 서버는 인터페이스가 다르고, 그래서 서버를 변경하면 기존의 코드를 모두 뜯어고쳐야 할 수도 있다.
- 이 인터페이스를 통일할 표준이 필요하다! -> **Servlet**

### Servlet

- HTTP 서버를 만드는 회사는 모두 이 서블릿을 기준으로 서버를 만든다.
- Tomcat, Jetty, Undertow 등의 서버들이 모두 이 서블릿을 지원한다.
- 따라서 개발자는 이 서블릿을 구현하여 WAS에 띄우기만 하면 된다.
