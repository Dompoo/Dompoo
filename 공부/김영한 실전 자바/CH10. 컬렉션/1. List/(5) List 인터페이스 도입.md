- `ArrayList`와 `LinkedList`는 제공하는 기능이 똑같다. 이런 경우에는 추상화를 하기 좋다.
- 또한 `List`라는 기능을 수행하기 위해 다른 성능의 구현체를 상황에 따라 바꿔끼는 것을 통해 OCP를 지킬 수 있다.
```java
public interface MyList<E> {  
      
    int size();  
      
    void add(E e);  
      
    void add(int index, E e);  
      
    E get(int index);  
      
    E set(int index, E e);  
      
    E remove();  
      
    E remove(int index);  
      
    int indexOf(E e);  
}
```
- 실제 자바에서는 아래와 같이 상속관계가 표현된다.
![[스크린샷 2024-12-25 오후 4.46.42.png]]
## 언제 무엇을 사용해야 할까?
- 현대에는 `ArrayList`가 `LinkedList`에 비해서 대부분 좋은 성능을 보여준다.
- 앞쪽에 데이터를 추가/삭제하는 일이 매우매우 잦은 환경에서만 `LinkedList`를 고려하면 된다.