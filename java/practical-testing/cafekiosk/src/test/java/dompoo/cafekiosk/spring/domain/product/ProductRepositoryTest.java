package dompoo.cafekiosk.spring.domain.product;

import static dompoo.cafekiosk.spring.domain.product.ProductSellingStatus.HOLD;
import static dompoo.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static dompoo.cafekiosk.spring.domain.product.ProductSellingStatus.STOP_SELLING;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

//@SpringBootTest
@DataJpaTest //JPA 관련된 빈들만 로딩하기 때문에 좀 더 빠르다.
@ActiveProfiles("test")
class ProductRepositoryTest {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Test
    @DisplayName("원하는 판매 상태의 상품들이 조회되어야 한다.")
    void findAllBySellingStatusIn() {
        //given
        Product product1 = Product.builder()
            .name("아메리카노")
            .price(4000)
            .productNumber("001")
            .sellingStatus(SELLING)
            .type(ProductType.HANDMADE)
            .build();
        Product product2 = Product.builder()
            .name("카페라떼")
            .price(5000)
            .productNumber("002")
            .sellingStatus(HOLD)
            .type(ProductType.HANDMADE)
            .build();
        Product product3 = Product.builder()
            .name("크루아상")
            .price(5000)
            .productNumber("003")
            .sellingStatus(STOP_SELLING)
            .type(ProductType.BAKERY)
            .build();
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
        Product product1 = Product.builder()
            .name("아메리카노")
            .price(4000)
            .productNumber("001")
            .sellingStatus(SELLING)
            .type(ProductType.HANDMADE)
            .build();
        Product product2 = Product.builder()
            .name("카페라떼")
            .price(5000)
            .productNumber("002")
            .sellingStatus(HOLD)
            .type(ProductType.HANDMADE)
            .build();
        Product product3 = Product.builder()
            .name("크루아상")
            .price(5000)
            .productNumber("003")
            .sellingStatus(STOP_SELLING)
            .type(ProductType.BAKERY)
            .build();
        productRepository.saveAll(List.of(product1, product2, product3));
        
        //when
        List<Product> result = productRepository.findAllByProductNumberIn(List.of("001", "002"));
        
        //then
        assertThat(result).hasSize(2);
    }
}