package dompoo.cafekiosk.unit;

import dompoo.cafekiosk.unit.beverages.Americano;
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
    void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());
        
        System.out.println(">>> 담긴 음료 수 : " + cafeKiosk.getBeverages().size());
        System.out.println(">>> 담긴 음료 수 : " + cafeKiosk.getBeverages().getFirst().getName());
    }
    
}