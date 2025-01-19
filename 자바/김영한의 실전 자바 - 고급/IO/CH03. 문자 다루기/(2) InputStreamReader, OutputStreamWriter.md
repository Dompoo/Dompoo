# InputStreamReader, OutputStreamReader

- 각각 스트림이 byte가 아니라 문자를 읽을 수 있도록 도와주는 객체이다.

```java
String writeString = "ABC";
		
// 파일에 쓰기
OutputStreamWriter osw = new OutputStreamWriter(
		new FileOutputStream(FILE_NAME),
		StandardCharsets.UTF_8
);
osw.write(writeString);
osw.close();
		
// 파일에서 읽기
InputStreamReader isr = new InputStreamReader(
		new FileInputStream(FILE_NAME),
		StandardCharsets.UTF_8
);
int ch;
StringBuilder buffer = new StringBuilder();
while ((ch = isr.read()) != -1) {
	buffer.append((char) ch);
}
isr.close();
		
System.out.println(buffer);
```

- `OutputStreamWriter` : 전달한 문자열을 `byte[]`로 변환하여 쓰기 작업을 한다.
- `InputStreamReader` : 읽은 `byte`를 하나씩 int로 변환하여 반환한다.
  - `char`형인데, 실제 반환 타입은 `int`이기 때문에 변환할 필요가 있다.
  - 이는 파일의 끝을 표현하는데에 char형이 제약이 있기 때문이다.
  - `int`형으로는 -1로 끝을 표현할 수 있다.
