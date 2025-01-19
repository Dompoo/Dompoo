# Reader, Writer

- byte로 데이터를 다루기 위해서는 기존 클래스를 사용하면 된다.
  - `InputStream`, `OutputStream`과 그 구현체들
- 문자로 데이터를 다루기 위해서는 `Reader`, `Writer`를 사용하면 된다.
  - 단, 문자로 데이터를 다룬다고 해서 실제로 쓰고 읽는 값이 문자인 것은 아니다.
  - **쓰고 읽는 실제 값은 항상 byte이다. 자바 내에서 데이터를 다루는 방식이 문자일 뿐이다.**
- `Reader`, `Writer`는 `Stream`을 다루는 방법을 편하게 해줄 뿐, 실제 동작은 `Stream`이 한다.

## FileReader, FileWriter

```java
String writeString = "ABC";
		
// 파일에 쓰기
FileWriter fw = new FileWriter(FILE_NAME, StandardCharsets.UTF_8);
fw.write(writeString);
fw.close();
		
// 파일에서 읽기
FileReader fr = new FileReader(FILE_NAME, StandardCharsets.UTF_8);
int ch;
StringBuilder buffer = new StringBuilder();
while ((ch = fr.read()) != -1) {
	buffer.append((char) ch);
}
fr.close();
		
System.out.println(buffer);
```

- `FileOutputStream`, `FileInputStream`을 더 편하게 사용할 수 있도록 하는 `FileWriter`, `FileReader`도 존재한다.
  - 생성자에서 그냥 `FileOutputStream`, `FileInputStream`을 생성해서 가지고 있는다.

## BufferedReader, BufferedWriter

- BufferedWriter는 버퍼를 통해 쓴다.
- **BufferedReader는 한줄 단위로 읽는 기능이 추가된다.**

```java
String writeString = "ABC\n가나다";
		
// 파일에 쓰기
FileWriter fw = new FileWriter(FILE_NAME, StandardCharsets.UTF_8);
BufferedWriter bw = new BufferedWriter(fw, BUFFER_SIZE);
bw.write(writeString);
bw.close();
		
// 파일에서 읽기
FileReader fr = new FileReader(FILE_NAME, StandardCharsets.UTF_8);
BufferedReader br = new BufferedReader(fr, BUFFER_SIZE);
		
StringBuilder content = new StringBuilder();
String line;
while ((line = br.readLine()) != null) {
	content.append(line).append("\n");
}
br.close();
		
System.out.println(content);
```
