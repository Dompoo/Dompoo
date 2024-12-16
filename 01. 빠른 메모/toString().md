- Object의 기본 메서드이다.
	- Object 내부에서는 `getClass().getName() + "@" + Integer.toHexString(hashCode());` 형식으로 생겼다.
	- 따라서 결과는 `java.lang.Object@a093mdc` 같이 생겼다.
- `System.out.println()` 과 같은 메서드는 내부에서 `toString()`을 호출한다. (오버로딩)
	- 따라서 `System.out.println(object)` 와 `System.out.println(object.toString())`은 같은 결과를 반환한다.
- 객체의 상태를 내가 원하는 방식으로 적절하게 나타내고 싶다면 `toString()`을 적절하게 오버라이딩해야 한다.
```java
public String toString() {
	return "Dog{" + name + " : " + age + "}";
}
```
- 오버라이딩을 했을 경우 그 부모타입의 메서드를 호출해도, 자식에게 오버라이딩이 있는지 확인하고, 있다면 그것을 출력한다.
```java
void run() {
	Dog dog = new Dog("멍멍이", 10);
	System.out.println((Object) dog); // 결과 : Dog{멍멍이 : 10}
}
```
- `toString`은 다음과 같은 상황에서 오버라이딩하면 좋다.
	- 컬렉션의 요소로 사용될 수 있을 때(컬렉션은 `toString()`시 내부 객체의 `toString()`을 호출함)
	- 내부 값이 많아서 디버깅시 값을 표현할 필요가 있을 때