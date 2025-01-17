# ExecutorService의 종료

## 서비스 종료

- `void shutdown()` : 새로운 작업은 받지 않고, 이미 제출된 작업을 모두 완료하고 종료한다.
  - 새로운 작업을 넣으려고 하면 예외가 발생한다.
  - 스레드가 실행중인 작업을 마저 완료한다.
  - 블로킹큐의 작업을 모두 꺼내어 처리한다.
  - `non-blocking` 메서드이다.
- `List<Runnable> shutdownNow()` : 실행 중인 작업을 중단하고, 대기 중인 작업을 반환하며 즉시 종료한다.
  - 작업 중인 스레드에 **인터럽트를 발생시켜 작업을 중단시킨다.**
    - 인터럽트를 발생시킨다고 무조건 종료되는 것은 아니다. `while(true)` 같은 상황에서 그럴 수 있으므로, 이 경우에는 종료에 실패한 것이다.
  - 블로킹큐의 작업을 모두 꺼내어 반환한다.
  - `non-blocking` 메서드이다.
- `void close()` : `shutdown()`을 호출하고 하루가 지나도 작업이 완료되지 않으면 `shutdownNow()`를 호출한다. (호출 스레드에 인터럽트가 걸려도 바로 호출한다.)

## 서비스 상태 확인

- `boolean isShutdown()` : 서비스가 종료되었는지 확인한다.
- `boolean isTerminated()` : `shutdown()`, `shutdownNow()` 호출 후, 모든 작업이 완료되었는지 확인한다.

## 작업 완료 대기

- `boolean awaitTermination(long timeout, TimeUnit unit)` : 서비스 종료시 모든 작업이 완료될 때까지 대기한다. 지정된 시간까지 기다린다.
  - `blocking` 메서드이다.

## 우아한 종료

- `close()`의 하루 대기는 너무 길다.
- 따라서 보통 `shutdown()`을 호출하고 특정 시간을 기다린 후, 그래도 완료되지 않으면 `shutdownNow()`를 실행하는 우아한 종료를 구현하는 방식을 택한다.

```java
private static void shutdownAndAwaitTermination(ExecutorService es) {
    es.shutdown(); // non-blocking, 새로운 작업을 받지 않고 기존 작업을 모두 수행한다.
	try {
        // 이미 대기중인 작업들을 모두 완료될 때까지 기다린다.
        if (!es.awaitTermination(10, TimeUnit.SECONDS)) {
            // 정상 종료가 너무 오래 걸리면 강제 종료한다.
            es.shutdownNow();
            if (!es.awaitTermination(10, TimeUnit.SECONDS)) {
                ThreadUtils.log("서비스가 종료되지 않았습니다.");
            } else {
                ThreadUtils.log("서비스가 강제 종료되었습니다.");
            }
        } else {
            ThreadUtils.log("서비스가 정상 종료되었습니다.");
        }
	} catch (InterruptedException e) {
        // es.awaitTermination() 중, 대기중인 스레드가 인터럽트 될 수 있다.
        es.shutdownNow();
        ThreadUtils.log("서비스가 강제 종료되었습니다.");
		throw new RuntimeException(e);
	}
}
```
