package dompoo.transaction.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static org.assertj.core.api.Assertions.*;

/**
 * Transactional 이 하나라도 붙어있으면 Spring AOP가 작동한다.
 * 실제 스프링 컨테이너에는 실제 객체가 아닌 프록시 객체가 등록되며,
 * 이후에 의존성 주입을 받을 때 이 프록시 객체가 주입된다.
 */
@Slf4j
@SpringBootTest
public class TxBasicTest {
	
	@Autowired
	BasicService basicService;
	
	@Test
	void proxyCheck() {
		log.info("aop class : {}", AopUtils.isAopProxy(basicService));
		assertThat(AopUtils.isAopProxy(basicService)).isTrue();
	}
	
	@Test
	void txTest() {
		assertThat(basicService.tx()).isTrue();
		assertThat(basicService.noTx()).isFalse();
	}
	
	
	@TestConfiguration
	static class TxBasicTestConfig {
		@Bean
		BasicService basicService() {
			return new BasicService();
		}
	}
	
	static class BasicService {
		
		@Transactional
		public boolean tx() {
			log.info("call tx");
			boolean active = TransactionSynchronizationManager.isActualTransactionActive();
			log.info("is Active : {}", active);
			return active;
		}
		
		public boolean noTx() {
			log.info("call noTx");
			boolean active = TransactionSynchronizationManager.isActualTransactionActive();
			log.info("is Active : {}", active);
			return active;
		}
	}
}
