## Comparable
- 어떠한 객체가 **비교될 수 있다.** 라는 뜻이며 `Arrays.sort()`, `Collections.sort()` 메서드가 이 구현을 통하여 정렬한다.
- 이렇게 구현된 정렬이 **자연 정렬 순서**이다.
```java
public class MyClass implements Comparable<MyClass> {  
      
    private final int id;  
    private final String name;  
      
    public MyClass(int id, String name) {  
       this.id = id;  
       this.name = name;  
    }  
      
    @Override  
    public int compareTo(MyClass o) {  
       return this.id - o.id;  
    }  
}
```
-  값을 정렬할 때 비교자(`Comparator`)가 없다면 위 방식으로 정렬된다.
## Comparator
 - **자연 정렬 순서**가 아닌, 다른 방법으로 정렬하고 싶을 때 사용한다.
	 - 자바에서 제공하는 객체를 정렬할 때 필요한 것이 **비교자**이다.
	 - 비교자를 제공할 경우 자연 정렬 순서보다 우선시된다.
	 - `Comparator`를 구현한 비교자를 통해서 다른 비교기준을 제공할 수 있다.
	 - 제공하지 않을 경우 `Comparable`이 사용된다.
```java
Integer[] arr = {1, 3, 2, 7, 5};  

// Comparable 자연 정렬 순서
Arrays.sort(arr); // 1 2 3 5 7

// Comparator 익명 클래스 구현
Arrays.sort(arr, new Comparator<Integer>() {  
    @Override  
    public int compare(Integer o1, Integer o2) {  
       return o2 - o1;  
    }  
}); // 7 5 3 2 1

// Comparator 람다식 구현
Arrays.sort(arr, (o1, o2) -> o2 - o1); // 7 5 3 2 1
```
- `reversed()`를 통해서 반대로 정렬할 수도 있다.
## 주의
### 구현하지 않는 경우
- 두 인터페이스를 모두 제공하지 않은 객체를 정렬하려고 하면 예외가 발생한다.
- `Comparator`가 없으니 **자연 정렬 순서**를 사용해야 하는데, 그것이 없으니 비교할 기준이 없다는 것이다.
- 따라서 어떠한 객체를 만들 때, 정렬이 필요하다면 `Comparable`을 기본적으로 구현하는 것이 권장된다.
### TreeMap, TreeSet 등
- 이 자료구조는 저장할 때부터 정렬하여 저장하기 때문에 `Comparable` 또는 `Comparator`가 필수이다.
- 해당 컬렉션 객체를 생성할 때 `Comparator`를 제공한다면 해당 기준으로, 제공하지 않는다면 자연 정렬 순서로 정렬된다.
### Enum
- 모든 열거형 객체는 Enum 클래스를 상속받는다.
- Enum 클래스 내부에는 `final`인 `Comparable`이 구현되어 있다. (`ordinal` 기준 정렬)
- 따라서 기본 `ordinal` 기준 정렬을 사용하던가, `Comparator`를 제공하여 정렬해야 한다. (`Comparable` 오버라이딩 불가)