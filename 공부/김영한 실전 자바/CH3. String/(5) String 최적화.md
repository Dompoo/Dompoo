- 문자열 리터럴 최적화
	- `"Hello" + " World"` 와 같이 리터럴이 더해지는 문자열을 컴파일하며 최적화한다.
	- `"Hello" + " World"` -> `"Hello World"`
- String 변수 최적화
	- `String str = str1 + str2`와 같은 변수가 더해지는 문자열을 컴파일하며 최적화한다.
	- `String str = new StringBuilder().append(str1).append(str2).toString()`
	- 최적화 내용은 버전에 따라 다르다. 아무튼 최적화된다는 것이 중요하다.
- 따라서 우리는 편하게 + 연산을 통해 더하면 된다.
# 최적화가 안되는 경우 (중요)
- 반복문을 통해 문자열을 더하는 경우
```java
String str = "";
for(int i = 0; i < time; i++) {
	str += str1;
}
```
컴파일러는 컴파일 시점에 반복문이 몇번 돌지 결정할 수 없기 때문에 최적화가 잘 되지 않는다.
따라서 이런 경우에는 가변문자열을 통해 다음과 같이 코드를 작성해야 한다.
```java
StringBuilder sb = new StringBuilder();
for(int i = 0; i < time; i++) {
	sb.append(str1);
}
String str = sb.toString();
```
이것 외에도 실행이 동적으로 결정되는 것들은 모두 최적화가 되지 않는다.
- 조건문
- 복잡한 문자열의 특정 부분을 변경할 때
- ...
