# Lock

- wait, notify의 문제점을 해결하기 위해서는 스레드 대기 집합을 둘로 나누어야 한다.
  - 소비자 스레드가 데이터를 소비하면 생산자 스레드 대기 집합에 알린다.
  - 생산자 스레드가 데이터를 생산하면 소비자 스레드 대기 집합에 알린다.
- **Lock**과 **ReentrantLock**으로 해결해보자.

## Lock, ReentrantLock 도입

```java
public class BoundedQueueV4 implements BoundedQueue {

  private final Lock lock = new ReentrantLock();
  private final Condition condition = lock.newCondition();
  private final Queue<String> queue = new ArrayDeque<>();
  private final int max;
  
  public BoundedQueueV4(int max) {
    this.max = max;
  }
  
  public void put(String data) {
    lock.lock();
    try {
      while (queue.size() == max) {
        log("[put] 큐가 가득 참, 생산자 대기");
        try {
          condition.await();
          log("[put] 생산자 깨어남");
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
      queue.offer(data);
      log("[put] 생산자 데이터 저장, signal() 호출");
      condition.signal();
    } finally {
      lock.unlock();
    }
  }
 
  public String take() {
    lock.lock();
    try {
      while (queue.isEmpty()) {
        log("[take] 큐에 데이터가 없음, 소비자 대기");
        try {
          condition.await();
          log("[take] 소비자 깨어남");
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
      String data = queue.poll();
      log("[take] 소비자 데이터 획득, signal() 호출");
      condition.signal();
      return data;
    } finally {
      lock.unlock();
    }
  }
}
```

- **Condition** : ReentrantLock을 사용하는 스레드가 대기하는 스레드 대기 공간
  - `lock.newCondtion()` 을 통해 만들 수 있다.
- `condition.await()` : 지정한 condition에 현재 스레드를 **WAITING** 상태로 보관한다.
- `condition.signal()` : 지정한 condition에서 대기중인 스레드 하나를 깨운다.
- 이제 **Condition**을 생산자 집합과 소비자 집합으로 나누어 관리하면 될 것 같다.
