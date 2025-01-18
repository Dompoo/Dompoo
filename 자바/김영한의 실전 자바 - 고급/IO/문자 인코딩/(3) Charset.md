# Charset

## 문자 집합 조회

```java
SortedMap<String, Charset> charsets = Charset.availableCharsets();
Charset defaultCharset = Charset.defaultCharset();
Charset forName = Charset.forName("UTF-8");
Charset standard = StandardCharsets.UTF_8;
```

- 자주 사용하는 문자 집합은 `StandardCharsets`에 상수로 지정되어 있다.
