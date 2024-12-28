- 시스템과 관련된 기본 기능을 제공한다.
```java
// 현재 시간(밀리초)를 읽는다.
long mills = System.currentTimeMillis();

// 현재 시간(나노초)를 읽는다.
long currentTimeNanos = System.nanoTime();

// 환경 변수를 읽는다.
Map<String, String> env = System.getenv();

// 시스템 속성을 읽는다.
Properties properties = System.getProperties();
  
// 운영체제 레벨에서 배열 복사(고속)  
int[] original = new int[10];  
int[] target = new int[10];  
System.arraycopy(original, 0, target, 0, original.length);  

// 프로그램 종료 (리소스 정리도 하지 않고 종료하므로 비추천)
System.exit(0);
```