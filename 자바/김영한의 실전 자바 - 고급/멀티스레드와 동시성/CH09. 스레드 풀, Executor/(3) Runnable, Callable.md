# Runnable, Callable

- 반환값이 없다. 반환값이 필요한 경우 멤버 변수에 넣어두었다가 나중에 꺼내는 방식을 채택해야 한다.
- 체크 예외를 던질 수 없다. 따라서 메서드 내부에서 반드시 처리되어야 한다.

## Callable의 등장

```java
@FunctionalInterface
public interface Callable<V> {

    V call() throws Exception;
}
```

- 제네릭을 통해 값을 반환할 수 있다.
- `throws Exception` 덕분에 체크 예외도 던질 수 있다.
- 이 `Callable`을 쓰면 `Future`라는 객체를 받아볼 수 있다.

```java
public static void main(String[] args) throws ExecutionException, InterruptedException {
    try (ExecutorService es = Executors.newFixedThreadPool(1)) {
        Future<Integer> future = es.submit(new MyCallable());
        Integer result = future.get();
        log("result value = " + result);
    }
}
	
private static class MyCallable implements Callable<Integer> {
		
    @Override
    public Integer call() {
        log("Callable 시작");
        sleep(2000);
        int value = new Random().nextInt(10);
        log("Callable 완료");
        return value;
    }
}
```

- `submit()`을 통해 `Callable`을 작업으로 전달할 수 있다.
  - 작업 인스턴스가 블로킹큐에 전달되고, 스레드 풀의 스레드 중 하나가 이를 처리할 것이다.
- 작업 결과는 바로 반환받는 것이 아니라, `Future`를 통해서 받게 된다.
- `Executor` 프레임워크를 사용하니까 정말 싱글스레드 프로그래밍을 하듯 편리하다.
  - 단지 `ExecutorService`에 작업을 요청하고 결과를 받아서 쓰면 된다.
- 근데 만약, `future.get()`을 하기 전에 스레드의 작업이 덜 끝났다면 어떻게 될까?
  - 이미 끝났다면 그냥 받아보면 된다.
  - 아직 안 끝났다면 `get()`으로 받아볼 반환값이 없다...?
