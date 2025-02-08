# JPQL

- ANSI 표준 SQL과 비슷하다.
- `em.createQuery()`를 통해 날릴 수 있다.
- 또는 Data JPA의 `@Query`에서도 사용한다.

```SQL
SELECT m 
FROM Member m 
WHERE m.username = :username;

SELECT t.name, COUNT(m)
FROM Member m
JOIN m.team t
GROUP BY t.name
HAVING COUNT(m) > 5;

SELECT m 
FROM Member m 
ORDER BY m.age DESC;

SELECT m 
FROM Member m 
WHERE m.age > (
    SELECT AVG(m2.age) FROM Member m2
);
```

## 간단 문법

- 별칭은 필수다.
- 필요한 경우 `:파라미터명`으로 파라미터 마인딩이 가능하다.
- 나머지는 ANSI 표준 SQL과 비슷하며, 필요한 경우 찾아서 쓰자.
