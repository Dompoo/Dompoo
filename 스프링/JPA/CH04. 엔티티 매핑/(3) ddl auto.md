# ddl auto

- JPA에는 데이터베이스 스키마 자동 생성 기능이 있다.

## ddl-auto의 옵션

- create : DROP + CREATE
- create-drop : DROP + CREATE + 종료시 DROP
- update : 테이블과 엔티티를 비교하여 변경 사항만 UPDATE
- validate : 테이블과 엔티티를 비교하여 차이가 있으면 경고를 남기고 종료
- none : 기능 사용 X (사실 그냥 유효하지 않은 값이다. 해당 설정을 삭제해도 된다.)

## ddl-auto 유의 사항

- create, create-drop, update 처럼 테이블을 수정할 수 있는 기능은 개발 단계에서만 사용하자.
