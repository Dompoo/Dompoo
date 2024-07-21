package dompoo.transaction.apply;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 내부 호출을 외부 호출로 리팩토링하면,
 * 외부 호출은 곧 프록시 객체를 호출하게 되므로 정상적으로 트랜잭션이 적용한다.
 *
 * 추가적으로 트랜잭션 aop는 public 메서드에만 적용된다.
 * package-visible(default), proteced, private는
 * 예외가 발생하지는 않으나, 트랜잭션 적용이 무시된다.
 */
@SpringBootTest
@Slf4j
public class InternalCallV2Test {
	
	@Autowired
	CallService callService;
	
	@Autowired
	InternalService internalService;
	
	@Test
	void printClass() {
		log.info("service class: {}", callService.getClass());
		log.info("service class: {}", internalService.getClass());
	}
	
	@Test
	void callInternal() {
		internalService.internal();
	}
	
	@Test
	void callExternal() {
		callService.external();
	}
	
	@TestConfiguration
	static class InternalCallV1TestConfig {
		
		@Bean
		InternalService internalService() {
			return new InternalService();
		}
		
		@Bean
		CallService callService() {
			return new CallService(internalService());
		}
	}
	
	@RequiredArgsConstructor
	static class CallService {
		
		private final InternalService internalService;
		
		public void external() {
			log.info("call external");
			printTxInfo();
			internalService.internal();
		}
		
		private void printTxInfo() {
			boolean active = TransactionSynchronizationManager.isActualTransactionActive();
			boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
			log.info("tx active : {}, readOnly : {}", active, readOnly);
		}
	}
	
	static class InternalService {
		
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
