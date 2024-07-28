package dompoo.cafekiosk.spring.api.service.order;

import static dompoo.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static dompoo.cafekiosk.spring.domain.product.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import dompoo.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import dompoo.cafekiosk.spring.api.service.order.response.OrderResponse;
import dompoo.cafekiosk.spring.domain.product.Product;
import dompoo.cafekiosk.spring.domain.product.ProductRepository;
import dompoo.cafekiosk.spring.domain.product.ProductType;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest {
    
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderService orderService;
    
    @Test
    @DisplayName("주문 번호 리스트를 받으면 주문을 생성해야 한다.")
    void createOrder() {
        //given
        Product product1 = createProduct("001", HANDMADE, 1000);
        Product product2 = createProduct("002", HANDMADE, 3000);
        Product product3 = createProduct("003", HANDMADE, 5000);
        productRepository.saveAll(List.of(product1, product2, product3));
        OrderCreateRequest request = OrderCreateRequest.builder()
            .productNumbers(List.of("001", "002"))
            .build();
        LocalDateTime now = LocalDateTime.now();
        
        //when
        OrderResponse response = orderService.createOrder(request, now);
        
        //then
        assertThat(response.getId()).isNotNull();
        assertThat(response)
            .extracting("registeredDateTime", "totalPrice")
            .contains(now, 4000);
        assertThat(response.getProducts()).hasSize(2)
            .extracting("productNumber", "price")
            .containsExactlyInAnyOrder(
                tuple("001", 1000),
                tuple("002", 3000)
            );
    }
    
    private static Product createProduct(String productNumber, ProductType productType, int price) {
        return Product.builder()
            .name("아메리카노")
            .price(price)
            .productNumber(productNumber)
            .sellingStatus(SELLING)
            .type(productType)
            .build();
    }
}