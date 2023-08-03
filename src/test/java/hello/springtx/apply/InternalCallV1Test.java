package hello.springtx.apply;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class InternalCallV1Test {

    @Autowired CallService callService;

    @Test
    void printProxy() {
        log.info("callServcie class={}", callService.getClass());
    }

    @Test
    void internalCall() {
        callService.internal();
    }

    @Test
    void externalCall() {
        callService.external();
    }

    @Test
    void externalCall_fixed() {
        callService.external2();
    }

    @TestConfiguration
    static class InternalCallV1TestConfig {

        @Bean
        CallService callService() {
            return new CallService(internalService());
        }

        @Bean
        InternalService internalService() {
            return new InternalService();
        }
    }

    @Slf4j
    @RequiredArgsConstructor
    static class CallService {

        private final InternalService internalService;

        public void external() {
            log.info("call external");
            printTxInfo();
            internal(); //프록시 내부 호출 -> 트랜잭션이 정상작동하지 않는다.
            /**
             * 클라이언트가 트랜잭션 프록시를 호출하였을 때, external은 트랜잭션이 적용되지 않고 호출된다.
             * 이때, external이 internal을 호출하게 되는데 이는 트랜잭션 프록시를 통해 호출되는 것이 아니다.
             * 다시 말하면, internal은 프록시를 거치지 않고 호출되므로 트랜잭션이 적용되지 못한다.
             */
        }

        public void external2() {
            log.info("call external");
            printTxInfo();
            internalService.internal(); //메서드를 외부 클래스로 분리
            /**
             * 외부 클래스를 통해 호출하게 되면 트랜잭션 프록시를 거치게 된다.
             */
        }

        @Transactional
        public void internal() {
            log.info("call internal");
            printTxInfo();
        }

        public void printTxInfo() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active={}", txActive);
            boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
            log.info("readOnly={}", readOnly);
        }
    }

    static class InternalService {
        @Transactional
        public void internal() {
            log.info("call internal");
            printTxInfo();
        }

        public void printTxInfo() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active={}", txActive);
            boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
            log.info("readOnly={}", readOnly);
        }
    }


}
