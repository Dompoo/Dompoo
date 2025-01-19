# File

- `File` 클래스를 통해 파일 또는 디렉토리를 다룰 수 있다.
- 지금은 자주 사용하지 않는 방법이다.

```java
File file = new File("temp/example.txt");
File newFile = new File("temp/newExample.txt");
File directory = new File("temp/exampleDir");
		
// 생성, 삭제
boolean fileCreated = file.createNewFile();
System.out.println("fileCreated = " + fileCreated);
		
boolean dirCreated = directory.mkdir();
System.out.println("dirCreated = " + dirCreated);
		
boolean fileDeleted = file.delete();
System.out.println("fileDeleted = " + fileDeleted);
		
boolean fileRenamed = file.renameTo(newFile);
System.out.println("fileRenamed = " + fileRenamed);
		
// 정보 확인
System.out.println("file.exists() = " + file.exists());
System.out.println("file.isFile() = " + file.isFile());
System.out.println("directory.isDirectory() = " + directory.isDirectory());
System.out.println("file.getName() = " + file.getName());
System.out.println("file.length() = " + file.length());
System.out.println("new Date(file.lastModified()) = " + new Date(file.lastModified()));
```
