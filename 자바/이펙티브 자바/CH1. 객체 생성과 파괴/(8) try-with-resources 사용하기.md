# try-with-resources 사용하기

- 사용한 자원을 close 해주어야 하는 경우가 있다.
- 하지만 이를 잊기 쉬우며, 중첩되면 코드가 복잡해지는데, 자바7부터 try-with-resources를 지원한다.
- AutoCloseable 인터페이스를 구현하여 `close()` 메서드를 구현하자.
- 이 방법을 사용하면 코드가 더 짧고 분명해지며, 만들어지는 예외 정보도 유용해진다.

```java
public static void copy(String src, String dst) {
    try (InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst)) {
        // ...
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
```
