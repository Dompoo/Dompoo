# CAS 락

- **CAS**없이 락을 구현하려고 하면, 연산이 2번 필요하다.
  - `while` 문을 계속 돌면서 락이 반납되었는지 확인
  - 반납되었다면 락을 다시 true로 만들고 while문 탈출한 후에 로직 수행
- 하지만 락이 위 2개의 연산으로 나누어져 있기 때문에, 임계 영역이 안전하게 보호되지 못한다.
  - 두 스레드에서 동시에 락이 반납되었다고 인지하고 임계 영역에 동시에 들어갈 수 있다.
- **CAS** 연산을 통해서 두 연산을 하나의 원자적 연산으로 처리할 수 있다.

## CAS를 이용한 스핀 대기 락

```java
import java.util.concurrent.atomic.AtomicBoolean;

public class SpinLock {
	
	private final AtomicBoolean lock = new AtomicBoolean(false);
	
	public void lock() {
		while(!lock.compareAndSet(false, true)) {
			//스핀대기
		}
	}
	
	public void unLock() {
		lock.set(false);
	}
}

// 사용
public static void main(String[] args) {
  SpinLock spinLock = new SpinLock();

  spinLock.lock();
  try {
    // 임계 영역
  } finally {
    spinLock.unLock();
  }
}
```

- **CAS**를 활용한 덕분에 동기화 과정없이 매우 가벼운 락을 만들 수 있다.
  - 그리고 사실상 락이 없다. 개념적인 락이며, 계속 반복하며 확인할 뿐이다.
  - 따라서 대기하는 스레드도 **RUNNABLE**을 유지하며, 가볍고 빠르게 동작한다.
  - **BLOCKED**, **WAITING**이 되지 않기 때문에 Context Switching 비용도 발생하지 않는다.
- 하지만 **CAS**를 활용한 이 방법은 오래 걸리는 로직을 실행하기 어렵다.
  - **while문 안에 무거운 동작이 들어가면 스핀대기가 매우 많이 걸린다.**
  - CPU를 많이 사용하며 대기하게 된다. (busy waiting)
- **따라서 이 방식은 안전한 임계영역이 필요하긴 하나, 연산이 매우매우 짧은 경우에만 사용해야 한다.**
  - 락을 사용하여 발생하는 Context Switching 비용 VS 락 획득을 체크하는 반복적인 스핀 대기 비용

## 락 vs CAS 락 프리

### CAS의 장단점

- **낙관적 동기화** : 락을 걸지 않고도 값을 안전하게 업데이트할 수 있다. 자주 충돌이 일어나지 않을 것이라 가정한다.
- **락 프리** : 락을 사용하지 않기 때문에 락 획득을 위한 대기 시간이 없다. 스레드가 블로킹되지 않으며 병렬처리가 더 효율적이다.
- **오버헤드** : 스핀락이기 때문에 충돌이 빈번한 경우 성능이 저하된다.

### 락의 장단점

- **충돌 관리** : 명확하게 하나의 스레드만 리소스에 접근하는 것이 보장된다.
- **스레드 대기** : 락을 획득하기 위해 대기하는 스레드는 CPU를 사용하지 않는다.
- **대기 시간** : 스레드가 락을 획득하기 위한 대기 시간이 걸린다.
- **컨텍스트 스위칭 비용** : 락을 획득하기 위해 대기할 때, 락을 획득할 때 상태가 바뀌며, Context Switching 비용이 발생한다.
