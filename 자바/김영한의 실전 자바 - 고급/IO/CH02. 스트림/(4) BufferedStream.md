# BufferedStream

- 버퍼를 직접 만들고 사용하기는 어려우므로, BufferedOutput/InputStream을 사용하는 것이 권장된다.

```java
// 쓰기
FileOutputStream fos = new FileOutputStream(FILE_NAME);
BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER_SIZE);
		
for (int i = 0; i < FILE_SIZE; i++) {
	bos.write(1);
}
		
bos.close();

// 읽기
FileInputStream fis = new FileInputStream(FILE_NAME);
BufferedInputStream bis = new BufferedInputStream(fis, BUFFER_SIZE);
		
int fileSize = 0;
int size;
while ((size = bis.read()) != -1) {
	fileSize += size;
}
		
bis.close();
```

- Buffered~Stream은 생성자에서 대상 Stream을 받는다.
  - 버퍼의 크기를 명시하지 않으면 8KB로 잡는다.
- Buffered~~Stream은 I/OStream을 구현하면서 I/OStream을 받는다는 점에서 프록시패턴이다. (젠장 또 프록시패턴이야)
- Buffered~Stream은 단독으로 사용할 수 없고, 보조하는 역할만 하는 **보조 스트림**이다.
- Buffered~Stream은 동기화 기능이 추가적으로 달려있으므로, 전에 직접 버퍼를 다뤘던 것보다 느리다.

## 동작

- BufferedStream은 내부적으로 `byte[] buf`를 가지고 있는다.
- `write()`가 호출될때마다 내부 버퍼에 값을 넣어놓는다.
- 버퍼가 모두 차면 내부 Stream에 해당 버퍼 내용을 한번에 전달해준다.
- 버퍼가 다 차지 않아도 있는 내용을 전달하고 싶을 수 있다. 이때는 `flush()`를 호출하면 된다.
- `close()`를 호출하면 남은 내용이 마저 flush되고 닫힌다.
- **마지막으로 연결한 BufferedStream을 닫아주면 연쇄적으로 다른 스트림도 닫힌다.**
