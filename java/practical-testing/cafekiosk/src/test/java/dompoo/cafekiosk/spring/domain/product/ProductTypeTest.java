package dompoo.cafekiosk.spring.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTypeTest {
    
    @Test
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    void contaionsStockType() {
        //given
        ProductType handmade = ProductType.HANDMADE;
        
        //when
        boolean result = ProductType.containsStockType(handmade);
        
        //then
        assertThat(result).isFalse();
    }
    
    @Test
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    void contaionsStockType2() {
        //given
        ProductType handmade = ProductType.BOTTLE;
        
        //when
        boolean result = ProductType.containsStockType(handmade);
        
        //then
        assertThat(result).isTrue();
    }
}