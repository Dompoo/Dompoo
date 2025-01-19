# Files, Path

- `File` 클래스를 대체할 수 있는 객체들이다.
- `File` 클래스의 기능뿐만 아니라, 다른 유틸리티 기능들도 추가되었다.
- `FileWriter` 등과 같은 스트림보다 `Files`를 사용하는 것을 고려해보아야 한다.

```java
Path file = Path.of("temp/example.txt");
Path newFile = Path.of("temp/newExample.txt");
Path directory = Path.of("temp/exampleDir");
		
// 생성, 삭제
Files.createFile(file); // 이미 존재시 예외 발생
Files.createDirectory(directory); // 이미 존재시 예외 발생
Files.delete(file);
Files.move(file, newFile, StandardCopyOption.REPLACE_EXISTING);
		
// 정보 확인
System.out.println("Files.isRegularFile(file) = " + Files.isRegularFile(file));
System.out.println("Files.exists(file) = " + Files.exists(file));
System.out.println("Files.isDirectory(directory) = " + Files.isDirectory(directory));
System.out.println("file.getFileName() = " + file.getFileName());
System.out.println("Files.size(file) = " + Files.size(file));
System.out.println("Files.getLastModifiedTime(file) = " + Files.getLastModifiedTime(file));
BasicFileAttributes attrs = Files.readAttributes(file, BasicFileAttributes.class);
```

## 절대 경로, 정규 경로, 상대 경로

- `Path.of("temp/..")` 에서
- **절대 경로** : `Files` 객체 그 자체의 경로
  - `/Users/Dompoo/study/java/java-adv2/temp/..`
- **정규 경로** : 절대 경로에서 .. 등이 모두 계산된 경로
  - `/Users/Dompoo/study/java/java-adv2`
- **상대 경로** : 현재 위치 기준으로의 경로, 자바 프로젝트 기준이다.

## Files 통해서 읽고 쓰기

```java
String writeString = "abc\n가나다";
Path path = Path.of(PATH);
		
// 쓰기
Files.writeString(path, writeString, StandardCharsets.UTF_8);
		
// 읽기
String fullString = Files.readString(path, StandardCharsets.UTF_8);
List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
Stream<String> linesStream = Files.lines(path, StandardCharsets.UTF_8);
```

- `FileWriter`, `FileReader` 등을 사용할 필요 없이 매우 간단하게 읽고 쓸 수 있다.
- `readAllLines()`는 한번에 다 읽는데에 비해, `lines()`는 한줄씩 읽어들인다.
