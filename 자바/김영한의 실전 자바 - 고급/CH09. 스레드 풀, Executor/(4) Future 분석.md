# Future 분석

```java
Future<Integer> future = es.submit(new MyCallable());
```

- `submit()`을 호출하며 Callable을 전달하기만 한다.
- `Callable.call()`은 스레드 풀의 다른 스레드가 실행하게 된다.
- 따라서 언제 실행이 완료되어 결과를 반환할지 알 수 없다.
- **따라서 바로 결과를 반환하는 대신에, 결과를 나중에 받을 수 있는 `Future`라는 객체를 대신 제공한다.**
  - 즉, `Future` 자체는 즉시 반환되지만, `Future.get()`을 호출한 스레드는 작업이 다 끝날 때까지 WAITING 상태로 기다린다.

## Future 동작 정리

- `ExecutorService`는 `submit()` 메서드 호출시 task의 결과를 알 수 있는 `Future` 객체를 생성한다.
  - 이때 생성되는 객체의 실제 구현체는 `FutureTask`이다.
- `submit()` 메서드의 결과로 `Future` 객체를 즉시 반환한다.
  - `Future` 객체의 내부에는 작업의 완료 여부와, 작업의 결과값을 가진다.
- 또한 `Future` 객체는 내부의 블로킹큐에 저장되며, 가능할 때 스레드 풀에서 스레드를 꺼내 작업을 수행한다.

![Executor와 Future](<Executor와_Future.png>)

- 만약 `future.get()`을 호출했을 때까지 아직 task가 끝나지 않았다면 (결과를 반환할 수 없다면) 요청 스레드는 `Future`가 완료 상태가 될 때까지 대기한다.
- 이때 요청 스레드는 WAITING 상태가 된다.
  - 이렇게 스레드가 어떠한 결과를 위해 대기하는 것을 **블로킹**이라고 부른다. (`Thread.join()`과 비슷하다.)
- 만약에 task가 모두 끝났다면 task를 처리했던 스레드 풀의 스레드가
  - task의 결과를 `Future`에 담는다.
  - 요청 스레드를 깨운다.
- 요청 스레드는 WAITING -> RUNNABLE 상태로 바뀐다.

## 활용해보자

```java
public static void main(String[] args) throws ExecutionException, InterruptedException {
	SumTask sumTask1 = new SumTask(1, 50);
	SumTask sumTask2 = new SumTask(51, 100);
	
	try (ExecutorService es = Executors.newFixedThreadPool(2)) {
		Future<Integer> future1 = es.submit(sumTask1);
		Future<Integer> future2 = es.submit(sumTask2);
	
		System.out.println("결과 1 : " + future1.get());
		System.out.println("결과 2 : " + future2.get());
		System.out.println("합산 : " + (future1.get() + future2.get()));
	}
}
	
static class SumTask implements Callable<Integer> {
	
	private final int start;
	private final int end;
	
	public SumTask(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	@Override
	public Integer call() throws Exception {
		log("작업 시작");
		int sum = 0;
		for (int number = start; number <= end; number++) {
			sum += number;
		}
		log("작업 종료");
		return sum;
	}
}
```
