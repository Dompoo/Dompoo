- 본론부터 말하면 String은 거의 모든 경우에 `equals()`로 비교하는 것이 좋다.
	- 자바는 최적화를 위해 생성된 문자열을 문자열 풀에 저장한다.
	- 따라서 `String str = "Hello"`를 두번 해도, 같은 Hello 주소를 참조하므로 **동일 / 동등**하다.
	- 하지만, `String str = new String("Hello")`를 하면 문자열풀에서 꺼내지 않고, 새로운 문자열을 만든다. 따라서 이 경우에는 **동일**하지 않고, **동등**만 하다.
	- 이런 비일관성과, 문자열은 보통 내부 값으로만 비교하는 것이 옳기 때문에 `==`은 사용하지 않고, `eqauls()`로만 비교하는 것이 좋다.
```java
// new String 비교
String str1 = new String("a");
String str2 = new String("a");
str1 == str2; // false
str1.eqauls(str2); // true

// 리터럴 비교
String str3 = "a";
String str4 = "a";
str3 == str4; // true
str3.equals(str4); // true
```
- 연장선으로, String은 거의 모든 상황에서 리터럴로 생성하는 것이 좋다.