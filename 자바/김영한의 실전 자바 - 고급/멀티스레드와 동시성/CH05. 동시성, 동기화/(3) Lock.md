# Lock, ReentrantLock

- **LockSupport**를 통해서 `synchronized`의 무한 대기를 해결했다.
- 하지만, 이를 잘 사용하기는 어렵다. `park()`와 `unpark()`만으로는 적절하게 락을 걸고, 끝날 때 락을 반납하여 다른 스레드를 깨우는 등의 코드를 작성하기 어렵다.
- 그래서 자바는 `Lock` 인터페이스와 `ReentrantLock` 이라는 구현체를 통해 이를 해결한다.
- 참고로 **ReentrantLock** 구현체를 통해 얻는 락은 모니터락과 **BLOCKED** 상태와는 관련없다.
  - 구현체 내부에서 소프트웨어적으로 구현된 락이다.

## 기능

- `void lock()`
  - 락을 획득한다.
  - 다른 스레드가 락을 가지고 있다면 **WAITING** 상태가 된다.
  - **인터럽트에 응답하지 않는다.**
  - 이는 **ReentrantLock** 내부적으로 인터럽트가 걸려서 **RUNNABLE**이 되어도, 다시 순간적으로 **WAITING** 상태로 강제 전환 시키기 때문에 가능한 일이다.
- `void lockInterruptibly()`
  - `lock()`과 비슷하다.
  - 단, **인터럽트에 응답한다.**
- `boolean tryLock()`
  - 락 획득을 시도하고, 획득했다면 true, 실패했다면(다른 스레드가 락 소유) false를 반환한다.
  - 위 두 메서드는 언젠가 무조건 락을 획득하는데에 비해, 이 메서드는 실패할 경우 바로 포기한다.
- `boolean tryLock(long time, TimeUnit unit)`
  - 주어진 시간동안 락 획득을 시도한다.
  - 주어진 시간이 지나도 락을 획득하지 못하면 false를 반환한다.
  - 중간에 인터럽트가 걸리면 예외가 발생하며 락 획득을 포기한다.
- `void unlock()`
  - 락을 해제한다.
  - 락 획득을 대기 중인 스레드들 중 하나가 획득하게 된다.
  - 만약 락을 획득한 스레드가 이를 호출하지 않으면, 예외가 발생한다.
- `Condition newCondition()`
  - **Condition**은 락과 결합되어 사용된다.
  - 스레드가 특정 조건을 기다리거나 신호를 받도록 한다.

## 공정성

- **ReentrantLock**는 공정 모드와 비공정 모드를 선택할 수 있다.

```java
// 비공정 모드 락
private final Lock nonFairLock = new ReentrantLock();
// 공정 모드 락
private final Lock fairLock = new ReentrantLock(true);
```

- 비공정 모드
  - 락을 풀었을 때, 대기중인 스레드중 무작위로 락을 획득한다.
  - 락을 획득하는 속도가 빠르다.
  - 특정 스레드가 장기간 락을 획득하지 못할 수 있다.
- 공정 모드
  - 락을 풀었을 때, 대기 큐에서 먼저 대기한 스레드가 락을 획득한다.
  - 락을 획득하는 속도가 약간 느리다.

## 활용

```java
public class BankAccount {
  private int balance;
  private final Lock lock = new ReentrantLock();
  
  public BankAccountV4(int initialBalance) {
    this.balance = initialBalance;
  }

  public boolean withdraw(int amount) {
    log("거래 시작: " + getClass().getSimpleName());
    lock.lock(); // ReentrantLock 이용하여 lock을 걸기
    try {
      log("[검증 시작] 출금액: " + amount + ", 잔액: " + balance);
      if (balance < amount) {
        log("[검증 실패] 출금액: " + amount + ", 잔액: " + balance);
        return false;
      }
      log("[검증 완료] 출금액: " + amount + ", 잔액: " + balance);
      sleep(1000);
      balance = balance - amount;
      log("[출금 완료] 출금액: " + amount + ", 변경 잔액: " + balance);
    } finally {
      lock.unlock(); // ReentrantLock 이용하여 lock 해제
    }
    log("거래 종료");
    return true;
  }

  public int getBalance() {
    lock.lock(); // ReentrantLock 이용하여 lock 걸기
    try {
      return balance;
    } finally {
      lock.unlock(); // ReentrantLock 이용하여 lock 해제
    }
  }
}
```

## 대기 중단

- **ReentrantLock**을 통해 무한정 대기하지 않는 락 기능을 사용할 수 있다. `boolean tryLock(long time, TimeUnit unit)`
- 또는 락을 당장 얻을 수 없다면 그냥 바로 빠져나올 수도 있다. : `boolean tryLock()`
- 이를 활용하면 아래와 같이 깔끔한 코드를 짤 수 있다.

```java
public boolean withdraw(int amount) {
  if (!lock.tryLock()) {
    return false;
  }

  try {
    if (balance < amount) {
    return false;
    }
    sleep(1000);
    balance = balance - amount;
  } finally {
    lock.unlock();
  }
  log("거래 종료");
  return true;
}
```
