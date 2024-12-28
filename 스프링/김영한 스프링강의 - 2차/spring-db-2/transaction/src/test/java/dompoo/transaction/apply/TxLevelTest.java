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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 항상 더 구체적인 것이 우선권을 갖는다.
 * 클래스 레벨에서 readOnly = true여도,
 * 메서드 레벨에서 readOnly = false로 정의하면,
 * 그 내부에서는 false이다.
 * 또한, 트랜잭션이 겹쳐있을 때 내부 트랜잭션의 기본값이 있으므로
 * 외부 트랜잭션은 아예 지워진다.
 */
@Slf4j
@SpringBootTest
public class TxLevelTest {
	
	@Autowired
	LevelService levelService;
	
	@Test
	void proxyCheck() {
		log.info("aop class : {}", AopUtils.isAopProxy(levelService));
		assertThat(AopUtils.isAopProxy(levelService)).isTrue();
	}
	
	@Test
	void levelTest() {
		levelService.write();
		levelService.read();
	}
	
	
	@TestConfiguration
	static class TxLevelTestConfig {
		@Bean
		LevelService levelService() {
			return new LevelService();
		}
	}
	
	@Transactional(readOnly = true) //읽기 전용 트랜잭션이 생성된다.
	static class LevelService {
		
		@Transactional
		public void write() {
			log.info("call write");
			printTxInfo();
		}
		
		public void read() {
			log.info("call read");
			printTxInfo();
		}
		
		private void printTxInfo() {
			boolean active = TransactionSynchronizationManager.isActualTransactionActive();
			boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
			log.info("tx active : {}, readOnly : {}", active, readOnly);
		}
	}
}
