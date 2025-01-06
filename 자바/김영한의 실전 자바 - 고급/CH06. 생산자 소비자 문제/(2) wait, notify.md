# wait, notify

- **wait()**
  - 스레드가 현재 가진 **모니터 락을 반납**하고 **WAITING** 상태가 된다.
  - `synchronized` 블록 내부에서 락을 가지고 있을 때만 호출해야 한다.
- **notify()**
  - 대기중인 스레드 중 하나를 깨운다.
  - `synchronized` 블록 내부에서만 호출해야 한다.
- **notifyAll()**
  - 대기중인 스레드를 모두 깨운다.
  - `synchronized` 블록 내부에서만 호출해야 한다.

## 생산자 소비자 문제를 어떻게 해결할 수 있을까?

```java
public class BoundedQueue {
	
	private final Queue<String> queue = new ArrayDeque<>();
	private final int max;
	
	public BoundedQueue(int max) {
		this.max = max;
	}
	
	public synchronized void put(String data) {
		while (queue.size() == max) {
			try {
				wait(); // RUNNABLE -> WAITING, 락 반납
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		queue.offer(data);
		notify(); // 대기 스레드, WAIT -> BLOCKED
	}
	
	public synchronized String take() {
		while (queue.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		String data = queue.poll();
		notify(); // 대기 스레드, WAIT -> BLOCKED
		return data;
	}
}
```
