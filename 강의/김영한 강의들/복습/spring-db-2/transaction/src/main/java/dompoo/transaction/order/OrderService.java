package dompoo.transaction.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
	
	private final OrderRepository orderRepository;
	
	@Transactional
	public void order(Order order) throws NotEnoughMoney {
		log.info("order 호출");
		orderRepository.save(order);
		
		log.info("결제 프로세스 시작");
		if (order.getUsername().equals("예외")) {
			// 시스템 예외 -> 복구불가능 -> 런타임예외
			log.info("시스템 예외 발생");
			throw new RuntimeException();
		} else if (order.getUsername().equals("잔고부족")) {
			// 비즈니스 예외 -> 체크예외
			log.info("비즈니스 예외 발생");
			order.setPayStatus("대기");
			throw new NotEnoughMoney("잔고가 부족합니다.");
		} else {
			log.info("정상 승인");
			order.setPayStatus("완료");
		}
		log.info("결제 프로세스 완료");
	}
}
