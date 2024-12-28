package dompoo.transaction.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 트랜잭션은 프록시에서 적용되는 것이다.
 * 그런데, 대상 클래스의 메서드(external)에서 @Transactional 메서드(internal)를 내부 호출한다고 해도,
 * 해당 내부 호출되는 메서드는 타겟의 메서드이기 때문에, 트랜잭션이 적용되지 않는다.
 * 정리하자면, 내부 호출되는 메서드에는 트랜잭션이 적용되지 않는다!
 *
 * 이런 문제는 프록시 기반 aop에서 모두 동일하게 벌어지는 일이다.
 * 내부 호출은 aop가 안먹여진다.
 */
@SpringBootTest
@Slf4j
public class InternalCallV1Test {
	
	@Autowired
	CallService callService;
	
	@Test
	void printClass() {
		log.info("service class: {}", callService.getClass());
	}
	
	@Test
	void callInternal() {
		callService.internal();
	}
	
	@Test
	void callExternal() {
		callService.external();
	}
	
	@TestConfiguration
	static class InternalCallV1TestConfig {
		
		@Bean
		CallService callService() {
			return new CallService();
		}
	}
	
	static class CallService {
		
		public void external() {
			log.info("call external");
			printTxInfo();
			internal();
		}
		
		@Transactional
		public void internal() {
			log.info("call internal");
			printTxInfo();
		}
		
		private void printTxInfo() {
			boolean active = TransactionSynchronizationManager.isActualTransactionActive();
			boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
			log.info("tx active : {}, readOnly : {}", active, readOnly);
		}
	}
}
