- 각 자료구조는 데이터를 순회하는 방법이 모두 다르다.
	- 배열리스트 : index를 중가시키며 순회
	- 연결리스트 : 노드의 next를 통해 순회
	- 트리셋, 해시셋, ...
- 개발자가 이런 순회 방법을 모두 외우고 사용하는 것은 어렵다.
- 따라서 모든 자료구조를 같은 방법으로 순회하는 것이 필요하다.
- 그것이 `Iterable`, `Iterator` 인터페이스이다.
	- `Iterable` : `Iterator`를 반환하는 메서드를 제공한다.
	- `Iterator` : `hasNext()`, `next()` 메서드를 통해 순회하는 기능을 제공한다.
## 직접 구현해보자.
### Iterable을 구현한 자료구조
```java
import java.util.Iterator;  
  
public class MyArray implements Iterable<Integer> {  
      
    private final int[] numbers;  
      
    public MyArray(int[] numbers) {  
       this.numbers = numbers;  
    }  
      
    public int get(int index) {  
       return numbers[index];  
    }  
      
    @Override    
    public Iterator<Integer> iterator() {  
       return new MyArrayIterator(numbers);  
    }  
}
```
### Iterator를 구현한 반복자
```java
import java.util.Iterator;  
  
public class MyArrayIterator implements Iterator<Integer> {  
      
    private int currentIndex = 0;  
    private final int[] targetArr;  
      
    public MyArrayIterator(int[] targetArr) {  
       this.targetArr = targetArr;  
    }  
      
    @Override  
    public boolean hasNext() {  
       return currentIndex < targetArr.length;  
    }  
      
    @Override  
    public Integer next() {  
       return targetArr[currentIndex++];  
    }  
}
```
### 사용
```java
MyArray array = new MyArray(new int[]{1, 2, 3, 4});  
Iterator<Integer> iterator = array.iterator();  
while (iterator.hasNext()) {  
    System.out.println(">> " + iterator.next());  
}
```
## 향상된 for-each문
- `Iterable`을 구현한 경우 자바 수준에서 for-each문을 지원해준다.
```java
MyArray array = new MyArray(new int[]{1, 2, 3, 4});  
for (Integer number : array) {  
    System.out.println(">> " + number);  
}
```
- for-each문은 컴파일 시 `Iterator`를 통해서 순회하는 코드로 컴파일된다.