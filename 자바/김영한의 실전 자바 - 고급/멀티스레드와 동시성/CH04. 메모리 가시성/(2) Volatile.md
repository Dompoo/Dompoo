# Volatile

- 캐시 메모리를 사용하면 성능은 좋다.
- 하지만 가끔씩 성능을 포기하더라도 동일한 시점에 동일한 데이터를 보는 것이 중요할 수 있다.
- 이때 사용하는 것이 **Volatile** 키워드이다.

```java
private static class Task implements Runnable {
	
	public volatile boolean runFlag = true;
	
	@Override
	public void run() {
		ThreadUtils.log("Task 시작");
		while (runFlag) {
			// Do Something
            // 외부에서 runFlag를 건드려야 종료되는 스레드
		}
		ThreadUtils.log("Task 종료");
	}
}
```

- 이렇게 `volatile` 키워드를 사용하여 캐시메모리를 사용하지 않고 메인메모리에서 바로 참조하도록 할 수 있다.
- 물론 성능이 조금 떨어지므로, 꼭 필요한 상황에서만 사용하자.
