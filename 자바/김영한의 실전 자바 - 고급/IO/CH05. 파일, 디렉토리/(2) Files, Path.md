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
