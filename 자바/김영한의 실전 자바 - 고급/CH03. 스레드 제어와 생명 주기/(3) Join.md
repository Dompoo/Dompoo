# Join

## Join은 왜 필요할까?

```java
public static void main(String[] args) {
	ThreadUtils.log("START");
	Thread thread1 = new Thread(new Job(), "Thread-01");
	Thread thread2 = new Thread(new Job(), "Thread-02");
	thread1.start();
	thread2.start();
	ThreadUtils.log("END");
}
	
private static class Job implements Runnable {
	
	@Override
	public void run() {
		ThreadUtils.log("작업 시작");
		ThreadUtils.sleep(2000);
		ThreadUtils.log("작업 완료");
	}
}
```

- 위와 같은 코드에서 main 스레드의 END 로그는 마지막에 남지 않는다.
- main 스레드는 다른 스레드가 종료될 때 까지 기다리지 않고, 명령만 내리고 자기 갈 길을 간다.
- 만약 다른 스레드에서의 결과값이 필요하다면? 다른 스레드의 종료를 기다려야 한다.
- 이럴 때 사용하는 것이 **Join**이다.

## Join을 사용하는 예제

- 스레드 1에서는 1~50까지 더하고, 스레드 2에서는 51~100까지 더하여 main 스레드에서는 이를 더하여 1~100까지의 덧셈을 2배 빠르게 구해보자.

```java
public static void main(String[] args) {
	ThreadUtils.log("START");
	Task task1 = new Task(1, 50);
	Task task2 = new Task(51, 100);
	Thread thread1 = new Thread(task1, "Thread-01");
	Thread thread2 = new Thread(task2, "Thread-02");
	thread1.start();
	thread2.start();
	ThreadUtils.log(task1.result);
	ThreadUtils.log(task2.result);
	ThreadUtils.log("result : " + (task1.result + task2.result));
	ThreadUtils.log("END");
}
	
private static class Task implements Runnable {
	
	private final int startValue;
	private final int endValue;
	public int result;
	
	public Task(int startValue, int endValue) {
		this.startValue = startValue;
		this.endValue = endValue;
	}
	
	@Override
	public void run() {
		ThreadUtils.log("작업 시작");
		ThreadUtils.sleep(2000);
		for (int i = startValue; i <= endValue; i++) {
			result += i;
		}
		ThreadUtils.log("작업 완료, result = " + result);
	}
}
```

- 안타깝지만 위 코드의 결과는 0이다.
- 각 스레드에서는 2초 후에 1275, 3775의 값이 뒤늦게 나온다.
- 즉, main 스레드에서 `task1.result + task2.result`는 두 스레드의 작업이 모두 끝나고 실행되어야 한다.

![Join 없는 결과](Join_없는_결과.png)

## Sleep으로 해결

```java
thread1.start();
thread2.start();
ThreadUtils.log(task1.result); // 0
ThreadUtils.log(task2.result); // 0
Thread.sleep(3000);
ThreadUtils.log(task1.result); // 1275
ThreadUtils.log(task2.result); // 3775
```

- 하지만 이 방법은 타이밍을 맞추기 어렵다.
  - 언제 각 스레드의 작업이 끝날까?
  - 무작정 오래 기다리면 느려질 뿐이다.
  - 다른 스레드의 작업이 끝나자마자 바로 실행되었으면 좋겠다.
- 다른 방법으로는 무한루프를 통해 다른 스레드가 **TERMINATED** 상태가 되는지 확인하는 방법이 있다.
  - 하지만, CPU 연산을 너무 많이 쓴다.
- 이것을 해결하기 위해 **Join**을 사용한다.

## Join으로 해결

```java
// ...
thread1.start();
thread2.start();
ThreadUtils.log(task1.result); // 0
ThreadUtils.log(task2.result); // 0
thread1.join();
thread2.join();
ThreadUtils.log(task1.result); // 1275
ThreadUtils.log(task2.result); // 3775

/* 결과
12:14:38.380 [Thread-02] 작업 완료, result = 3775
12:14:38.380 [Thread-01] 작업 완료, result = 1275
12:14:38.381 [     main] 1275
12:14:38.381 [     main] 3775
*/
```

- `join()`을 사용하면 main 스레드가 각 스레드의 작업이 끝날때까지 대기한다.
  - 타임스탬프를 보면 0.001초만에 깨어난 것을 확인할 수 있다.
- 이렇게 `join()` 이후에 다른 스레드가 끝나기를 기다리는 상태가 **WAITING**이다.
- `join()`은 기본적으로 무한정 대기하기 때문에, 특정 시간만큼만 대기하도록 설정할 수 있다.
  - `join()` 메서드에 파라미터로 millis를 넘겨주면 된다.
