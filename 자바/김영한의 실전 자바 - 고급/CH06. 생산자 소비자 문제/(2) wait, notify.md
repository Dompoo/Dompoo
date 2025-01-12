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

- 데이터 추가시
  - 만약 데이터를 더 추가할 수 없으면 대기한다.
    - 대기시 모니터락 반납
  - 소비자 스레드가 데이터를 소비하면 생산자 스레드를 깨운다.
    - 깨우는 스레드가 생산자 스레드인지는 보장할 수 없다.
    - 바로 깨우지 못하더라도, 조금 돌아서 깨울 수 있다.
- 데이터 소비시
  - 만약 데이터를 소비할 수 없으면 기다린다. (대기시 모니터락 반납)
  - 데이터를 추가하면 스레드를 깨운다.
- 스레드를 깨운다고 바로 **RUNNABLE**이 되는 것은 아니다.
  - 소비 스레드를 깨웠지만, 아직 `notify()`를 호출한 스레드가 임계영역에서 나오지 않았다.
  - 따라서 **WAITING** 상태에서 곧바로 **BLOCKED** 상태가 된다.
  - 이후에 `notify()`를 호출한 스레드가 임계영역을 탈출하면 **RUNNABLE**이 된다.

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

## 문제점

### 비효율성

- `notify()`는 깨울 스레드를 선택할 수 없다.
  - 소비자가 소비자를 notify하면 곧바로 다시 wait할 수밖에 없다.
- **즉, 같은 종류의 스레드를 깨울 때 비효율이 발생한다.**
- 스레드 대기 집합을 나누어 서로를 깨울 수 있도록 할 수는 없을까?

### 스레드기아

- `notify()`는 깨울 스레드를 선택할 수 없다.
- 어떤 한 스레드가 운이 없어서 영원히 깨어나지 못하는 경우도 존재한다.
- 이 문제는 `notifyAll()`을 사용하면 막을 수 있다.
  - 일단 대기 집합의 모든 스레드가 깨어나기 때문에 락을 획득할 기회가 반드시 온다.
