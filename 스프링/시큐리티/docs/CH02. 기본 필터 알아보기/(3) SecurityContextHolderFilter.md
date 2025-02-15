# SecurityContextHolderFilter

- 이전 요청에서 인증된 사용자의 정보를 현재 요청의 `SecurityContextHolder`에 저장한다.
- 또한 현재 요청이 끝나면 `SecurityContextHolder`를 비운다.
- 기본으로 등록되는 필터이다.

## 어떻게 동작하는가

- 먼저 `SecurityContextRepository`가 있다.
- 만약 이전 요청에서 인증을 하고, 상태가 STATELESS가 아니라면 해당 유저 정보가 어딘가에 있는데, 이를 `SecurityContextRepository.loadDefferdContext()`을 통해 불러올 수 있다.
  - 어떻게 불러오는지는 `SecurityContextRepository` 구현체에 위임한다.
- 이렇게 불러온 유저 정보를 `SecurityContextHolder.setDefferdContext()`를 통해 저장하고 다음 필터를 부른다.
- 요청이 끝나면 `SecurityContextHolder`에서 해당 정보를 지운다.

## SecurityContextRepository

- 인증 정보를 저장하는 역할을 한다.
- 서버 세션, 레디스 등을 통해 저장할 수 있다.
- 구현체
  - HttpSessionSecurityContextRepository : 서버 세션 기반 구현체
  - NullSecurityContextRepository : 아무 작업 X (**jwt를 통한 무상태 관리시**)
  - RequestAttributeSecurityContextRepository : HTTP Request 기반 구현체
  - 기타 : 직접 구현하자.
