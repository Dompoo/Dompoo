# WebAsyncManagerIntegrationFilter

- 스프링 MVC에서 `@Async`, `Callable`, `DeferredResult` 등을 사용하면 **요청 처리가 별도의 스레드에서 진행된다.**
- 하지만 `SecurityContext`는 `ThreadLocal`을 통해 스레드 단위로 생성된다.
- 따라서 비동기 작업의 경우에는 **여러 스레드가 한 `SecurityContext`를 접근할 필요**가 있는데, 이를 도와주는 필터이다.
- **기본으로 등록**되는 필터이다.

## 어떻게 작동하는가

- 요청이 들어올 때 `SecurityContext`를 `WebAsyncManager`에 저장한다.
- 비동기 요청이 들어올 때 저장되었던 `SecurityContext`를 다시 `SecurityContextHolder`에 끼운다.
- 비동기 요청이 종료되면 `SecurityContextHolder`를 정리하여 다른 요청을 기다린다.
- 두 요청이 동일한 클라이언트인지, 즉 동일한 `SecurityContext`를 사용해야 하는지는 세션이나 쿠키등의 인증정보를 통해 판단한다.
