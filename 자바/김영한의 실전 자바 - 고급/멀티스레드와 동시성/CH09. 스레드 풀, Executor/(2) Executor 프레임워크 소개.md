# Executor 프레임워크 소개

- 멀티스레딩 및 병렬 처리를 쉽게 적용할 수 있도록 도와주는 도구의 모음이다.

```java
public interface Executor {

    void execute(Runnable command);
}
```

- `Executor`는 별 다른 기능이 없고, 이 인터페이스를 확장한 `ExecutorService`에 대부분의 기능이 있다.
- `ExecutorService`의 대표 구현체는 `ThreadPoolExecutor`이다.

## ThreadPoolExecutor

- ThreadPoolExecutor는 크게 2가지 요소로 구성된다.
  - **스레드 풀** : 스레드를 관리한다.
  - **`BlockingQueue`** : 작업을 보관한다. 생산자 소비자 문제를 해결하기 위해 일반 큐 대신, `BlocingQueue`를 사용한다.
- `execute()`를 실행하면 내부에서 `BlocingQueue`에 작업을 보관한다. 메인 스레드가 생산자가 된다.
- 스레드 풀에 있는 스레드가 소비자가 된다. 스레드 풀에 있는 스레드들 중 하나가 `BlockingQueue`의 작업을 하나 받아서 처리한다.

### 생성자

- `corePoolSize` : 스레드 풀에서 관리되는 기본 스레드의 수
- `maximumPoolSize` : 스레드 풀에서 관리되는 최대 스레드 수
- `keepAliveTime`, `unit` : 기본 스레드 수를 초과해서 만들어진 스레드가 생존할 수 있는 대기 시간
- `workQueue` : 작업을 보관할 블로킹 큐

### 어떻게 동작하는가?

1. 생성자 호출에 블로킹큐와 스레드 풀이 생성된다.
2. 메인 스레드에서 `execute()` 메서드를 통해 taskA ~ taskD가 호출된다.
3. 블로킹큐에 해당 작업 4개를 넣는다.
4. 스레드를 생성하여(최초 생성) taskA를 처리한다.
5. 스레드를 생성하여(최초 생성) taskB를 처리한다.
6. taskA가 다 처리되었다면 스레드 풀에 스레드를 반환한다. (RUNNABLE -> WAITING)
7. taskB가 다 처리되었다면 스레드 풀에 스레드를 반환한다. (RUNNABLE -> WAITING)
8. taskC, taskD가 비슷한 방식으로 처리된다.
9. `close()`가 호출되면 스레드가 제거된다.
