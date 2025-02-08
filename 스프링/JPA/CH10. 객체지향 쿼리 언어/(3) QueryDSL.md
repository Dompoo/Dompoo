# QueryDSL

- JPQL 빌더 역할을 한다.
- 동적으로 여러 메서드가 호출되지만 결국 결과물은 JPQL이며, `EntityManager`를 통해 쿼리를 날리게 된다.

## 설정 방법

- 먼저 필요한 의존성을 추가해준다.

```groovy
implementation 'com.querydsl:querydsl-jpa:5.0.0:jakarta'
annotationProcessor "com.querydsl:querydsl-apt:${dependencyManagement.importedProperties['querydsl.version']}:jakarta"
```

- `querydsl-jpa` : QueryDSL JPA 라이브러리
- `querydsl-apt` : 쿼리 타입(Q) 생성에 필요한 라이브러리
- 그 다음에는 `JPAQueryFactory`를 생성해준다.

```java
public class HelloRepository {

    private JPAQueryFactory queryFactory;

    public HelloRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    // ... 사용
}
```

- 생성한 `JPAQueryFactory`를 통해 쿼리하면 된다.
