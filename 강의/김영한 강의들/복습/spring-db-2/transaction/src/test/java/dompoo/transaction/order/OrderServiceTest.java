package dompoo.transaction.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Slf4j
class OrderServiceTest {
	
	@Autowired
	OrderService orderService;
	@Autowired
	OrderRepository orderRepository;

	@Test
	void complete() throws NotEnoughMoney {
		Order order = new Order();
		order.setUsername("정상");
		
		orderService.order(order);
		
		Order findOrder = orderRepository.findById(order.getId()).get();
		assertThat(findOrder.getPayStatus()).isEqualTo("완료");
	}
	
	@Test
	void sysEx() throws NotEnoughMoney {
		Order order = new Order();
		order.setUsername("예외");
		
		assertThatThrownBy(() ->
				orderService.order(order))
				.isExactlyInstanceOf(RuntimeException.class);
		
		// 런타임(시스템)예외는 롤백된다.
		assertThat(orderRepository.findById(order.getId()).isEmpty()).isTrue();
	}
	
	@Test
	void bizEx() {
		Order order = new Order();
		order.setUsername("잔고부족");
		
		try {
			orderService.order(order);
		} catch (NotEnoughMoney e) {
			// 요런식으로 체크예외를 비즈니스 예외로 사용할 수 있다.
			log.info("고객에게 잔고부족을 알림");
		}
		
		// 체크(비즈니스)예외는 커밋된다.
		Order findOrder = orderRepository.findById(order.getId()).get();
		assertThat(findOrder.getPayStatus()).isEqualTo("대기");
	}

}