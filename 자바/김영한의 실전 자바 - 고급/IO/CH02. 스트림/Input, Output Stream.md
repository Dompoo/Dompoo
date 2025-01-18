# Input/Output Stream

- 현대 컴퓨터는 대부분의 데이터를 `byte` 단위로 주고 받는다.
- 파일/네트워크/콘솔 등 여러 곳에서 데이터를 주고받기 위해 만든 인터페이스가 필요하다.
- 파일이든 네트워크든 콘솔이든 상관없이 동일한 방법으로 데이터를 주고받을 수 있는 일관된 방법이 필요한 것이다.
- 그것이 InputStream, OutputStream이다.
  - `FileInputStream`, `FileOutputStream`
  - `ByteArrayInputStream`, `ByteArrayOutputStream`
  - `SocketInputStream`, `SocketOutputStream`
  - 등의 구현체가 존재한다.

## 메모리에 IO

```java
// 메모리에 쓰기
byte[] output = {1, 2, 3};
ByteArrayOutputStream bos = new ByteArrayOutputStream();
bos.write(output);
		
// 메모리에서 읽기
ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
byte[] input = bis.readAllBytes();
System.out.println("Arrays.toString(input) = " + Arrays.toString(input));
```

- 컬렉션이나 배열을 사용하면 된다.
- 스트림을 테스트하는 용도 정도로 사용한다.

## 콘솔에 IO

```java
PrintStream printStream = System.out;
		
byte[] bytes = "Hello!".getBytes(StandardCharsets.UTF_8);
printStream.write(bytes);
```

- `println()`, `print()` 등의 메서드가 이 `write()` 메서드를 활용하는 것이다.
- `println()`에서는 `String`을 받을 수 있는데, `String`을 `byte[]`로 바꾸고 `write`를 호출하는 식이다.

## 정리

- IO Stream은 소스나 목적지와 상관없이 동일한 방식으로 코드를 작성할 수 있게 해준다.
- 또한 다양한 스트림 구현체를 통해 복잡한 IO 작업도 수행할 수 있다.
