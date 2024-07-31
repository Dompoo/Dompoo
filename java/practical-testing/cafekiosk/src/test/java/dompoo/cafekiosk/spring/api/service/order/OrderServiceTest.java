package dompoo.cafekiosk.spring.api.service.order;

import dompoo.cafekiosk.spring.api.service.order.dto.request.OrderCreateServiceRequest;
import dompoo.cafekiosk.spring.api.service.order.dto.response.OrderResponse;
import dompoo.cafekiosk.spring.domain.order.OrderRepository;
import dompoo.cafekiosk.spring.domain.orderproduct.OrderProductRepository;
import dompoo.cafekiosk.spring.domain.product.Product;
import dompoo.cafekiosk.spring.domain.product.ProductRepository;
import dompoo.cafekiosk.spring.domain.product.ProductType;
import dompoo.cafekiosk.spring.domain.stock.Stock;
import dompoo.cafekiosk.spring.domain.stock.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static dompoo.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static dompoo.cafekiosk.spring.domain.product.ProductType.*;
import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
//@Transactional
//Transactional로 롤백을 하면, 실제 서비스 코드 내부에서 트랜잭션이 걸리든 안걸리든 상관없이
//트랜잭션 내부에서 코드가 실행된다. -> 트랜잭션이 없어서 생기는 문제를 파악할 수 없게 된다.
/*
+ save delete 등은 SimpleJpaRepository라는 구현체에 트랜잭션이 이미 걸려 있기 때문에 쿼리가 잘 나가지만,
변경감지 기능은 트랜잭션 외부에서 실행되므로 작동하지 않았던 것이다.
 */
class OrderServiceTest {
    
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private OrderService orderService;
    
    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        stockRepository.deleteAllInBatch();
    }
    
    @Test
    @DisplayName("주문 번호 리스트를 받으면 주문을 생성해야 한다.")
    void createOrder() {
        //given
        Product product1 = createProduct("001", HANDMADE, 1000);
        Product product2 = createProduct("002", HANDMADE, 3000);
        Product product3 = createProduct("003", HANDMADE, 5000);
        productRepository.saveAll(List.of(product1, product2, product3));
        OrderCreateServiceRequest request = OrderCreateServiceRequest.builder()
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
    
    @Test
    @DisplayName("재고와 관련있는 상품이 포함된 주문 번호 리스트를 받으면 주문을 생성해야 한다.")
    void createOrderWithStock() {
        //given
        Product product1 = createProduct("001", HANDMADE, 1000);
        Product product2 = createProduct("002", BOTTLE, 3000);
        Product product3 = createProduct("003", BAKERY, 5000);
        
        Stock stock1 = Stock.create("002", 2);
        Stock stock2 = Stock.create("003", 2);
        
        stockRepository.saveAll(List.of(stock1, stock2));
        productRepository.saveAll(List.of(product1, product2, product3));
        
        OrderCreateServiceRequest request = OrderCreateServiceRequest.builder()
            .productNumbers(List.of("001", "002", "002", "003"))
            .build();
        LocalDateTime now = LocalDateTime.now();
        
        //when
        OrderResponse response = orderService.createOrder(request, now);
        
        //then
        assertThat(response.getId()).isNotNull();
        assertThat(response)
            .extracting("registeredDateTime", "totalPrice")
            .contains(now, 12000);
        assertThat(response.getProducts()).hasSize(4)
            .extracting("productNumber", "price")
            .containsExactlyInAnyOrder(
                tuple("001", 1000),
                tuple("002", 3000),
                tuple("002", 3000),
                tuple("003", 5000)
            );
        
        List<Stock> stocks = stockRepository.findAll();
        assertThat(stocks).hasSize(2)
            .extracting("productNumber", "quantity")
            .containsExactlyInAnyOrder(
                tuple("002", 0),
                tuple("003", 1)
            );
    }
    
    @Test
    @DisplayName("재고가 부족한 상품이 포함된 주문 번호 리스트를 받으면 예외가 발생해야 한다.")
    void createOrderWithLessStock() {
        //given
        Product product1 = createProduct("001", HANDMADE, 1000);
        Product product2 = createProduct("002", BOTTLE, 3000);
        Product product3 = createProduct("003", BAKERY, 5000);
        
        Stock stock1 = Stock.create("002", 2);
        Stock stock2 = Stock.create("003", 2);
        
        stockRepository.saveAll(List.of(stock1, stock2));
        productRepository.saveAll(List.of(product1, product2, product3));
        
        OrderCreateServiceRequest request = OrderCreateServiceRequest.builder()
            .productNumbers(List.of("001", "002", "002", "002", "003"))
            .build();
        LocalDateTime now = LocalDateTime.now();
        
        //when //then
        assertThatThrownBy(() -> orderService.createOrder(request, now))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("재고가 부족한 상품이 있습니다.");
    }
    
    @Test
    @DisplayName("중복되는 제품번호로 주문할 수 있어야 한다.")
    void createOrderByDuplicatedNumber() {
        //given
        Product product1 = createProduct("001", HANDMADE, 1000);
        Product product2 = createProduct("002", HANDMADE, 3000);
        Product product3 = createProduct("003", HANDMADE, 5000);
        productRepository.saveAll(List.of(product1, product2, product3));
        OrderCreateServiceRequest request = OrderCreateServiceRequest.builder()
            .productNumbers(List.of("001", "001"))
            .build();
        LocalDateTime now = LocalDateTime.now();
        
        //when
        OrderResponse response = orderService.createOrder(request, now);
        
        //then
        assertThat(response.getId()).isNotNull();
        assertThat(response)
            .extracting("registeredDateTime", "totalPrice")
            .contains(now, 2000);
        assertThat(response.getProducts()).hasSize(2)
            .extracting("productNumber", "price")
            .containsExactlyInAnyOrder(
                tuple("001", 1000),
                tuple("001", 1000)
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