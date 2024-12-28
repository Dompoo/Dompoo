package dompoo.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTypeTest {
    
    @Test
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    void contaionsStockType() {
        //given
        ProductType type1 = ProductType.HANDMADE;
        ProductType type2 = ProductType.BOTTLE;
        ProductType type3 = ProductType.BAKERY;
        
        //when
        boolean result1 = ProductType.containsStockType(type1);
        boolean result2 = ProductType.containsStockType(type2);
        boolean result3 = ProductType.containsStockType(type3);
        
        //then
        assertThat(result1).isFalse();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
    }
    
    /*
    매개변수화된 테스트를 사용하여 여러 상황에 대해 테스트를 수행할 수 있다.
    여러 Source를 사용하여 값을 넣어줄 수 있다.
    하지만 항상 좋은 것은 아니다.
    간단하다면 나누어 테스트하는 것이 좋을 수도 있다.
    [Unit Testing]에서는 테스트명이 명시적이지 못한 문제를 들었다.
     */
    @ParameterizedTest
    @CsvSource({"HANDMADE,false", "BOTTLE,true", "BAKERY,true"})
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    void contaionsStockTypeParameterizedTest(ProductType type, boolean expected) {
        //when
        boolean result = ProductType.containsStockType(type);
        
        //then
        assertThat(result).isEqualTo(expected);
    }
}