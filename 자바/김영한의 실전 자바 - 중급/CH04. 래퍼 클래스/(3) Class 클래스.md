- 런타임에 클래스의 메타데이터를 다루는데 사용된다.
- 기능
	- 타입 정보 얻기 : 이름, 슈퍼클래스, 인터페이스, 접근 제한자 등의 정보를 조회할 수 있다.
	- 리플렉션 : 메서드, 필드, 생성자 등을 조회하여 객체 인스턴스를 생성하거나 메서드를 호출할 수 있다.
	- 동적 로딩과 생성 : 클래스를 동적으로 로딩하고, 새로운 인스턴스를 생성할 수 있다.
	- 애너테이션 처리 : 애너테이션을 조회하고 처리할 수 있다.
```java
Class<String> clazz = String.class;
clazz.getName(); // 클래스 이름
clazz.getSimpleName(); // 간단한 이름
clazz.getPackage().getName(); // 패키지
clazz.getSuperclass().getName(); // 부모클래스
Class<?>[] interfaces = clazz.getInterfaces(); // 인터페이스 정보
Method[] methods = clazz.getDeclaredMethods(); // 메서드 정보
Constructor<?>[] constructors = clazz.getDeclaredConstructors(); // 생성자 정보
clazz.isInterface(); // 인터페이스 여부
clazz.isEnum(); // 열거형 여부
clazz.isPrimitive(); // 기본형 여부
clazz.getDeclaredConstructor().newInstance() // 동적으로 인스턴스 생성
//...
```
