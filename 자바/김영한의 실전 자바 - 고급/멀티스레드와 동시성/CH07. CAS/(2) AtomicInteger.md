# AtomicInteger

- 우리가 만든 것과 비슷한 기능을 자바에서 제공한다.
- Integer 말고도 Long, Boolean등 다양한 클래스가 존재한다.
  - 멀티스레드 상황에서는 이 클래스들을 활용하면 좋다.

```java
AtomicInteger atomicInteger = new AtomicInteger(15);
atomicInteger.addAndGet(10);
atomicInteger.get();
```

- 여기서 놀라운 사실은, **AtomicXxx**의 여러 원자적 연산들은 락을 사용하지 않는다.
- 바로 **CAS**라는 것을 사용한다.
