package dompoo.springtx.exception;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class RollbackTest {

    @Autowired RollbackServcie servcie;

    @Test
    void runtimeExceptin() {
        //런타임 예외 발생 : 런타임 예외는 복구불가능한 예외로 가정 -> 롤백
        Assertions.assertThatThrownBy(() -> servcie.runtimeException())
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void checkedException() {
        //체크 예외 발생 : 체크 예외는 비즈니스적 의미가 있다고 가정 -> 커밋
        Assertions.assertThatThrownBy(() -> servcie.checkedException())
                .isInstanceOf(MyException.class);
    }

    @Test
    void rollbackFor() {
        //체크 예외 rollbackFor 지정 : 롤백
        Assertions.assertThatThrownBy(() -> servcie.rollbackFor())
                .isInstanceOf(MyException.class);
    }

    @TestConfiguration
    static class RollbackTestConfig {

        @Bean
        RollbackServcie rollbackServcie() {
            return new RollbackServcie();
        }
    }

    @Slf4j
    static class RollbackServcie {

        //런타임 예외 발생 : 롤백
        @Transactional
        public void runtimeException() {
            log.info("call runtimeException");
            throw new RuntimeException();
        }

        //체크 예외 발생 : 커밋
        @Transactional
        public void checkedException() throws MyException {
            log.info("call checkedException");
            throw new MyException();
        }

        //체크 예외 rollbackFor 지정 : 롤백
        @Transactional(rollbackFor = MyException.class)
        public void rollbackFor() throws MyException {
            log.info("call checkedException");
            throw new MyException();
        }
    }

    static class MyException extends Exception {
    }


}
