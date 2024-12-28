package dompoo.cafekiosk.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dompoo.cafekiosk.unit.beverages.Americano;
import dompoo.cafekiosk.unit.beverages.Latte;
import dompoo.cafekiosk.unit.order.Order;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

/*
수동 테스트의 문제점
- 무조건 성공한다.
- 내가 직접 콘솔에 찍힌 값을 보고 검증해야 한다.
- 느리다.
- 다른 사람은 이 테스트가 무엇을 검증하는지 명확하게 이해하지 못한다.
 */
class CafeKioskTest {
    
    @Test
    void add_manual() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());
        
        System.out.println(">>> 담긴 음료 수 : " + cafeKiosk.getBeverages().size());
        System.out.println(">>> 담긴 음료 수 : " + cafeKiosk.getBeverages().getFirst().getName());
    }
    
    @Test
    void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        
        cafeKiosk.add(new Americano());
        
        assertThat(cafeKiosk.getBeverages()).hasSize(1);
        assertThat(cafeKiosk.getBeverages().getFirst()).isExactlyInstanceOf(Americano.class);
    }
    
    @Test
    void addSeveralBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        
        cafeKiosk.add(new Americano(), 2);
        
        assertThat(cafeKiosk.getBeverages()).hasSize(2);
        assertThat(cafeKiosk.getBeverages().get(0)).isExactlyInstanceOf(Americano.class);
        assertThat(cafeKiosk.getBeverages().get(1)).isExactlyInstanceOf(Americano.class);
    }
    
    @Test
    void addOneBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        
        cafeKiosk.add(new Americano(), 1);
        
        assertThat(cafeKiosk.getBeverages()).hasSize(1);
        assertThat(cafeKiosk.getBeverages().get(0)).isExactlyInstanceOf(Americano.class);
    }
    
    @Test
    void addZeroBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        
        assertThatThrownBy(() ->
            cafeKiosk.add(new Americano(), 0))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("음료는 한잔 이상 주문하실 수 있습니다.");
    }
    
    @Test
    void remove() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);
        
        cafeKiosk.remove(americano);
        
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }
    
    @Test
    void deleteAll() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());
        cafeKiosk.add(new Latte());
        
        cafeKiosk.deleteAll();
        
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }
    
/*
    @Test
    void createTest() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        Order order = cafeKiosk.createOrder(); // 항상 성공하는 테스트는 아니다.

        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().getFirst()).isExactlyInstanceOf(Americano.class);
    }
*/
    
    @Test
    void createTestWithCurTime() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());
        
        Order order = cafeKiosk.createOrder(LocalDateTime.of(2024, 7, 28, 10, 0));
        
        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().getFirst()).isExactlyInstanceOf(Americano.class);
    }
    
    @Test
    void createTestBeforeShopOpen() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());
        
        assertThatThrownBy(() ->
            cafeKiosk.createOrder(LocalDateTime.of(2024, 7, 28, 9, 59)))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("주문 시간이 아닙니다.");
    }
    
    @Test
    void createTestAfterShopClose() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());
        
        assertThatThrownBy(() ->
            cafeKiosk.createOrder(LocalDateTime.of(2024, 7, 28, 22, 1)))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("주문 시간이 아닙니다.");
    }
    
    
    
}