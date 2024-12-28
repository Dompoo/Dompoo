## Stack
- 후입선출(`LIFO`) 자료구조이다.
- `Stack`도 있긴 한데, `Stack`이 `extends`하는 `Vector`가 권장되지 않는 클래스이다.
- 따라서 대신에 `Deque`를 사용하는 것이 권장된다.
## Queue
- 선입선출(`FIFO`) 자료구조이다.
- `Queue` 인터페이스 구현체는 `ArrayDeque`, `LinkedList`등이 있다.
## Deque
- Double End Queue의 약자이다.
- `offerFirst(), offerLast(), pollFirst(), pollLast()`등을 통해 양쪽에서 넣고 뺄 수 있다.
	- Stack처럼 쓰기 위한 `push(), pop()` 메서드도 제공한다.
		- 단, peek은 `peekFirst()`를 쓰면 된다.
	- Queue처럼 쓰기 위한 `offer(), poll()` 메서드도 제공한다.
		- 단, peek은 `peekLast()`를 쓰면 된다.
- 구현체로 `ArrayDeque`, `LinkedList`가 있다. (원형 큐 구조를 사용하는`ArrayDeque`가 빠르다.)
- Stack, Queue 쓰지 말고 그냥 이거 사용하면 된다.