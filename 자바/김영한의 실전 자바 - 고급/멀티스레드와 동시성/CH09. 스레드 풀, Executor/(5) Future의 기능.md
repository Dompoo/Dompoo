# Future의 기능

## 취소 기능

- `cancel()`
  - 작업이 실행중이 아니거나 아직 시작되지 않았으면 취소하고, 실행중인 경우 파라미터에 따라 다르게 동작한다.
  - `cancel(true)` : `Future`를 취소 상태로 변경한다. 작업이 실행중이라면 `interrupt()`를 통하여 작업을 중단한다.
  - `cancel(false)` : `Future`를 취소 상태로 변경한다. 작업이 실행중이라면 중단하지 않는다.
  - 반환값 : 작업이 성공적으로 취소되었는가?

## 상태 기능

- `isCancelled()`
  - 취소 여부를 확인한다.
- `isDone()`
  - 작업 종료 여부(정상 완료, 취소 등)를 확인한다.
- `state()`
  - `Future`의 상태를 열거형으로 반환한다.

## 작업 기능

- `get()`
  - 작업이 완료될 때까지 기다리고, 완료되면 결과를 반환한다.
  - 예외
    - `InturruptedException` : 대기 스레드가 인터럽트되면 발생한다.
    - `ExecutionException` : 작업 계산 중 예외가 터지면 발생한다.
  - `get(long timeout, TimeUnit unit)` 을 통하여 대기 시간을 지정할 수 있다.
    - 이 경우 `TimeoutException` 이 발생할 수 있다.
- `submit()`
  - 작업을 한개 제출한다.
- `List<Future<T>> invokeAll()`
  - 작업을 컬렉션에 담아 여러개 한번에 제출하고, 모두 완료될 때까지 기다린다.
  - 모두 완료된 후에 반환되므로, `get()`을 하며 추가 대기하지 않는다.
- `T invokeAny()`
  - 작업을 컬렉션에 담아 여러개 한번에 제출하고, 가장 먼저 완료된 작업의 결과를 반환한다.

## 예외에 대하여

- `cancel()`한 `Future`를 `get()`하려고 하면 예외가 발생한다.
- `get()`을 호출했는데, 인터럽트가 걸리면 예외가 발생한다.
- `get()`을 호출했는데, 스레드가 작업하는 도중 예외가 발생하면 발생한다.
- `get(timeout)`을 호출했는데, 타임아웃이 지나면 예외가 발생한다.

## 활용

- 아래 예제는 3개의 task를 병렬 실행하는 예제이다.
- 단, `ExecutorService`가 적절히 종료되지 못하고 있는데, 이는 `OrderService`가 `AutoCloseable`을 구현하도록 하여 해결할 수 있다.
  - `ExecutorService`의 종료에 대하여는 추후에 더 자세히 다룬다.

```java
public class OrderService {
    
    private final ExecutorService es = Executors.newFixedThreadPool(4);
    
    public void order(String orderNo) throws InterruptedException, ExecutionException {
        InventoryWork inventoryWork = new InventoryWork(orderNo);
        ShippingWork shippingWork = new ShippingWork(orderNo);
        AccountingWork accountingWork = new AccountingWork(orderNo);

        // 작업 요청
        List<Future<Boolean>> futures = es.invokeAll(List.of(inventoryWork, shippingWork, accountingWork));
        
        for (Future<Boolean> future : futures) {
            if (!future.get()) {
                log("일부 작업이 실패했습니다.");
                return;
            }
        }
        log("모든 주문 처리가 성공적으로 완료되었습니다.");
    }

    static class InventoryWork implements Callable<Boolean> {

        private final String orderNo;

        public InventoryWork(String orderNo) {
            this.orderNo = orderNo;
        }

        @Override
        public Boolean call() {
            log("재고 업데이트: " + orderNo);
            sleep(1000);
            return true;
        }
    }

    static class ShippingWork implements Callable<Boolean> {

        private final String orderNo;

        public ShippingWork(String orderNo) {
            this.orderNo = orderNo;
        }

        @Override
        public Boolean call() {
            log("배송 시스템 알림: " + orderNo);
            sleep(1000);
            return true;
        }
    }

    static class AccountingWork implements Callable<Boolean> {

        private final String orderNo;

        public AccountingWork(String orderNo) {
            this.orderNo = orderNo;
        }

        @Override
        public Boolean call() {
            log("회계 시스템 업데이트: " + orderNo);
            sleep(1000);
            return true;
        }
    }
}
```
