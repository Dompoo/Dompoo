package dompoo.cafekiosk.spring.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	@Query(value = "select o from Order o where " +
			"o.registedDateTime >= :startDateTime " +
			"and o.registedDateTime < :endDateTime " +
			"and o.status = :status")
	List<Order> findOrdersBy(
			@Param("startDateTime") LocalDateTime startDateTime,
			@Param("endDateTime") LocalDateTime endDateTime,
			@Param("status") OrderStatus status);
}
