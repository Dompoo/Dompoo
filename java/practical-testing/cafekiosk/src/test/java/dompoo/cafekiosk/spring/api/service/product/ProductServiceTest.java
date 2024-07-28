package dompoo.cafekiosk.spring.api.service.product;

import static dompoo.cafekiosk.spring.domain.product.ProductSellingStatus.HOLD;
import static dompoo.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static dompoo.cafekiosk.spring.domain.product.ProductSellingStatus.STOP_SELLING;
import static org.assertj.core.api.Assertions.assertThat;

import dompoo.cafekiosk.spring.api.service.product.response.ProductResponse;
import dompoo.cafekiosk.spring.domain.product.Product;
import dompoo.cafekiosk.spring.domain.product.ProductRepository;
import dompoo.cafekiosk.spring.domain.product.ProductType;
import java.util.List;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class ProductServiceTest {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private ProductService productService;
    
    @Test
    @DisplayName("현재 판매중이거나 판매 보류된 상품을 조회할 수 있어야 한다.")
    void getSellingProducts() {
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
        List<ProductResponse> response = productService.getSellingProducts();
        
        //then
        assertThat(response).hasSize(2)
            .extracting("name", "productNumber", "sellingType")
            .containsExactlyInAnyOrder(
                Tuple.tuple("아메리카노", "001", SELLING),
                Tuple.tuple("카페라떼", "002", HOLD)
            );
    }
}