- `ArrayList`는 사용하지 않는 배열의 공간이 낭비되고, 중간에 데이터 삽입이 어렵다는 단점이 있었다.
- 연결된 노드들을 통해 데이터를 나타내면 이런 문제를 해결할 수 있다.
![[Pasted image 20241225005723.png]]
- 값을 넣지 않으면 노드가 생기지 않으므로 메모리 효율적이다.
	- 사실 크게 효율적이지는 않다. 노드 하나하나가 훨씬 무겁기 때문이다.
- 중간 노드의 `next`만 수정하면 되므로 중간 삽입/삭제가 쉽다.
## 직접 구현해보자
```java
public class MyLinkedList {  
      
    private Node first;  
    private int size;  
      
    public void add(Object e) {  
       Node newNode = new Node(e);  
       if (first == null) {  
          first = newNode;  
       } else {  
          Node lastNode = getLastNode();  
          lastNode.next = newNode;  
       }  
       size++;  
    }  
      
    public void add(int index, Object e) {  
       Node newNode = new Node(e);  
       Node prevNode = getNode(index - 1);  
       newNode.next = prevNode.next;  
       prevNode.next = newNode;  
    }  
      
    private Node getLastNode() {  
       Node node = first;  
       while(node.next != null) {  
          node = node.next;  
       }  
       return node;  
    }  
      
    public Object get(int index) {  
       Node target = getNode(index);  
       return target.element;  
    }  
      
    public Object set(int index, Object e) {  
       Node target = getNode(index);  
       Object oldValue = target.element;  
       target.element = e;  
       return oldValue;  
    }  
      
    public Object remove(int index) {  
       Node prevNode = getNode(index - 1);  
       Object oldValue = prevNode.next.element;  
       prevNode.next = prevNode.next.next;  
       return oldValue;  
    }  
      
    private Node getNode(int index) {  
       Node node = first;  
       for (int i = 0; i < index; i++) {  
          node = node.next;  
       }  
       return node;  
    }  
      
    public int indexOf(Object e) {  
       int index = 0;  
       for (Node node = first; node != null; node = node.next) {  
          if (node.element.equals(e)) {  
             return index;  
          }  
          index++;  
       }  
       return -1;  
    }  
      
    public int size() {  
       return size;  
    }  
      
    @Override  
    public String toString() {  
       return first + ", size=" + size;  
    }  
}
```
- 성능은 어떨까?
	- 코드에서 보이는 것처럼, 조회 성능이 `O(n)`이다.
		- 해당 노드까지 가는데에 여러 노드들을 거치며 `next`로 넘어가야 하기 때문이다.
	- 데이터 추가도 마지막 노드를 찾는데 `O(n)`이 걸린다.
	- 데이터 삽입도 해당 위치를 찾는데 `O(n)`이 걸린다.
	- 데이터 수정도 해당 위치를 찾는데 `O(n)`이 걸린다.
	- 데이터 삭제도 해당 위치를 찾는데 `O(n)`이 걸린다.
	- 데이터 검색도 모든 노드를 순회하며 값을 찾는데 `O(n)`이 걸린다.
- 자바는 연결리스트의 가장 뒤 추가/삭제 연산의 속도를 높이기 위해 **이중 연결 리스트**를 사용한다.
![[스크린샷 2024-12-25 오후 4.10.19.png]]
- 배열리스트는 어떠한 자원에 대한 위치를 찾는 것은 빠르지만, 연산이 오래 걸린다.
- 연결리스트는 어떠한 자원에 대한 연산은 빠르지만, 위치를 찾는 것이 오래 걸린다.
## 제네릭을 도입해보자.
```java
import java.util.StringJoiner;  
  
public class Node<E> {  
      
    public E element;  
    public Node<E> next;  
      
    public Node(E element) {  
       this.element = element;  
    }  
      
    @Override  
    public String toString() {  
       StringJoiner sj = new StringJoiner(" -> ", "[", "]");  
       Node<E> x = this;  
       while (x != null) {  
          sj.add(String.valueOf(x.element));  
          x = x.next;  
       }  
       return sj.toString();  
    }  
}

public class MyLinkedList<E> {  
      
    private Node<E> first;  
    private int size;  
      
    public void add(E e) {  
       add(size, e);  
    }  
      
    public void add(int index, E e) {  
       Node<E> newNode = new Node<>(e);  
       if (index == 0) {  
          newNode.next = first;  
          first = newNode;  
       } else {  
          Node<E> prevNode = getNode(index - 1);  
          newNode.next = prevNode.next;  
          prevNode.next = newNode;  
       }  
       size++;  
    }  
      
    private Node<E> getLastNode() {  
       Node<E> node = first;  
       while(node.next != null) {  
          node = node.next;  
       }  
       return node;  
    }  
      
    public E get(int index) {  
       Node<E> target = getNode(index);  
       return target.element;  
    }  
      
    public E set(int index, E e) {  
       Node<E> target = getNode(index);  
       E oldValue = target.element;  
       target.element = e;  
       return oldValue;  
    }  
      
    public E remove(int index) {  
       Node<E> removeNode;  
       if (index == 0) {  
          removeNode = first;  
          first = first.next;  
       } else {  
          Node<E> prevNode = getNode(index - 1);  
          removeNode = prevNode.next;  
          prevNode.next = removeNode.next;  
       }  
         
       E removedElement = removeNode.element;  
       removeNode.element = null;  
       removeNode.next = null;  
         
       size--;  
       return removedElement;  
    }  
      
    private Node<E> getNode(int index) {  
       Node<E> node = first;  
       for (int i = 0; i < index; i++) {  
          node = node.next;  
       }  
       return node;  
    }  
      
    public int indexOf(E e) {  
       int index = 0;  
       for (Node<E> node = first; node != null; node = node.next) {  
          if (node.element.equals(e)) {  
             return index;  
          }  
          index++;  
       }  
       return -1;  
    }  
      
    public int size() {  
       return size;  
    }  
      
    @Override  
    public String toString() {  
       return first + ", size=" + size;  
    }  
}

```