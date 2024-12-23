- 배열의 경우 길이를 동적으로 바꿀 수 없다.
- 또한 데이터를 처음, 중간에 추가하기 어려웠다.
- 배열의 이런 불편함을 해소하고 동적으로 데이터를 추가할 수 있는 자료구조가 `ArrayList`이다.
- `List`는
	- 순서가 있다.
	- 중복을 허용한다.
- 이런 `List`중에서 `Array(배열)`을 통해 구현한 리스트라고 해서 `ArrayList`라고 불린다.
## 직접 구현해보자
```java
import java.util.Arrays;  
  
public class MyArrayList {  
      
    private static final int DEFAULT_CAPACITY = 10;  
      
    private Object[] elements;  
    private int size = 0;  
      
    public MyArrayList() {  
       this(DEFAULT_CAPACITY);  
    }  
      
    public MyArrayList(int capacity) {  
       elements = new Object[capacity];  
    }  
      
    public int size() {  
       return size;  
    }  
      
    public void add(Object e) {  
       elements[size++] = e;  
    }  
      
    public Object get(int index) {  
       return elements[index];  
    }  
      
    public Object set(int index, Object e) {  
       Object oldElement = get(index);  
       elements[index] = e;  
       return oldElement;  
    }  
      
    public int indexOf(Object e) {  
       for (int i = 0; i < size; i++) {  
          if (elements[i].equals(e)) {  
             return i;  
          }  
       }  
       return -1;  
    }  
      
    @Override  
    public String toString() {  
       return Arrays.toString(Arrays.copyOf(elements, size))  
             + ", size=" + size + ", capacity=" + elements.length;  
    }  
}
```
- 하지만 위 코드에는 문제가 있다.
	- 최대 용량에 도달하면 `ArrayIndexOutOfBound` 예외가 터진다.
- 따라서 용량을 동적으로 확장하기 위해 아래와 같이 `add` 메서드를 수정할 수 있다.
```java
public void add(Object e) {  
    if (size == elements.length) {  
       growDouble();  
    }  
    elements[size++] = e;  
}  
  
private void growDouble() {  
    elements = Arrays.copyOf(elements, elements.length * 2);  
}
```
- 이렇게 용량이 부족할 때마다 용량이 확장된 새 배열을 참조하도록 하여 해결한다.
- 이 경우 기존 배열은 GC의 대상이 된다.
## 기능을 추가해보자
- `add(index, e)`를 통해 원하는 위치에 값을 추가할 수 있다.
- `remove(index)`를 통해 원하는 위치의 값을 삭제할 수 있다.
```java
public void add(int index, Object e) {  
    if (size == elements.length) {  
       growDouble();  
    }  
    shiftRightFrom(index);  
    elements[index] = e;  
}  
  
private void shiftRightFrom(int index) {  
    for (int i = size; i > index; i--) {  
       elements[i] = elements[i - 1];  
    }  
}

// ... 다른 기능들

public Object remove(int index) {  
    Object oldElement = get(index);  
    shiftLeftFrom(index);  
    elements[--size] = null;  
    return oldElement;  
}  
  
private void shiftLeftFrom(int index) {  
    for (int i = index; i < size - 1; i++) {  
       elements[i] = elements[i + 1];  
    }  
}
```
- `add`나 `remove` 메서드의 `shift`기능 때문에 배열리스트는 보통 순서대로 삽입하고 순서대로 삭제할 때 가장 효율적이다.
## 제네릭을 적용해보자
```java
import java.util.Arrays;  
  
public class MyArrayList<E> {  
      
    private static final int DEFAULT_CAPACITY = 10;  
      
    private Object[] elements;  
    private int size = 0;  
      
    public MyArrayList() {  
       this(DEFAULT_CAPACITY);  
    }  
      
    public MyArrayList(int capacity) {  
       elements = new Object[capacity];  
    }  
      
    public int size() {  
       return size;  
    }  
      
    public void add(E e) {  
       add(size, e);  
    }  
      
    public void add(int index, E e) {  
       if (size == elements.length) {  
          growDouble();  
       }  
       shiftRightFrom(index);  
       elements[index] = e;  
    }  
      
    private void shiftRightFrom(int index) {  
       for (int i = size; i > index; i--) {  
          elements[i] = elements[i - 1];  
       }  
    }  
      
    private void growDouble() {  
       elements = Arrays.copyOf(elements, elements.length * 2);  
    }  
      
    @SuppressWarnings("unchecked")  
    public E get(int index) {  
       return (E) elements[index];  
    }  
      
    public E set(int index, E e) {  
       E oldElement = get(index);  
       elements[index] = e;  
       return oldElement;  
    }  
      
    public E remove() {  
       return remove(size - 1);  
    }  
      
    public E remove(int index) {  
       E oldElement = get(index);  
       shiftLeftFrom(index);  
       elements[--size] = null;  
       return oldElement;  
    }  
      
    private void shiftLeftFrom(int index) {  
       for (int i = index; i < size - 1; i++) {  
          elements[i] = elements[i + 1];  
       }  
    }  
      
    public int indexOf(E e) {  
       for (int i = 0; i < size; i++) {  
          if (elements[i].equals(e)) {  
             return i;  
          }  
       }  
       return -1;  
    }  
      
    @Override  
    public String toString() {  
       return Arrays.toString(Arrays.copyOf(elements, size))  
             + ", size=" + size + ", capacity=" + elements.length;  
    }  
}
```
- 내가 만든 `ArrayList`의 단점
	- 정확한 크기를 알지 못하면 뒷부분의 메모리가 낭비된다.
	- 데이터를 중간에 추가/삭제할 때 성능이 좋지 않다.