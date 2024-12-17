- String은 불변이다.
- 따라서 제공하는 모든 메서드들은 반환값을 통해 사용해야 한다.
```java
String str = "hello";
str.concat(" java"); // X, str은 바뀌지 않는다.
String newStr = str.concat(" java"); // O
```
- String이 불변인 이유
	- 불변 객체를 통해서 얻을 수 있는 장점들 때문에 (일관성)
	- 문자열 풀을 통해 여러 다른 곳에서 같은 값이 참조되기 때문에, 어느 한 곳에서 이를 수정하는 일을 막기 위하여