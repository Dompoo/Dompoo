package dompoo.cafekiosk.spring.api.service.order;

import dompoo.cafekiosk.spring.api.service.mail.MailService;
import dompoo.cafekiosk.spring.domain.order.Order;
import dompoo.cafekiosk.spring.domain.order.OrderRepository;
import dompoo.cafekiosk.spring.domain.order.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderStatisticsService {
	
	private final OrderRepository orderRepository;
	private final MailService mailService;
	
	//DB 조회시 커넥션을 하나 소비하는데, 트랜잭션에 참여하지 않아도 되는데 후에 긴 작업이 있는 경우
	//트랜잭션을 걸지 않으면 repository에서 걸리는 트랜잭션 만으로 충분할 수 있으므로 걸지 않는다.
	public boolean sendOrderStatisticsMail(LocalDate orderDate, String targetEmail) {
		// 해당 일자에 결제완료된 주문들을 가져와서
		List<Order> orderList = orderRepository.findOrdersBy(
				orderDate.atStartOfDay(),
				orderDate.atStartOfDay().plusDays(1),
				OrderStatus.PAYMENT_COMPLETED
		);
		
		// 총 매출합계를 계산하고
		int totalPriceOfDay = orderList.stream()
				.mapToInt(Order::getTotalPrice)
				.sum();
		
		// 메일을 보낸다.
		boolean result = mailService.sendMail(
				"no-reply@test.com",
				targetEmail,
				String.format("[매출통계] %s", orderDate),
				String.format("매출 합계는 %d원 입니다.", totalPriceOfDay));
		
		if (!result) {
			throw new IllegalArgumentException("매출 통계 메일 전송에 실패했습니다.");
		}
		
		return result;
	}
	
}
