# 스레드 생성 - Runnable

- 사실 실무에서는 **Thread**를 상속하여 스레드를 생성하지 않는다.
- 대신 주로 **Runnable**을 구현하여 생성하게 된다.

```java
public static void main(String[] args) {
	System.out.println(Thread.currentThread().getName() + " MAIN START");
	
	MyThread myThread = new MyThread();
	Thread thread = new Thread(myThread);
	thread.start();
	
	System.out.println(Thread.currentThread().getName() + " MAIN END");
}
	
private static class MyThread implements Runnable {
	
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " RUN START");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		System.out.println(Thread.currentThread().getName() + " RUN END");
	}
}
```

- 참고로 `new Thread()`로 생성할 때, 두번째 파라미터로 스레드 이름을 지정해줄 수도 있다.

## 왜 Runnable이 권장될까?

- 자바가 단일 상속만을 지원하므로, 다른 클래스를 상속받는 경우 **Thread**를 상속받을 수 없다.
- 또한 인터페이스 방식이 좀 더 유연하다.
- **Runnable**을 구현하는 방식은 실행하는 작업만이 작성되므로, 좀 더 객체지향적이다.
  - 실제 수행하고 싶은 작업과는 상관없는 다른 스레드 관련 기능이 노출되지 않는다.
  - 평소에는 그냥 객체처럼 사용하다가, 스레드 관련 기능이 필요한 경우 스레드를 그때 생성하여 작동시키면 된다.
