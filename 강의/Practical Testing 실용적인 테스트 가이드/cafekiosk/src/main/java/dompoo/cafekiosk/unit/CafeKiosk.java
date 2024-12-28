package dompoo.cafekiosk.unit;

import dompoo.cafekiosk.unit.beverages.Beverage;
import dompoo.cafekiosk.unit.order.Order;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class CafeKiosk {
    
    private static final LocalTime SHOP_OPEN_TIME = LocalTime.of(10, 0);
    private static final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22, 0);
    
    private final List<Beverage> beverages = new ArrayList<>();
    
    public void add(Beverage beverage) {
        beverages.add(beverage);
    }
    
    public void add(Beverage beverage, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("음료는 한잔 이상 주문하실 수 있습니다.");
        }
        
        for (int i = 0; i < count; i++) {
            beverages.add(beverage);
        }
    }
    
    public void remove(Beverage beverage) {
        beverages.remove(beverage);
    }
    
    public void deleteAll() {
        beverages.clear();
    }
    
    public int calculateTotalPrice() {
        int totalPrice = 0;
        for (Beverage beverage : beverages) {
            totalPrice += beverage.getPrice();
        }
        return totalPrice;
    }
    
//    public Order createOrder() {
//        LocalDateTime curDateTime = LocalDateTime.now();
//        LocalTime curTime = curDateTime.toLocalTime();
//
//        if (curTime.isAfter(SHOP_CLOSE_TIME) || curTime.isBefore(SHOP_OPEN_TIME)) {
//            throw new IllegalArgumentException("주문 시간이 아닙니다.");
//        }
//        return new Order(curDateTime, beverages);
//    }
    
    /*
    테스트하기 어려운 영역은 분리한다.
    테스트하기 어려운 영역 이란?
    - 관측할 때마다 다른 값에 의존하는 코드
    - 외부에 영향을 주는 코드
    반대로 테스트하기 좋은 영역은?
    - 같은 입력에는 항상 같은 결과를 주는 코드
    - 외부와 영향을 적게 주고 받는 코드
     */
    
    public Order createOrder(LocalDateTime curDateTime) {
        LocalTime curTime = curDateTime.toLocalTime();
        
        if (curTime.isAfter(SHOP_CLOSE_TIME) || curTime.isBefore(SHOP_OPEN_TIME)) {
            throw new IllegalArgumentException("주문 시간이 아닙니다.");
        }
        return new Order(curDateTime, beverages);
    }
}
