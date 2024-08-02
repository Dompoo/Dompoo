package dompoo.cafekiosk.spring.api.service.order;

import dompoo.cafekiosk.spring.IntegrationTestSupport;
import dompoo.cafekiosk.spring.client.mail.MailSendClient;
import dompoo.cafekiosk.spring.domain.history.mail.MailSendHistory;
import dompoo.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;
import dompoo.cafekiosk.spring.domain.order.Order;
import dompoo.cafekiosk.spring.domain.order.OrderRepository;
import dompoo.cafekiosk.spring.domain.order.OrderStatus;
import dompoo.cafekiosk.spring.domain.orderproduct.OrderProductRepository;
import dompoo.cafekiosk.spring.domain.product.Product;
import dompoo.cafekiosk.spring.domain.product.ProductRepository;
import dompoo.cafekiosk.spring.domain.product.ProductSellingStatus;
import dompoo.cafekiosk.spring.domain.product.ProductType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static dompoo.cafekiosk.spring.domain.product.ProductSellingStatus.*;
import static dompoo.cafekiosk.spring.domain.product.ProductType.HANDMADE;
import static org.mockito.ArgumentMatchers.any;

class OrderStatisticsServiceTest extends IntegrationTestSupport {
	
	@Autowired
	private OrderStatisticsService orderStatisticsService;
	
	@Autowired
	private OrderProductRepository orderProductRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private MailSendHistoryRepository mailSendHistoryRepository;
	
	@MockBean
	private MailSendClient mailSendClient;
	
	@AfterEach
	void tearDown() {
		orderProductRepository.deleteAllInBatch();
		orderRepository.deleteAllInBatch();
		productRepository.deleteAllInBatch();
		mailSendHistoryRepository.deleteAllInBatch();
	}
	
	@Test
	@DisplayName("결제 완료 주문들을 조회하여, 매출 통계 메일을 전송할 수 있어야 한다.")
	void sendOrderStatisticsMail() {
	    //given
		LocalDateTime now = LocalDateTime.of(2024, 7, 31, 10, 0);
		
		Product product1 = createProduct("001", HANDMADE, SELLING, 4000, "아메리카노");
		Product product2 = createProduct("002", HANDMADE, HOLD, 5000, "카페라떼");
		Product product3 = createProduct("003", HANDMADE, STOP_SELLING, 5000, "크루아상");
		List<Product> productList = List.of(product1, product2, product3);
		productRepository.saveAll(productList);
		
		savePaymentCompletedOrder(productList, now.minusDays(1).withHour(23).withMinute(59).withSecond(59));
		savePaymentCompletedOrder(productList, now.withHour(0).withMinute(0).withSecond(0));
		savePaymentCompletedOrder(productList, now);
		savePaymentCompletedOrder(productList, now.withHour(23).withMinute(59).withSecond(59));
		savePaymentCompletedOrder(productList, now.plusDays(1).withHour(0).withMinute(0).withSecond(0));
		
		//가짜 행위를 정의하는 것 stub, stubbing
		Mockito.when(mailSendClient.sendMail(any(String.class), any(String.class), any(String.class), any(String.class)))
				.thenReturn(true);
		
		//when
		orderStatisticsService.sendOrderStatisticsMail(now.toLocalDate(), "email@email.com");
		
		//then
		List<MailSendHistory> historyList = mailSendHistoryRepository.findAll();
		Assertions.assertThat(historyList).hasSize(1)
				.extracting("content")
				.contains("매출 합계는 42000원 입니다.");
		
	}
	
	private Order savePaymentCompletedOrder(List<Product> productList, LocalDateTime now) {
		Order order = Order.builder()
				.products(productList)
				.registeredDateTime(now)
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