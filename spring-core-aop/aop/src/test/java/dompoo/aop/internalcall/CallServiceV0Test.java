package dompoo.aop.internalcall;

import dompoo.aop.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(CallLogAspect.class)
@Slf4j
@SpringBootTest
class CallServiceV0Test {

    @Autowired CallServiceV0 callService;

    @Test
    void external() {
        callService.external();
        //internal 호출에서는 target 객체 안에서 작동하기 때문에,
        //this.internal()에서는 AOP가 적용되지 않는다.
    }

    @Test
    void internal() {
        callService.internal();
        //프록시의 internal()에서는 AOP가 적용된다.
    }
}