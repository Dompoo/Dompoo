package dompoo.cafekiosk.spring.domain.product;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static dompoo.cafekiosk.spring.domain.product.ProductSellingStatus.*;
import static dompoo.cafekiosk.spring.domain.product.ProductType.BAKERY;
import static dompoo.cafekiosk.spring.domain.product.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
@DataJpaTest
//JPA 관련된 빈들만 로딩하기 때문에 좀 더 빠르다.
//자동으로 트랜잭션 롤백을 통한 clear를 해준다.
@ActiveProfiles("test")
class ProductRepositoryTest {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Test
    @DisplayName("원하는 판매 상태의 상품들이 조회되어야 한다.")
    void findAllBySellingStatusIn() {
        //given
        Product product1 = createProduct("001", HANDMADE, SELLING, 4000, "아메리카노");
        Product product2 = createProduct("002", HANDMADE, HOLD, 5000, "카페라떼");
        Product product3 = createProduct("003", BAKERY, STOP_SELLING, 5000, "크루아상");
        productRepository.saveAll(List.of(product1, product2, product3));
        
        //when
        List<Product> result = productRepository.findAllBySellingStatusIn(List.of(SELLING, HOLD));
        
        //then
        assertThat(result).hasSize(2)
            .extracting("productNumber", "name", "sellingStatus")
            .containsExactlyInAnyOrder(
                Tuple.tuple("001", "아메리카노", SELLING),
                Tuple.tuple("002", "카페라떼", HOLD)
            );
    }
    
    @Test
    @DisplayName("상품 번호로 상품들이 조회되어야 한다.")
    void findAllByProductNumberIn() {
        //given
        Product product1 = createProduct("001", HANDMADE, SELLING, 4000, "아메리카노");
        Product product2 = createProduct("002", HANDMADE, HOLD, 5000, "카페라떼");
        Product product3 = createProduct("003", BAKERY, STOP_SELLING, 5000, "크루아상");
        productRepository.saveAll(List.of(product1, product2, product3));
        
        //when
        List<Product> result = productRepository.findAllByProductNumberIn(List.of("001", "002"));
        
        //then
        assertThat(result).hasSize(2);
    }
    
    @Test
    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어올 수 있어야 한다.")
    void findLatestProductNumber() {
        //given
        String targetNumber = "003";
        Product product1 = createProduct("001", HANDMADE, SELLING, 4000, "아메리카노");
        Product product2 = createProduct("002", HANDMADE, HOLD, 5000, "카페라떼");
        Product product3 = createProduct(targetNumber, BAKERY, STOP_SELLING, 5000, "크루아상");
        productRepository.saveAll(List.of(product1, product2, product3));
        
        //when
        String latestProductNumber = productRepository.findLatestProductNumber();
        
        //then
        assertThat(latestProductNumber).isEqualTo("003");
    }
    
    @Test
    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어올 때, 상품이 없을 때는 null을 읽어야 한다.")
    void findLatestProductNumberEmpty() {
        //given //when
        String latestProductNumber = productRepository.findLatestProductNumber();
        
        //then
        assertThat(latestProductNumber).isNull();
        
    }
    
    private Product createProduct(String productNumber, ProductType type, ProductSellingStatus sellingStatus, int price, String name) {
        return Product.builder()
            .productNumber(productNumber)
            .name(name)
            .price(price)
            .sellingStatus(sellingStatus)
            .type(type)
            .build();
    }
}