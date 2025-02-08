# 스프링 데이터 JPA 소개

- JPA를 더 간편하게 사용하도록 도와주는 프로젝트이다.
- **인터페이스만 작성하면 실행 시점에 구현체를 작성해서 주입해준다.**
- 따로 메서드를 작성하지 않아도 웬만한 기능은 구현이 되어있다.

## 인터페이스

- 실제로 사용하게 되는 것은 **JpaRepositry**이다.
- 이 인터페이스는 Repository -> CrudRepository -> PagingAndSortingRepository -> JpaRepository 순으로 extends 된다.
