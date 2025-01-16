# ExecutorService의 종료

## 서비스 종료

- `void shutdown()` : 새로운 작업은 받지 않고, 이미 제출된 작업을 모두 완료하고 종료한다.
  - 새로운 작업을 넣으려고 하면 예외가 발생한다.
  - 스레드가 실행중인 작업을 마저 완료한다.
  - 블로킹큐의 작업을 모두 꺼내어 처리한다.
- `List<Runnable> shutdownNow()` : 실행 중인 작업을 중단하고, 대기 중인 작업을 반환하며 즉시 종료한다.
  - 작업 중인 스레드에 인터럽트를 발생시켜 작업을 중단시킨다.
  - 블로킹큐의 작업을 모두 꺼내어 반환한다.
- `void close()` : `shutdown()`을 호출하고 하루가 지나도 작업이 완료되지 않으면 `shutdownNow()`를 호출한다. (호출 스레드에 인터럽트가 걸려도 바로 호출한다.)

## 서비스 상태 확인

- `boolean isShutdown()` : 서비스가 종료되었는지 확인한다.
- `boolean isTerminated()` : `shutdown()`, `shutdownNow()` 호출 후, 모든 작업이 완료되었는지 확인한다.

## 작업 완료 대기

- `boolean awaitTermination(long timeout, TimeUnit unit)` : 서비스 종료시 모든 작업이 완료될 때까지 대기한다. 지정된 시간까지 기다린다.
