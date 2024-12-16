- Priority Queue는 내부에서 정렬을 하는 큐이다.
- [[_코테를 위한 알고리즘|알고리즘]]문제에 자주 사용된다.
- 즉, offer()를 하고 poll()할 때 정렬된 순서로 반환한다.

```java
/* 기본으로 정렬하고 싶은 경우 */
PriorityQueue<Integer> pq = new PriorityQueue<>();
pq.offer(3);
pq.offer(5);
pq.offer(1);
pq.poll() // 1
pq.poll() // 3
pq.poll() // 5

/* 역으로 정렬하고 싶은 경우 */
PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
pq.offer(3);
pq.offer(5);
pq.offer(1);
pq.poll() // 5
pq.poll() // 3
pq.poll() // 1

/* 커스텀으로 정렬하고 싶은 경우 */
class Node implements Comparable<Node> {
	int number;
	int cost;

	// ...

	@Override
	public int compareTo(Node o) {
		return this.cost - o.cost;
	}
}
PriorityQueue<Node> pq = new PriorityQueue<>();
pq.offer(new Node(1, 3));
pq.offer(new Node(2, 4));
pq.offer(new NodE(3, 1));
pq.poll() // 3, 1
pq.poll() // 1, 3
pq.poll() // 2, 4
```