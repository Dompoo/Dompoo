package dompoo.transaction.exception;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 런타임 예외는 기본적으로 롤백된다.
 * 체크 예외는 기본적으로 커밋된다.
 * rollbackFor에 옵션을 주면 체크 예외도 롤백할 수 있다.
 */
@SpringBootTest
@Slf4j
public class RollBackTest {
	
	@Autowired
	RollBackService service;
	
	@Test
	void runtime() {
		assertThatThrownBy(
				() -> service.runtimeEx() // 런타임예외가 터지면 롤백된다.
		);
	}
	
	@Test
	void checked() {
		assertThatThrownBy(
				() -> service.checkedEx() // 체크예외가 터지면 커밋된다.
		);
	}
	
	@Test
	void rollbackFor() {
		assertThatThrownBy(
				() -> service.rollbackFor() // 체크예외가 터져도 롤백된다.
		);
	}
	
	@TestConfiguration
	static class RollBackTestConfig {
		
		@Bean
		RollBackService rollBackService() {
			return new RollBackService();
		}
	}
	
	static class RollBackService {
		
		// 런타임예외 : 롤백
		@Transactional
		public void runtimeEx() {
			log.info("call runtime exception");
			throw new RuntimeException();
		}
		
		// 체크예외 : 커밋
		@Transactional
		public void checkedEx() throws MyException {
			log.info("call checked exception");
			throw new MyException();
		}
		
		// 체크예외를 rollbackFor 지정 : 롤백
		@Transactional(rollbackFor = MyException.class)
		public void rollbackFor() throws MyException {
			log.info("call rollback checked exception");
			throw new MyException();
		}
	}
	
	static class MyException extends Exception {
	
	}
}
