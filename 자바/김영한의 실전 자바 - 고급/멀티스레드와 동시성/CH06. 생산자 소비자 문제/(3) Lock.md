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

## 대기 공간 분리

```java
public class BoundedQueueV4 implements BoundedQueue {

  private final Lock lock = new ReentrantLock();
  private final Condition producerCondition = lock.newCondition();
  private final Condition consumerCondition = lock.newCondition();
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
          producerCondition.await();
          log("[put] 생산자 깨어남");
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
      queue.offer(data);
      log("[put] 생산자 데이터 저장, signal() 호출");
      consumerCondition.signal();
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
          consumerCondition.await();
          log("[take] 소비자 깨어남");
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
      String data = queue.poll();
      log("[take] 소비자 데이터 획득, signal() 호출");
      producerCondition.signal();
      return data;
    } finally {
      lock.unlock();
    }
  }
}
```

- 두 대기 공간이 분리되었기 때문에 이제 비효율 문제가 완전히 제거된다.
  - 생산자는 무조건 소비자를 깨운다.
  - 소비자는 무조건 생산자를 깨운다.

## Object.notify() vs Conditino.signal()

- 깨우는 순서
  - notify()는 따로 정의되어 있지 않다. (하지만 보통 오래 기다리면 잘 깨어나도록 구현된다.)
  - signal()은 일반적으로 FIFO 순서로 깨운다.
- 사용처
  - notify()는 synchronized 블록 내에서 모니터락을 가지고 있는 스레드가 호출한다.
  - signal()은 ReentrantLock을 가지고 있는 스레드가 호출한다.
