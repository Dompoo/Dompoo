package dompoo.cafekiosk.spring.api.service.product;

import dompoo.cafekiosk.spring.IntegrationTestSupport;
import dompoo.cafekiosk.spring.domain.product.Product;
import dompoo.cafekiosk.spring.domain.product.ProductRepository;
import dompoo.cafekiosk.spring.domain.product.ProductSellingStatus;
import dompoo.cafekiosk.spring.domain.product.ProductType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static dompoo.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static dompoo.cafekiosk.spring.domain.product.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;

class ProductNumberFactoryTest extends IntegrationTestSupport {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductNumberFactory productNumberFactory;
	
	@AfterEach
	void tearDown() {
		productRepository.deleteAllInBatch();
	}
	
	@Test
	@DisplayName("상품이 1개 이상 있을 경우 상품번호는 그보다 1 높아야 한다.")
	void createNextProductNumber() {
	    //given
		Product product = createProduct("001", HANDMADE, SELLING, 4000, "아메리카노");
		productRepository.save(product);
	    
	    //when
		String result = productNumberFactory.createNextProductNumber();
		
		//then
		assertThat(result).isEqualTo("002");
	}
	
	@Test
	@DisplayName("마지막 상품번호가 999일경우 상품번호는 1000이어야 한다.")
	void createNextProductNumberWithProductNumberIs999() {
		//given
		Product product = createProduct("999", HANDMADE, SELLING, 4000, "아메리카노");
		productRepository.save(product);
		
		//when
		String result = productNumberFactory.createNextProductNumber();
		
		//then
		assertThat(result).isEqualTo("1000");
	}
	
	@Test
	@DisplayName("상품이 없을 있을 경우 상품번호는 001이어야 한다.")
	void createNextProductNumberWithNoProduct() {
		//when
		String result = productNumberFactory.createNextProductNumber();
		
		//then
		assertThat(result).isEqualTo("001");
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