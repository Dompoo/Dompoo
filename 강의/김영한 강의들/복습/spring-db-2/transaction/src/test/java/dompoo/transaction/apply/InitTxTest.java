package dompoo.transaction.apply;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@SpringBootTest
@Slf4j
public class InitTxTest {
	
	@Autowired
	Hello hello;
	
	@Test
	void test() {
	    // 초기화 코드는 스프링이 초기화 시점에 알아서 실행한다.
		// 스프링 초기화 시점에는 spring aop가 적용되지 않으므로,
		// transactional 도 적용되지 않는다.
		
		// 따라서 초기화 이후에 애플리케이션이 준비되었을 때 초기화 코드를 실행해야
		// transactional 이 적용될 수 있는데,
		// 이를 위해서는 @EventListener(ApplicationReadyEvent.class) 를 사용한다.
	}
	
	@TestConfiguration
	static class InitTxTestConfig {
		@Bean
		Hello hello() {
			return new Hello();
		}
	}
	
	static class Hello {
		
		@PostConstruct
		@Transactional
		public void initV1() {
			boolean active = TransactionSynchronizationManager.isActualTransactionActive();
			log.info("@PostConstruct transaction active : {}", active);
		}
		
		@EventListener(ApplicationReadyEvent.class)
		@Transactional
		public void initV2() {
			boolean active = TransactionSynchronizationManager.isActualTransactionActive();
			log.info("@EventListener transaction active : {}", active);
		}
	}
}
