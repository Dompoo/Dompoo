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

## 문자 디코딩

```java
public static void main(String[] args) {
	test("A", US_ASCII, US_ASCII);
	test("A", US_ASCII, ISO_8859_1);
	test("A", US_ASCII, UTF_16BE);
	test("A", US_ASCII, UTF_8);
	
	test("가", US_ASCII, US_ASCII);
	test("가", ISO_8859_1, ISO_8859_1);
	test("가", UTF_16BE, UTF_16BE);
	test("가", UTF_8, UTF_8);
}
	
private static void test(String text, Charset encodingSet, Charset decodingSet) {
	byte[] encoded = text.getBytes(encodingSet);
	String decoded = new String(encoded, decodingSet);
	System.out.printf("%s -> [%s] 인코딩 : %s / [%s] 디코딩 : %s\n", text, encodingSet.displayName(), Arrays.toString(encoded), decodingSet.displayName(), decoded);
}

/* 결과
A -> [US-ASCII] 인코딩 : [65] / [US-ASCII] 디코딩 : A
A -> [US-ASCII] 인코딩 : [65] / [ISO-8859-1] 디코딩 : A
A -> [US-ASCII] 인코딩 : [65] / [UTF-16BE] 디코딩 : �
A -> [US-ASCII] 인코딩 : [65] / [UTF-8] 디코딩 : A
가 -> [US-ASCII] 인코딩 : [63] / [US-ASCII] 디코딩 : ?
가 -> [ISO-8859-1] 인코딩 : [63] / [ISO-8859-1] 디코딩 : ?
가 -> [UTF-16BE] 인코딩 : [-84, 0] / [UTF-16BE] 디코딩 : 가
가 -> [UTF-8] 인코딩 : [-22, -80, -128] / [UTF-8] 디코딩 : 가
*/
```

## 결론

- `UTF-16`은 아스키와 호환되지 않는다. (2바이트 표현 vs 1바이트 표현)
- `UTF-8`은 아스키와 호환되며, 표준처럼 사용된다.
  - 알파벳, 숫자는 1바이트
  - 한글은 3바이트
- 한글의 경우는 좀 더 복잡하다. `EUC-KR` -> `MS-949` 처럼 한글이 확장되는 특수 케이스를 제외하고는 다른 문자 집합과 호환되지 않는다.
  - `EUC-KR` 인코딩, `MS-949` 디코딩의 경우 인코딩만 되면 호환된다.
  - `MS-949` 인코딩, `EUC-KR` 디코딩의 경우 자주 사용하지 않는 글자는 깨진다.
  - 모든 한글을 포현할 수 있는 `UTF-8` 과 `MS-949`도 서로 호환되지 않는다.
