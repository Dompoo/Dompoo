package dompoo.cafekiosk.unit;

import static org.assertj.core.api.Assertions.assertThat;

import dompoo.cafekiosk.unit.beverages.Americano;
import dompoo.cafekiosk.unit.beverages.Latte;
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
    
}