package dompoo.cafekiosk.spring.domain.stock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StockTest {
    
    @Test
    @DisplayName("현재 있는 재고가 필요한 재고보다 적은지 확인할 수 있어야 한다.")
    void isQuantityLessThan() {
        //given
        Stock stock = Stock.create("001", 10);
        long requiedQuantity = 10;
        
        //when
        boolean result = stock.isQuantityLessThan(requiedQuantity);
        
        //then
        assertThat(result).isFalse();
    }
    
    @Test
    @DisplayName("현재 있는 재고가 필요한 재고보다 적은지 확인할 수 있어야 한다.")
    void isQuantityLessThan2() {
        //given
        Stock stock = Stock.create("001", 10);
        long requiedQuantity = 11;
        
        //when
        boolean result = stock.isQuantityLessThan(requiedQuantity);
        
        //then
        assertThat(result).isTrue();
    }
    
    @Test
    @DisplayName("재고를 주어진 개수만큼 차감시킬 수 있어야 한다.")
    void deductQuantity() {
        //given
        Stock stock = Stock.create("001", 5);
        long quantity = 5;
        
        //when
        stock.deductQuantity(quantity);
        
        //then
        assertThat(stock.getQuantity()).isEqualTo(0);
    }
    
    @Test
    @DisplayName("재고보다 많은 개수만큼 차감하는 경우 예외가 발생한다.")
    void deductQuantityMore() {
        //given
        Stock stock = Stock.create("001", 5);
        long quantity = 6;
        
        //when //then
        assertThatThrownBy(() -> stock.deductQuantity(quantity))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("차감할 재고 수량이 없습니다.");
    }
}