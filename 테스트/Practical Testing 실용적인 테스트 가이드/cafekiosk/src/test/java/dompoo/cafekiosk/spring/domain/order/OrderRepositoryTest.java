package dompoo.cafekiosk.spring.domain.order;

import dompoo.cafekiosk.spring.domain.orderproduct.OrderProductRepository;
import dompoo.cafekiosk.spring.domain.product.Product;
import dompoo.cafekiosk.spring.domain.product.ProductRepository;
import dompoo.cafekiosk.spring.domain.product.ProductSellingStatus;
import dompoo.cafekiosk.spring.domain.product.ProductType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static dompoo.cafekiosk.spring.domain.product.ProductSellingStatus.*;
import static dompoo.cafekiosk.spring.domain.product.ProductType.HANDMADE;

@DataJpaTest
class OrderRepositoryTest {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderProductRepository orderProductRepository;
	
	@AfterEach
	void tearDown() {
		orderProductRepository.deleteAllInBatch();
		orderRepository.deleteAllInBatch();
		productRepository.deleteAllInBatch();
	}
	
	@Test
	@DisplayName("시작시간과 종료시간 사이의 특정 주문 상태의 주문을 조회할 수 있어야 한다.")
	void findOrdersBy() {
	    //given
		LocalDateTime dateTime = LocalDateTime.of(2024, 7, 31, 10, 0, 30);
		
		Product product1 = createProduct("001", HANDMADE, SELLING, 4000, "아메리카노");
		Product product2 = createProduct("002", HANDMADE, HOLD, 5000, "카페라떼");
		Product product3 = createProduct("003", HANDMADE, STOP_SELLING, 5000, "크루아상");
		List<Product> productList = List.of(product1, product2, product3);
		productRepository.saveAll(productList);
		
		savePaymentCompletedOrder(productList, dateTime.minusDays(1).withHour(23).withMinute(59).withSecond(59));
		Order order1 = savePaymentCompletedOrder(productList, dateTime.withHour(0).withMinute(0).withSecond(0));
		Order order2 = savePaymentCompletedOrder(productList, dateTime);
		Order order3 = savePaymentCompletedOrder(productList, dateTime.withHour(23).withMinute(59).withSecond(58));
		savePaymentCompletedOrder(productList, dateTime.plusDays(1).withHour(0).withMinute(0).withSecond(0));
	    
	    //when
		List<Order> result = orderRepository.findOrdersBy(
				dateTime.withHour(0).withMinute(0).withSecond(0),
				dateTime.withHour(0).withMinute(0).withSecond(0).plusDays(1),
				OrderStatus.PAYMENT_COMPLETED
		);
		
		//then
		Assertions.assertThat(result).hasSize(3)
				.containsExactly(order1, order2, order3);
	}
	
	private Order savePaymentCompletedOrder(List<Product> productList, LocalDateTime dateTime) {
		Order order = Order.builder()
				.products(productList)
				.registeredDateTime(dateTime)
				.status(OrderStatus.PAYMENT_COMPLETED)
				.build();
		return orderRepository.save(order);
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