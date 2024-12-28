package dompoo.cafekiosk.spring.domain.order;

import static dompoo.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;

import dompoo.cafekiosk.spring.domain.product.Product;
import dompoo.cafekiosk.spring.domain.product.ProductType;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    @DisplayName("주문 생성시 상품 리스트에서 주문의 총 금액을 계산한다.")
    void caculateTotalPrice() {
        //given
        List<Product> products = List.of(
            createProduct("001", 1000),
            createProduct("001", 3000),
            createProduct("001", 5000)
        );
        
        //when
        Order order = Order.create(products, LocalDateTime.now());
        
        //then
        Assertions.assertThat(order.getTotalPrice()).isEqualTo(9000);
    }
    
    @Test
    @DisplayName("주문 생성시 상품 리스트에서 주문 상태는 INIT이어야 한다.")
    void init() {
        //given
        List<Product> products = List.of(
            createProduct("001", 1000),
            createProduct("001", 3000),
            createProduct("001", 5000)
        );
        
        //when
        Order order = Order.create(products, LocalDateTime.now());
        
        //then
        Assertions.assertThat(order.getStatus()).isEqualByComparingTo(OrderStatus.INIT);
    }
    
    @Test
    @DisplayName("주문 생성시 등록시간이 기록되어야 한다.")
    void registedDateTime() {
        //given
        LocalDateTime now = LocalDateTime.now();
        List<Product> products = List.of(
            createProduct("001", 1000),
            createProduct("001", 3000),
            createProduct("001", 5000)
        );
        
        //when
        Order order = Order.create(products, now);
        
        //then
        Assertions.assertThat(order.getRegistedDateTime()).isEqualTo(now);
    }
    
    private static Product createProduct(String productNumber, int price) {
        return Product.builder()
            .name("아메리카노")
            .price(price)
            .productNumber(productNumber)
            .sellingStatus(SELLING)
            .type(ProductType.HANDMADE)
            .build();
    }
}