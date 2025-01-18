# Charset

## 문자 집합 조회

```java
SortedMap<String, Charset> charsets = Charset.availableCharsets();
Charset defaultCharset = Charset.defaultCharset();
Charset forName = Charset.forName("UTF-8");
Charset standard = StandardCharsets.UTF_8;
```

- 자주 사용하는 문자 집합은 `StandardCharsets`에 상수로 지정되어 있다.

## 문자 인코딩

```java
byte[] bytes = text.getBytes(charset); // charset 비지정시 시스템 기본 문자 집합으로 인코딩
System.out.printf("%s를 [%s]로 인코딩 -> %s, %sbyte\n", text, charset, Arrays.toString(bytes), bytes.length);
```

- 문자를 바이트로 인코딩하기 위해서는 반드시 문자 집합이 필요하다.
  - 따로 지정하지 않을 시 기본 문자 집합으로 인코딩한다.

## 짧은 결론

- `UTF-16`은 아스키와 호환되지 않는다. (2바이트 표현 vs 1바이트 표현)
- `UTF-8`은 아스키와 호환되며, 표준처럼 사용된다.
  - 알파벳, 숫자는 1바이트
  - 한글은 3바이트
